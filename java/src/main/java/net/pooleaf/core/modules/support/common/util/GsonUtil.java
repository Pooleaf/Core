package net.pooleaf.core.modules.support.common.util;

import com.google.gson.*;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class GsonUtil {

    @Getter
    public static GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .registerTypeAdapter(LocalTime.class, new LocalTimeSerializer())
            .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
            .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
            .setExclusionStrategies(new GsonExcludeStrategy())
            .serializeNulls();

    @Getter
    private static Gson gson = createGson();


    public static Gson createGson() {
        return gson = gsonBuilder.create();
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
            // static 제외
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            field.setAccessible(true);

            Object value = field.get(temp);
            field.set(object, value);
        }

        return object;
    }


    /**
     * 직렬화에서 제외시키는 어노테이션
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GsonExclude {
    }

    /**
     * 직렬화 제외 처리
     */
    public class GsonExcludeStrategy implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getAnnotation(GsonExclude.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }

    }

    private DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            try {
                return new JsonPrimitive(localDateTime.format(localDateTimeFormatter));
            } catch (Exception exception) {
                return null;
            }
        }
    }

    public static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                return LocalDateTime.parse(jsonElement.getAsString(), localDateTimeFormatter);
            } catch (Exception exception) {
                return null;
            }
        }

    }

    private DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static class LocalDateSerializer implements JsonSerializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
            try {
                return new JsonPrimitive(localDate.format(localDateFormatter));
            } catch (Exception exception) {
                return null;
            }
        }
    }

    public static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                return LocalDate.parse(jsonElement.getAsString(), localDateFormatter);
            } catch (Exception exception) {
                return null;
            }
        }

    }

    private DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static class LocalTimeSerializer implements JsonSerializer<LocalTime> {

        @Override
        public JsonElement serialize(LocalTime localTime, Type type, JsonSerializationContext jsonSerializationContext) {
            try {
                return new JsonPrimitive(localTime.format(localTimeFormatter));
            } catch (Exception exception) {
                return null;
            }
        }
    }

    public static class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {

        @Override
        public LocalTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                return LocalTime.parse(jsonElement.getAsString(), localTimeFormatter);
            } catch (Exception exception) {
                return null;
            }
        }

    }


}
