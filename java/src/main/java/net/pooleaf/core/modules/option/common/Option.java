package net.pooleaf.core.modules.option.common;

import com.google.common.base.Preconditions;
import lombok.Data;
import net.pooleaf.core.modules.commonevent.CommonEventModule;
import net.pooleaf.core.modules.option.OptionModule;
import net.pooleaf.core.modules.option.common.events.PlayerOptionChangedEvent;
import net.pooleaf.core.modules.option.common.events.ServerOptionChangedEvent;
import net.pooleaf.core.modules.support.common.util.GsonUtil;

import java.util.*;


@Data
public class Option {

    private final String key;
    private Map<String, String> datas = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Map<String, Object> cacheDatas = new HashMap<>(); // List, Map 캐시


    public Option set(String key, Object value) {
        if (value == null) {
            delete(key);
        } else {
            String valueString;

            if (value instanceof List || value instanceof Map) {
                valueString = GsonUtil.getGson().toJson(value);
                cacheDatas.put(key, value);
            } else {
                valueString = value.toString();
            }

            datas.put(key, valueString);
        }

        return this;
    }

    public Option delete(String key) {
        datas.remove(key);
        cacheDatas.remove(key);

        return this;
    }

    public Option deleteAll() {
        datas.clear();
        cacheDatas.clear();

        return this;
    }

    public boolean exists(String key) {
        return datas.containsKey(key);
    }

    public int size() {
        return datas.size();
    }

    public String getString(String key) {
        return datas.get(key);
    }

    public Boolean getBoolean(String key) {
        String value = getString(key);
        if (value == null) {
            return null;
        }

        return Boolean.parseBoolean(value);
    }

    public Integer getInt(String key) {
        String value = getString(key);
        if (value == null) {
            return null;
        }

        return Integer.parseInt(value);
    }

    public Long getLong(String key) {
        String value = getString(key);
        if (value == null) {
            return null;
        }

        return Long.parseLong(value);
    }

    public Double getDouble(String key) {
        String value = getString(key);
        if (value == null) {
            return null;
        }

        return Double.parseDouble(value);
    }

    public List getList(String key) {
        List list = (List) cacheDatas.get(key);
        if (list != null) {
            return list;
        }

        String value = getString(key);
        if (value == null) {
            return null;
        }

        list = GsonUtil.getGson().fromJson(value, List.class);
        cacheDatas.put(key, list);

        return list;
    }

    public Map getMap(String key) {
        Map map = (Map) cacheDatas.get(key);
        if (map != null) {
            return map;
        }

        String value = getString(key);
        if (value == null) {
            return null;
        }

        map = GsonUtil.getGson().fromJson(value, Map.class);
        cacheDatas.put(key, map);

        return map;
    }


    public boolean isPlayerOption() {
        return key.contains(OptionModule.getRedisManager().PLAYER_OPTION_PREFIX);
    }

    public boolean isServerOption() {
        return key.contains(OptionModule.getRedisManager().SERVER_OPTION_NAME);
    }


    public Option load() {
        datas = OptionModule.getRedisManager().option().getAll(key);
        cacheDatas.clear();

        return this;
    }

    public Option save() {
        OptionModule.getRedisManager().option().set(key, datas);

        if (isPlayerOption()) {
            String uuidString = key.substring(OptionModule.getRedisManager().PLAYER_OPTION_PREFIX.length());
            UUID uuid = UUID.fromString(uuidString);

            PlayerOptionChangedEvent event = new PlayerOptionChangedEvent(uuid, datas.keySet());
            CommonEventModule.callEvent(event);
        } else if (isServerOption()) {
            ServerOptionChangedEvent event = new ServerOptionChangedEvent(datas.keySet());
            CommonEventModule.callEvent(event);
        }

        return this;
    }

    public Option save(String... fields) {
        Preconditions.checkNotNull(fields);
        Preconditions.checkArgument(fields.length != 0);

        Map<String, String> saveDatas = new HashMap<>();
        for (String field : fields) {
            if (datas.containsKey(field)) {
                saveDatas.put(field, datas.get(field));
            }
        }

        OptionModule.getRedisManager().option().set(key, saveDatas);

        if (isPlayerOption()) {
            String uuidString = key.substring(OptionModule.getRedisManager().PLAYER_OPTION_PREFIX.length());
            UUID uuid = UUID.fromString(uuidString);

            PlayerOptionChangedEvent event = new PlayerOptionChangedEvent(uuid, saveDatas.keySet());
            CommonEventModule.callEvent(event);
        } else if (isServerOption()) {
            ServerOptionChangedEvent event = new ServerOptionChangedEvent(saveDatas.keySet());
            CommonEventModule.callEvent(event);
        }

        return this;
    }

}