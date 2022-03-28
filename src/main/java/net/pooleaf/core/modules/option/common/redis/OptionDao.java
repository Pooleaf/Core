package net.pooleaf.core.modules.option.common.redis;

import lombok.SneakyThrows;
import net.pooleaf.core.modules.redislib.common.AbstractRedisManager;
import net.pooleaf.core.modules.redislib.common.RedisDao;
import org.bukkit.material.Tree;

import java.util.Map;
import java.util.TreeMap;

public class OptionDao extends RedisDao {

    public OptionDao(AbstractRedisManager redisManager) {
        super(redisManager);
    }


    public void set(String name, String key, Object value) {
        if (value == null) {
            redisManager.getAsyncCommands().hdel(name, key);
        } else {
            redisManager.getAsyncCommands().hset(name, key, value.toString());
        }
    }

    public void set(String name, Map<String, String> options) {
        redisManager.getAsyncCommands().hset(name, options);
    }

    @SneakyThrows
    public boolean exists(String name, String key) {
        return redisManager.getAsyncCommands().hexists(name, key).get();
    }

    @SneakyThrows
    public Object get(String name, String key) {
        return redisManager.getAsyncCommands().hget(name, key).get();
    }

    @SneakyThrows
    public Map<String, String> getAll(String name) {
        Map<String, String> datas = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        datas.putAll(redisManager.getAsyncCommands().hgetall(name).get());
        return datas;
    }

    public void delete(String name, String key) {
        redisManager.getAsyncCommands().hdel(name, key);
    }

}