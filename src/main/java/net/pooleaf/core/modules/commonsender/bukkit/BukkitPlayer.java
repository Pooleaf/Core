package net.pooleaf.core.modules.commonsender.bukkit;

import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitPlayer extends CommonPlayer<Player> {

  @Override
  public void setDisplayName(String displayName) {
    super.setDisplayName(displayName);

    // 온라인일 경우 실제 닉네임도 변경
    if (isOnline()) {
      getPlatformSender().setDisplayName(displayName);
    }
  }

  @Override
  public Player getPlatformSender() {
    return Bukkit.getPlayer(uuid);
  }

  @Override
  public boolean hasPermission(String node) {
    return getPlatformSender().hasPermission(node);
  }

  @Override
  public void kickPlayer(String message) {
    getPlatformSender().kickPlayer(message);
  }

}
