package net.pooleaf.core.modules.channel.common.redis;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.redislib.common.AbstractRedisManager;

import java.io.File;

public class ChannelRedisManager extends AbstractRedisManager {

    private ChannelDao channelDao = new ChannelDao(this);
    private ChannelGroupDao channelGroupDao = new ChannelGroupDao(this);

    public ChannelRedisManager() {
        super(Core.getPlugin());

        getConfig().setFile(new File(Core.getPlugin().getDataFolder(), "channel-redis-config.yml"));
    }

    public ChannelDao channel() {
        return channelDao;
    }

    public ChannelGroupDao channelGroup() {
        return channelGroupDao;
    }

}