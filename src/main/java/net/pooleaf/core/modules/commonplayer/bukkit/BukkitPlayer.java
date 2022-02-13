package net.pooleaf.core.modules.commonplayer.bukkit;

import net.pooleaf.core.modules.commonplayer.common.CommonPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitPlayer extends CommonPlayer<Player> {

  @Override
  public Player getPlatformPlayer() {
    return Bukkit.getPlayer(uuid);
  }

  @Override
  public void kickPlayer(String message) {
    getPlatformPlayer().kickPlayer(message);
  }

}
