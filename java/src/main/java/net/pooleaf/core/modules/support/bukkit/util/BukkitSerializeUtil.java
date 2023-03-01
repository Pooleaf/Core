package net.pooleaf.core.modules.support.bukkit.util;

import lombok.experimental.UtilityClass;
import net.pooleaf.core.modules.support.common.util.GsonUtil;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link{ConfigurationSerializable}} 객체를 직렬화하거나 역직렬화하는 유틸
 */
@UtilityClass
public class BukkitSerializeUtil {

    /**
     * {@link{ConfigurationSerializable}} 객체를 {@link{Map}}으로 직렬화합니다.
     */
    public static LinkedHashMap<String, Object> serializeToMap(ConfigurationSerializable configurationSerializable) {
        if (configurationSerializable == null) return null;

        LinkedHashMap<String, Object> serializedMap = new LinkedHashMap<>();
        serializedMap.put("==", ConfigurationSerialization.getAlias(configurationSerializable.getClass()));
        serializedMap.putAll(configurationSerializable.serialize());

        for (Map.Entry<String, Object> entry : serializedMap.entrySet()) {
            if (entry.getValue() instanceof ConfigurationSerializable) {
                serializedMap.put(entry.getKey(), serializeToMap((ConfigurationSerializable) entry.getValue()));
            }
        }

        return serializedMap;
    }

    /**
     * {@link{Map}} 객체를 역직렬화합니다.
     */
    public static Object deserializeFromMap(Map<String, Object> map) {
        if (map == null) return null;
        if (!map.containsKey("==")) return null;

        LinkedHashMap<String, Object> deserializedMap = null;
        if (map instanceof LinkedHashMap) {
            deserializedMap = (LinkedHashMap<String, Object>) map;
        } else {
            deserializedMap = new LinkedHashMap<>();
            deserializedMap.putAll(map);
        }

        for (Map.Entry<String, Object> entry : deserializedMap.entrySet()) {
            if (entry.getValue() instanceof Map && ((Map) entry.getValue()).containsKey("==")) {
                deserializedMap.put(entry.getKey(), deserializeFromMap((Map<String, Object>) entry.getValue()));
            }
        }

        return ConfigurationSerialization.deserializeObject(deserializedMap);
    }

    /**
     * {@link{ConfigurationSerializable}} 객체를 JSON으로 직렬화합니다.
     */
    public static String serializeToJson(ConfigurationSerializable configurationSerializable) {
        Map<String, Object> serializedMap = serializeToMap(configurationSerializable);
        if (serializedMap == null) {
            return null;
        }

        return GsonUtil.getGson().toJson(serializedMap);
    }

    /**
     * JSON을 역직렬화합니다.
     */
    public static Object deserializeFromJson(String json) {
        if (json == null) {
            return null;
        }

        Map<String, Object> jsonMap = GsonUtil.getGson().fromJson(json, Map.class);
        return deserializeFromMap(jsonMap);
    }

}