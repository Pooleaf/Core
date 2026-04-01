package net.pooleaf.core.modules.channel.common.redis;

import net.pooleaf.core.modules.redislib.common.AbstractRedisManager;
import net.pooleaf.core.modules.redislib.common.RedisDao;
import io.lettuce.core.KeyValue;
import lombok.SneakyThrows;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;

import java.util.List;

public class ChannelDao extends RedisDao {

    public static final String CHANNEL_INFO_KEY = "channel:info:";


    public ChannelDao(AbstractRedisManager redisManager) {
        super(redisManager);
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

    /**
     * 등록된 서버 이름 목록에 없는 채널만 Redis에서 삭제합니다.
     * BungeeCord 재시작 시 기존 채널 데이터를 보존하기 위해 사용됩니다.
     */
    public void removeUnregisteredChannels(java.util.Set<String> registeredServerNames) {
        for (String name : getAllChannelNames()) {
            name = name.substring(CHANNEL_INFO_KEY.length());

            if (!registeredServerNames.contains(name)) {
                removeChannel(name);
            }
        }
    }

}
