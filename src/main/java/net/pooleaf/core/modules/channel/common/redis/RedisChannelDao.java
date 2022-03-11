package net.pooleaf.core.modules.channel.common.redis;

import io.lettuce.core.KeyValue;
import lombok.SneakyThrows;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.redislib.common.RedisDao;

import java.util.List;

public class RedisChannelDao extends RedisDao {

    public static final String CHANNEL_INFO_KEY = "channel:info:";


    public RedisChannelDao() {
        super(ChannelModule.getRedisManager());
    }


    /**
     * Redis에 등록된 모든 채널 이름을 불러옵니다.
     * @return channel:info:채널이름
     */
    @SneakyThrows
    public List<String> getAllChannelNames() {
        return redisManager.getAsyncCommands().keys(CHANNEL_INFO_KEY + "*").get();
    }

    @SneakyThrows
    public Channel loadChannel(String channelName) {
        Channel channel = ChannelModule.getChannelManager().get(channelName);
        if (channel == null) {
            channel = new Channel(channelName);
        }

        String jsonString = redisManager.getAsyncCommands().get(CHANNEL_INFO_KEY + channel.getName()).get();
        if (jsonString == null) {
            return null;
        }

        ChannelModule.getChannelManager().set(channel.getName(), channel);
        channel.loadFromJson(jsonString);

        return channel;
    }

    @SneakyThrows
    public void loadAllChannels() {
        List<String> channelNames = getAllChannelNames();
        if (channelNames.isEmpty()) {
            return;
        }

        String[] channelNamesArr = channelNames.toArray(new String[0]);
        for (KeyValue<String, String> keyValue : redisManager.getAsyncCommands().mget(channelNamesArr).get()) {
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
        redisManager.getAsyncCommands().set(CHANNEL_INFO_KEY + channel.getName(), channel.toJson());
    }

    public void saveAllChannels() {
        for (Channel channel : ChannelModule.getChannels()) {
            saveChannel(channel);
        }
    }

    public void removeChannel(String channelName) {
        redisManager.getAsyncCommands().del(CHANNEL_INFO_KEY + channelName);
    }

    public void removeUnusedChannels() {
        for (String name : getAllChannelNames()) {
            name = name.substring(CHANNEL_INFO_KEY.length());

            if (!ChannelModule.getChannelManager().exists(name)) {
                removeChannel(name);
            }
        }
    }

}
