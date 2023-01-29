package net.pooleaf.core.modules.commonconfig.bukkit;

import lombok.SneakyThrows;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

public class BukkitConfigUtil {

    @SneakyThrows
    public static void enableUtf8Config() {
        Field utf8OverrideField = FileConfiguration.class.getDeclaredField("UTF8_OVERRIDE");
        ReflectionUtil.removeFinal(utf8OverrideField);
        utf8OverrideField.set(null, true);

        Field systemUtfField = FileConfiguration.class.getDeclaredField("SYSTEM_UTF");
        ReflectionUtil.removeFinal(systemUtfField);
        systemUtfField.set(null, true);
    }

}
