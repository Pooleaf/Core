package net.pooleaf.core.modules.channel.common.redis;

import java.io.File;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.redislib.common.AbstractRedisManager;

public class ChannelRedisManager extends AbstractRedisManager {

    private RedisChannelDao channelDao = new RedisChannelDao(this);
    private RedisChannelGroupDao channelGroupDao = new RedisChannelGroupDao(this);


    public ChannelRedisManager() {
        super(Core.getPlugin());

        getConfig().setFile(new File(Core.getPlugin().getDataFolder(), "channel-redis-config.yml"));
    }


    public RedisChannelDao channel() {
        return channelDao;
    }

    public RedisChannelGroupDao channelGroup() {
        return channelGroupDao;
    }

}