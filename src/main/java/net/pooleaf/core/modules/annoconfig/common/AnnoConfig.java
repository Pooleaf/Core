package net.pooleaf.core.modules.annoconfig.common;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.SneakyThrows;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigAes256;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigExclude;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigName;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigSerialize;
import net.pooleaf.core.modules.commonconfig.common.CommonConfig;
import net.pooleaf.core.modules.commonconfig.common.CommonConfigFactory;
import net.pooleaf.core.modules.support.common.util.EncryptionUtil;
import net.pooleaf.core.modules.support.common.util.NumberUtil;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AnnoConfig {

    @Getter
    private static Map<Class, Class<? extends ConfigSerialize.ConfigSerializer>> defaultSerializer = new HashMap<>();


    /**
     * 해당 File의 설정을 해당 객체 Field에 불러옵니다.
     * 부모 Class의 Field에도 적용됩니다.
     * @param file 설정을 불러올 File
     * @param configObject 값이 적용될 객체
     */
    @SneakyThrows
    public static void load(File file, Object configObject) {
        CommonConfig config = CommonConfigFactory.createConfig(file).load();

        for (Field field : ReflectionUtil.getAllField(configObject.getClass())) {
            if (field.getAnnotation(ConfigExclude.class) != null) continue;

            field.setAccessible(true);

            String name;
            if (field.getAnnotation(ConfigName.class) == null) {
                name = field.getName();
            } else {
                name = field.getAnnotation(ConfigName.class).value();
            }

            Object value = config.get(name);

            if (value != null) {
                if (field.getAnnotation(ConfigAes256.class) != null) {
                    String key = field.getAnnotation(ConfigAes256.class).value();
                    Preconditions.checkArgument(key.length() >= 16, name + ": AES256의 Key는 16글자여야 합니다.");

                    try {
                        value = EncryptionUtil.decryptAes256(key, value.toString());
                    } catch (Exception e) {
                        value = value;
                    }
                } else if (field.getAnnotation(ConfigSerialize.class) != null) {
                    value = field.getAnnotation(ConfigSerialize.class).value().newInstance().deserialize(value.toString());
                } else if (defaultSerializer.containsKey(field.getType())) {
                    value = defaultSerializer.get(field.getType()).newInstance().deserialize(value.toString());
                }

                if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
                    value = Float.parseFloat(value.toString());
                }

                field.set(configObject, value);
            }
        }
    }

    /**
     * 해당 객체의 Field 값들을 해당 File에 저장합니다.
     * 부모 Class의 Field에도 적용됩니다.
     * @param file 값이 저장될 File
     * @param configObject 저장할 객체
     */
    @SneakyThrows
    public static void save(File file, Object configObject) {
        CommonConfig config = CommonConfigFactory.createConfig(file).load();

        for (Field field : ReflectionUtil.getAllField(configObject.getClass())) {
            if (field.getAnnotation(ConfigExclude.class) != null) continue;

            field.setAccessible(true);

            String name;
            if (field.getAnnotation(ConfigName.class) == null) {
                name = field.getName();
            } else {
                name = field.getAnnotation(ConfigName.class).value();
            }

            Object value = field.get(configObject);

            if (value != null) {
                if (field.getAnnotation(ConfigAes256.class) != null) {
                    String key = field.getAnnotation(ConfigAes256.class).value();
                    Preconditions.checkArgument(key.length() >= 16, name + ": AES256의 Key는 16글자여야 합니다.");

                    value = EncryptionUtil.encryptAes256(key, value.toString());
                } else if (field.getAnnotation(ConfigSerialize.class) != null) {
                    value = field.getAnnotation(ConfigSerialize.class).value().newInstance().serialize(value);
                } else if (defaultSerializer.containsKey(field.getType())) {
                    value = defaultSerializer.get(field.getType()).newInstance().serialize(value);
                }
            }

            config.set(name, value);
        }

        config.save();
    }

}
