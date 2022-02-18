package net.pooleaf.core.modules.redislib.common;

public abstract class RedisDao {

    protected AbstractRedisManager sqlManager;


    public RedisDao(AbstractRedisManager sqlManager) {
        this.sqlManager = sqlManager;
        sqlManager.getDaos().add(this);
    }

    public void onConnected() {}

}
