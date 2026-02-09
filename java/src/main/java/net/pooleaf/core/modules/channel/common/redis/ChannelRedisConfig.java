package net.pooleaf.core.modules.channel.common.redis;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigName;
import net.pooleaf.core.modules.redislib.common.configs.RedisConfig;
import net.pooleaf.core.plugin.CorePlugin;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelRedisConfig extends RedisConfig {

    @ConfigName("서버 이름")
    private String serverName = Core.getServerName();

    public ChannelRedisConfig(CorePlugin plugin) {
        super(plugin);
    }

}
