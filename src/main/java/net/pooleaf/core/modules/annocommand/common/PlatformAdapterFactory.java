package net.pooleaf.core.modules.annocommand.common;

import lombok.experimental.UtilityClass;
import net.pooleaf.core.modules.annocommand.bukkit.BukkitPlatformAdapter;
import net.pooleaf.core.modules.annocommand.bungee.BungeePlatformAdapter;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;

@UtilityClass
public class PlatformAdapterFactory {

    public static PlatformAdapter createPlatformAdapter() {
        if (ReflectionUtil.existsClass("org.bukkit.Bukkit")) return new BukkitPlatformAdapter();
        else if (ReflectionUtil.existsClass("net.md_5.bungee.api.ProxyServer")) return new BungeePlatformAdapter();

        return null;
    }

}
