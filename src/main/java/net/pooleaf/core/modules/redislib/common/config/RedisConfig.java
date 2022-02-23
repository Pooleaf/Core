package net.pooleaf.core.modules.redislib.common.config;

import lombok.Data;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.annoconfig.common.SimpleAnnoConfig;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigAes256;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigName;

import java.io.File;

@Data
public class RedisConfig extends SimpleAnnoConfig {

    @ConfigName("Core 플러그인 RedisManager 사용")
    private Boolean useCorePluginRedisManager = true;

    @ConfigName("주소")
    private String address = "localhost";

    @ConfigName("포트")
    private int port = 3306;

    @ConfigName("비밀번호")
    @ConfigAes256("redisconfig passwd")
    private String password = "password";

    @ConfigName("서버 이름")
    private String serverName = null;

    public RedisConfig() {
        super(new File(Core.getPlugin().getDataFolder(), "redis-config.yml"));
    }

}
