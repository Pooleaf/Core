package net.pooleaf.core.modules.channel.common.redis;

import io.lettuce.core.KeyValue;
import lombok.SneakyThrows;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroup;
import net.pooleaf.core.modules.redislib.common.AbstractRedisManager;
import net.pooleaf.core.modules.redislib.common.RedisDao;

import java.util.List;

public class ChannelGroupDao extends RedisDao {

    public static final String CHANNEL_GROUP_INFO_KEY = "channel:group_info:";


    public ChannelGroupDao(
        AbstractRedisManager redisManager) {
        super(redisManager);
    }


    @SneakyThrows
    public List<String> getAllChannelGroupNames() {
        return redisManager.getAsyncCommands().keys(CHANNEL_GROUP_INFO_KEY + "*").get();
    }

    @SneakyThrows
    public ChannelGroup loadGroup(String groupName) {
        ChannelGroup group = ChannelModule.getChannelGroupManager().get(groupName);
        if (group == null) {
            group = new ChannelGroup(groupName);
        }

        String jsonString = redisManager.getAsyncCommands().get(CHANNEL_GROUP_INFO_KEY + group.getName()).get();
        if (jsonString == null) {
            return null;
        }

        ChannelModule.getChannelGroupManager().set(groupName, group);
        group.loadFromJson(jsonString);

        return group;
    }

    @SneakyThrows
    public void loadAllGroups() {
        List<String> groupNames = getAllChannelGroupNames();
        if (groupNames.isEmpty()) {
            return;
        }

        String[] groupNamesArr = groupNames.toArray(new String[0]);
        for (KeyValue<String, String> keyValue : redisManager.getAsyncCommands().mget(groupNamesArr).get()) {
            String name = keyValue.getKey().substring(CHANNEL_GROUP_INFO_KEY.length());
            String jsonString = keyValue.getValue();

            ChannelGroup channelGroup = ChannelModule.getChannelGroupManager().get(name);
            if (channelGroup == null) {
                channelGroup = new ChannelGroup(name);
                ChannelModule.getChannelGroupManager().set(name, channelGroup);
            }

            channelGroup.loadFromJson(jsonString);
        }
    }

    public void saveGroup(ChannelGroup group) {
        redisManager.getAsyncCommands().set(CHANNEL_GROUP_INFO_KEY + group.getName(), group.toJson());
    }

    public void saveAllGroup() {
        for (ChannelGroup group : ChannelModule.getChannelGroups()) {
            saveGroup(group);
        }
    }

    public void removeGroup(String groupName) {
        redisManager.getAsyncCommands().del(CHANNEL_GROUP_INFO_KEY + groupName);
    }

    public void removeUnusedChannelGroups() {
        for (String name : getAllChannelGroupNames()) {
            name = name.substring(CHANNEL_GROUP_INFO_KEY.length());

            if (!ChannelModule.getChannelGroupManager().exists(name)) {
                removeGroup(name);
            }
        }
    }

}
