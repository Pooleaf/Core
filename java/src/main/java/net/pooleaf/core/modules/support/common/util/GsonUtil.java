package net.pooleaf.core.modules.support.common.util;

import com.google.gson.*;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class GsonUtil {

    @Getter
    public static GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(Map.class, new MapDeserializer())
            .serializeNulls();

    @Getter
    private static Gson gson = createGson();


    public static Gson createGson() {
        return gsonBuilder.create();
    }

    public static Object loadFromJson(String json, Object object) {
        return loadFromJson(getGson(), json, object);
    }

    /**
     * Json을 불러와 해당 객체의 변수에 넣어줍니다.
     */
    @SneakyThrows
    public static Object loadFromJson(Gson gson, String json, Object object) {
        Object temp = gson.fromJson(json, object.getClass());

        for (Field field : ReflectionUtil.getAllField(object.getClass())) {
            field.setAccessible(true);

            Object value = field.get(temp);
            field.set(object, value);
        }

        return object;
    }


    static class MapDeserializer implements JsonDeserializer<Map<String, Object>> {

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

}
