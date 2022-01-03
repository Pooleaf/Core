package net.pooleaf.core.annocommand;

import lombok.experimental.UtilityClass;
import net.pooleaf.core.annocommand.bukkit.BukkitPlatformAdapter;
import net.pooleaf.core.annocommand.bungee.BungeePlatformAdapter;

@UtilityClass
public class PlatformAdapterFactory {

    public static PlatformAdapter createPlatformAdapter() {
        if (existsClass("org.bukkit.Bukkit")) return new BukkitPlatformAdapter();
        else if (existsClass("net.md_5.bungee.api.ProxyServer")) return new BungeePlatformAdapter();

        return null;
    }

    private static boolean existsClass(String className) {
        try {
            Class.forName(className);

            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
