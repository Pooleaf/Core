package net.pooleaf.core.modules.channel.common.redis;

import java.io.File;

import lombok.Getter;
import net.pooleaf.core.modules.redislib.common.AbstractRedisManager;
import net.pooleaf.core.Core;

public class ChannelRedisManager extends AbstractRedisManager {

    private ChannelDao channelDao = new ChannelDao(this);
    private ChannelGroupDao channelGroupDao = new ChannelGroupDao(this);

    @Getter
    private ChannelRedisConfig channelConfig;


    public ChannelRedisManager() {
        super(Core.getPlugin());

        channelConfig = new ChannelRedisConfig(Core.getPlugin());
        channelConfig.setFile(new File(Core.getPlugin().getDataFolder(), "channel-redis-config.yml"));
    }

    @Override
    public void loadConfig() {
        try {
            channelConfig.load();
            channelConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ChannelDao channel() {
        return channelDao;
    }

    public ChannelGroupDao channelGroup() {
        return channelGroupDao;
    }

}