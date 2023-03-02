package net.pooleaf.core.modules.support.bukkit.util;

import com.google.gson.*;
import net.pooleaf.core.modules.support.common.util.GsonUtil;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Map;

public class BukkitGsonUtil {

    public static class ConfigurationSerializableSerializer implements JsonSerializer<ItemStack> {

        @Override
        public JsonElement serialize(ItemStack configurationSerializable, Type type, JsonSerializationContext jsonSerializationContext) {
            Map<String, Object> serializedMap = BukkitSerializeUtil.serializeToMap(configurationSerializable);
            return GsonUtil.getGson().toJsonTree(serializedMap);
        }

    }

    public static class ConfigurationDeserializableSerializer implements JsonDeserializer<ItemStack> {

        @Override
        public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonObject()) {
                return null;
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (!jsonObject.has("==")) {
                return null;
            }

            return (ItemStack) BukkitSerializeUtil.deserializeFromJson(jsonElement.toString());
        }
    }

}
