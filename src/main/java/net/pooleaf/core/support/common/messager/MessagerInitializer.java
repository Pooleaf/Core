package net.pooleaf.core.support.common.messager;


import net.pooleaf.core.support.bukkit.messager.BukkitMessagerAdapter;
import net.pooleaf.core.support.bungee.messager.BungeeMessagerAdapter;
import net.pooleaf.core.support.common.platform.Platform;

public class MessagerInitializer {

  public static void init() {
    switch (Platform.getCurrentPlatform()) {
      case BUKKIT:
        Messager.setMessagerAdapter(new BukkitMessagerAdapter()); break;
      case BUNGEECORD:
        Messager.setMessagerAdapter(new BungeeMessagerAdapter()); break;
    }
  }

}
