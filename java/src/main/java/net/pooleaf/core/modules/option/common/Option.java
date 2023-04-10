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


    public Option set(String key, Object value) {
        if (value == null) {
            datas.remove(key);
        } else {
            String valueString;

            if (value instanceof List || value instanceof Map) {
                valueString = GsonUtil.getGson().toJson(value);
            } else {
                valueString = value.toString();
            }

            datas.put(key, valueString);
        }

        return this;
    }

    public Option delete(String key) {
        datas.remove(key);

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
        String value = getString(key);
        if (value == null) {
            return null;
        }

        return GsonUtil.getGson().fromJson(value, List.class);
    }

    public Map getMap(String key) {
        String value = getString(key);
        if (value == null) {
            return null;
        }

        return GsonUtil.getGson().fromJson(value, Map.class);
    }


    public boolean isPlayerOption() {
        return key.contains(OptionModule.getRedisManager().PLAYER_OPTION_PREFIX);
    }

    public boolean isServerOption() {
        return key.contains(OptionModule.getRedisManager().SERVER_OPTION_NAME);
    }


    public Option load() {
        datas = OptionModule.getRedisManager().option().getAll(key);
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
            saveDatas.put(field, datas.get(field));
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