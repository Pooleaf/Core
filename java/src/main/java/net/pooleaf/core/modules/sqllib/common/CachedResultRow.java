package net.pooleaf.core.modules.sqllib.common;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;
import net.pooleaf.core.modules.support.common.util.StringUtil;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Getter
@ToString
public class CachedResultRow {

    private Map<String, Object> datas;


    protected CachedResultRow(Map<String, Object> datas) {
        this.datas = datas;
        Collections.unmodifiableMap(this.datas);
    }


    public boolean exists(String key) {
        return datas.containsKey(key);
    }

    public int size() {
        return datas.size();
    }

    public Object get(String key) {
        return datas.get(key);
    }

    public Object get(int index) {
        return datas.get(datas.keySet().toArray()[index]);
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public String getString(int index) {
        return (String) get(index);
    }

    public Boolean getBoolean(String key) {
        return (Boolean) get(key);
    }

    public Boolean getBoolean(int index) {
        return (Boolean) get(index);
    }

    public Integer getInt(String key) {
        return (Integer) get(key);
    }

    public Integer getInt(int index) {
        return (Integer) get(index);
    }

    public Long getLong(String key) {
        return (Long) get(key);
    }

    public Long getLong(int index) {
        return (Long) get(index);
    }

    public Double getDouble(String key) {
        return (Double) get(key);
    }

    public Double getDouble(int index) {
        return (Double) get(index);
    }

    public Timestamp getTimestamp(String key) {
        return (Timestamp) get(key);
    }

    public Timestamp getTimestamp(int index) {
        return (Timestamp) get(index);
    }

    public LocalDateTime getLocalDateTime(String key) {
        return getTimestamp(key).toLocalDateTime();
    }

    public LocalDateTime getLocalDateTime(int index) {
        return getTimestamp(index).toLocalDateTime();
    }

    public LocalDate getLocalDate(String key) {
        return getTimestamp(key).toLocalDateTime().toLocalDate();
    }

    public LocalDate getLocalDate(int index) {
        return getTimestamp(index).toLocalDateTime().toLocalDate();
    }

    public LocalTime getLocalTime(String key) {
        return getTimestamp(key).toLocalDateTime().toLocalTime();
    }

    public LocalTime getLocalTime(int index) {
        return getTimestamp(index).toLocalDateTime().toLocalTime();
    }

    public Blob getBlob(String key) {
        return (Blob) get(key);
    }

    public Blob getBlob(int index) {
        return (Blob) get(index);
    }

    @SneakyThrows
    public <T> T toObject(Class<T> objectClass) {
        T object = objectClass.newInstance();
        return toObject(object);
    }

    @SneakyThrows
    public <T> T toObject(T object) {
        for (String key : getDatas().keySet()) {
            Class objectClass = object.getClass();

            String targetFieldName = StringUtil.convertSnakeCaseToLowerCamelCase(key);
            Field targetField = ReflectionUtil.getFieldAll(objectClass, targetFieldName);
            if (targetField != null) {
                Object value = replaceValue(targetField, get(key));

                targetField.setAccessible(true);
                targetField.set(object, value);
            }
        }

        return object;
    }

    /**
     * 값을 해당 필드에 맞는 타입으로 변환해줍니다.
     */
    private Object replaceValue(Field targetField, Object value) {
        // Timestamp -> LocalDateTime 변환
        if (value instanceof Timestamp) {
            if (targetField.getType().isAssignableFrom(LocalDateTime.class)) {
                value = ((Timestamp) value).toLocalDateTime();
            } else if (targetField.getType().isAssignableFrom(LocalDate.class)) {
                value = ((Timestamp) value).toLocalDateTime().toLocalDate();
            } else if (targetField.getType().isAssignableFrom(LocalTime.class)) {
                value = ((Timestamp) value).toLocalDateTime().toLocalTime();
            }
        }

        // UUID 변환
        if (targetField.getType().isAssignableFrom(UUID.class)) {
            value = UUID.fromString((String) value);
        }

        return value;
    }

}
