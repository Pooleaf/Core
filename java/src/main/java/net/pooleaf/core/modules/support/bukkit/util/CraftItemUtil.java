package net.pooleaf.core.modules.support.bukkit.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

@UtilityClass
public class CraftItemUtil {

    // org.bukkit → nms
    @SneakyThrows
    public static Object asNMSCopy(ItemStack bukkitItemStack) {
        Class craftItemStackClass = BukkitReflectionUtil.getCraftBukkitClass("inventory.CraftItemStack");
        Method asNMSCopyMethod = ReflectionUtil.getMethod(craftItemStackClass, "asNMSCopy");
        return asNMSCopyMethod.invoke(null, bukkitItemStack);
    }

    // nms → org.bukkit
    @SneakyThrows
    public static Object asBukkitCopy(Object nmsItemStack) {
        Class craftItemStackClass = BukkitReflectionUtil.getCraftBukkitClass("inventory.CraftItemStack");
        Method asBukkitCopyMethod = ReflectionUtil.getMethod(craftItemStackClass, "asBukkitCopy");
        return asBukkitCopyMethod.invoke(null, nmsItemStack);
    }

}
