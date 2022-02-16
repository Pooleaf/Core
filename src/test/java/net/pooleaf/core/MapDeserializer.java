package net.pooleaf.core;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapDeserializer implements JsonDeserializer<Map<String, Object>> {

    @Override
    public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (Map<String, Object>) convertInt(json);
    }

    public Object convertInt(JsonElement json) {
        if (json.isJsonArray()) {
            List<Object> list = new ArrayList<>();

            for (JsonElement jsonElement : json.getAsJsonArray()) {
                list.add(convertInt(jsonElement));
            }

            return list;
        } else if (json.isJsonObject()) {
            Map<String, Object> map = new HashMap<>();

            for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                map.put(entry.getKey(), convertInt(entry.getValue()));
            }

            return map;
        } else if (json.isJsonPrimitive()) {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            } else if (primitive.isString()) {
                return primitive.getAsString();
            } else if (primitive.isNumber()) {
                Number number = primitive.getAsNumber();

                if (!number.toString().contains(".")) { // 소수점 안붙어있는 숫자면 Long으로 불러옴
                    return number.longValue();
                } else {
                    return number.doubleValue();
                }
            }
        }

        return null;
    }

}
