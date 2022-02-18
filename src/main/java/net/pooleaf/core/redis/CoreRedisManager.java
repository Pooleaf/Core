package net.pooleaf.core.redis;

import net.pooleaf.core.modules.redislib.common.AbstractRedisManager;

public class CoreRedisManager extends AbstractRedisManager {

    public CoreRedisManager() {
        // Core 플러그인 RedisManager 사용 Config 제거
        getConfig().setUseCorePluginRedisManager(null);
    }

}
