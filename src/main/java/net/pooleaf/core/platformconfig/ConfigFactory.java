package net.pooleaf.core.platformconfig;

import java.io.File;

public class ConfigFactory {

  public static Config createConfig(File file) {
    if (existsClass("org.bukkit.Bukkit")) return new BukkitConfig(file);
    else if (existsClass("net.md_5.bungee.api.ProxyServer")) return new BungeeConfig(file);

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
