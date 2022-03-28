package net.pooleaf.core.modules.option.common.redis;

import net.pooleaf.core.modules.redislib.common.AbstractRedisManager;

import java.util.Map;

public class OptionServerDao extends OptionDao {

    public static final String SERVER_OPTION_NAME = "global_option";


    public OptionServerDao(AbstractRedisManager redisManager) {
        super(redisManager);
    }


    public void set(String key, Object value) {
        set(SERVER_OPTION_NAME, key, value);
    }

    public void set(Map<String, String> options) {
        set(SERVER_OPTION_NAME, options);
    }

    public boolean exists(String key) {
        return exists(SERVER_OPTION_NAME, key);
    }

    public Object get(String key) {
        return get(SERVER_OPTION_NAME, key);
    }

    public Map<String, String> getAll() {
        return getAll(SERVER_OPTION_NAME);
    }

    public void delete(String key) {
        delete(SERVER_OPTION_NAME, key);
    }

}