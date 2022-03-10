package net.pooleaf.core.modules.channel.common;

import io.lettuce.core.KeyValue;
import lombok.SneakyThrows;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroup;
import net.pooleaf.core.modules.redislib.common.AbstractRedisManager;

import java.util.List;

public class RedisManager extends AbstractRedisManager {

    public static final String CHANNEL_INFO_KEY = "channel:info:";
    public static final String CHANNEL_GROUP_INFO_KEY = "channel:group_info:";


    // Channel

    /**
     * Redis에 등록된 모든 채널 이름을 불러옵니다.
     * @return channel:info:채널이름
     */
    @SneakyThrows
    public List<String> getAllChannelNames() {
        return getAsyncCommands().keys(CHANNEL_INFO_KEY + "*").get();
    }

    @SneakyThrows
    public void loadChannel(String channelName) {
        Channel channel = ChannelModule.getChannelManager().get(channelName);
        if (channel == null) {
            channel = new Channel(channelName);
        }

        String jsonString = getAsyncCommands().get(CHANNEL_INFO_KEY + channel.getName()).get();
        if (jsonString == null) {
            return;
        }

        ChannelModule.getChannelManager().set(channel.getName(), channel);
        channel.loadFromJson(jsonString);
    }

    @SneakyThrows
    public void loadAllChannels() {
        List<String> channelNames = getAllChannelNames();
        if (channelNames.isEmpty()) {
            return;
        }

        String[] channelNamesArr = channelNames.toArray(new String[0]);
        for (KeyValue<String, String> keyValue : getAsyncCommands().mget(channelNamesArr).get()) {
            String name = keyValue.getKey().substring(CHANNEL_INFO_KEY.length());
            String jsonString = keyValue.getValue();

            Channel channel = ChannelModule.getChannelManager().get(name);
            if (channel == null) {
                channel = new Channel(name);
                ChannelModule.getChannelManager().set(name, channel);
            }

            channel.loadFromJson(jsonString);
        }
    }

    public void saveChannel(Channel channel) {
        getAsyncCommands().set(CHANNEL_INFO_KEY + channel.getName(), channel.toJson());
    }

    public void saveAllChannels() {
        for (Channel channel : ChannelModule.getChannels()) {
            saveChannel(channel);
        }
    }

    public void removeChannel(String channelName) {
        getAsyncCommands().del(CHANNEL_INFO_KEY + channelName);
    }

    public void removeUnusedChannels() {
        for (String name : getAllChannelNames()) {
            name = name.substring(CHANNEL_INFO_KEY.length());

            if (!ChannelModule.getChannelManager().exists(name)) {
                removeChannel(name);
            }
        }
    }


    // ChannelGroup

    @SneakyThrows
    public List<String> getAllChannelGroupNames() {
        return getAsyncCommands().keys(CHANNEL_GROUP_INFO_KEY + "*").get();
    }

    @SneakyThrows
    public void loadGroup(String groupName) {
        ChannelGroup group = ChannelModule.getChannelGroupManager().get(groupName);
        if (group == null) {
            group = new ChannelGroup(groupName);
        }

        String jsonString = getAsyncCommands().get(CHANNEL_GROUP_INFO_KEY + group.getName()).get();
        if (jsonString == null) {
            return;
        }

        ChannelModule.getChannelGroupManager().set(groupName, group);
        group.loadFromJson(jsonString);
    }

    @SneakyThrows
    public void loadAllGroup() {
        List<String> groupNames = getAllChannelGroupNames();
        if (groupNames.isEmpty()) {
            return;
        }

        String[] groupNamesArr = groupNames.toArray(new String[0]);
        for (KeyValue<String, String> keyValue : getAsyncCommands().mget(groupNamesArr).get()) {
            String name = keyValue.getKey().substring(CHANNEL_GROUP_INFO_KEY.length());
            String jsonString = keyValue.getValue();

            Channel channel = ChannelModule.getChannelManager().get(name);
            if (channel == null) {
                channel = new Channel(name);
                ChannelModule.getChannelManager().set(name, channel);
            }

            channel.loadFromJson(jsonString);
        }
    }

    public void saveGroup(ChannelGroup group) {
        getAsyncCommands().set(CHANNEL_GROUP_INFO_KEY + group.getName(), group.toJson());
    }

    public void saveAllGroup() {
        for (ChannelGroup group : ChannelModule.getChannelGroups()) {
            saveGroup(group);
        }
    }

    public void removeGroup(String groupName) {
        getAsyncCommands().del(CHANNEL_GROUP_INFO_KEY + groupName);
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