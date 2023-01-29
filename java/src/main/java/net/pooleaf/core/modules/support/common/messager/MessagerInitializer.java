package net.pooleaf.core.modules.support.common.messager;


import net.pooleaf.core.modules.support.bukkit.messager.BukkitMessagerAdapter;
import net.pooleaf.core.modules.support.bungee.messager.BungeeMessagerAdapter;
import net.pooleaf.core.modules.support.common.platform.Platform;

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
