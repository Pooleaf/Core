package net.pooleaf.core.modules.support.bukkit.util;

import com.google.gson.*;
import net.pooleaf.core.modules.support.common.util.GsonUtil;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.lang.reflect.Type;
import java.util.Map;

public class BukkitGsonUtil {

    public static class ConfigurationSerializableSerializer implements JsonSerializer<ConfigurationSerializable> {

        @Override
        public JsonElement serialize(ConfigurationSerializable configurationSerializable, Type type, JsonSerializationContext jsonSerializationContext) {
            Map<String, Object> serializedMap = BukkitSerializeUtil.serializeToMap(configurationSerializable);
            return GsonUtil.getGson().toJsonTree(serializedMap);
        }

    }

    public static class ConfigurationDeserializableSerializer implements JsonDeserializer<ConfigurationSerializable> {

        @Override
        public ConfigurationSerializable deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonObject()) {
                return null;
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (!jsonObject.has("==")) {
                return null;
            }

            return (ConfigurationSerializable) BukkitSerializeUtil.deserializeFromJson(jsonElement.toString());
        }
    }

}
