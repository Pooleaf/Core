package net.pooleaf.core.modules.redislib.common.configs;

import net.pooleaf.core.modules.annoconfig.common.SimpleAnnoConfig;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigAes256;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigName;
import lombok.Data;

import java.io.File;
import net.pooleaf.core.plugin.CorePlugin;

@Data
public class RedisConfig extends SimpleAnnoConfig {

    @ConfigName("Core 플러그인 RedisManager 사용")
    private Boolean useCorePluginRedisManager = true;

    @ConfigName("주소")
    private String address = "localhost";

    @ConfigName("포트")
    private int port = 6379;

    @ConfigName("비밀번호")
    @ConfigAes256("redisconfig passwd")
    private String password = "password";


    public RedisConfig(CorePlugin plugin) {
        super(new File(plugin.getDataFolder(), "redis-config.yml"));
    }

}
