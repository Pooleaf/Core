package net.pooleaf.core.modules.redislib.common;

public abstract class RedisDao {

    protected AbstractRedisManager redisManager;


    public RedisDao(AbstractRedisManager redisManager) {
        this.redisManager = redisManager;
        redisManager.getDaos().add(this);
    }

    public void onConnected() {}

}
