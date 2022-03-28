package net.pooleaf.core.modules.option.common.redis;

import net.pooleaf.core.modules.redislib.common.AbstractRedisManager;

import java.util.Map;
import java.util.UUID;

public class OptionPlayerDao extends OptionDao {

    public static final String PLAYER_OPTION_PREFIX = "player_option:";


    public OptionPlayerDao(AbstractRedisManager redisManager) {
        super(redisManager);
    }


    public void set(UUID uuid, String key, Object value) {
        set(PLAYER_OPTION_PREFIX + uuid.toString(), key, value);
    }

    public void set(UUID uuid, Map<String, String> options) {
        set(PLAYER_OPTION_PREFIX + uuid.toString(), options);
    }

    public boolean exists(UUID uuid, String key) {
        return exists(PLAYER_OPTION_PREFIX + uuid.toString(), key);
    }

    public Object get(UUID uuid, String key) {
        return get(PLAYER_OPTION_PREFIX + uuid.toString(), key);
    }

    public Map<String, String> getAll(UUID uuid) {
        return getAll(PLAYER_OPTION_PREFIX + uuid.toString());
    }

    public void delete(UUID uuid, String key) {
        delete(PLAYER_OPTION_PREFIX + uuid.toString(), key);
    }

}