package net.pooleaf.core.modules.channel.common.redis;

import net.pooleaf.core.modules.redislib.common.AbstractRedisManager;

public class RedisManager extends AbstractRedisManager {

    private RedisChannelDao channelDao;
    private RedisChannelGroupDao channelGroupDao;


    public RedisChannelDao channel() {
        return channelDao;
    }

    public RedisChannelGroupDao channelGroup() {
        return channelGroupDao;
    }

}