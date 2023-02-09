package net.pooleaf.core.modules.commonsender.bukkit;

import com.cryptomorin.xseries.XSound;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.gui.bukkit.title.DefaultTitleBuilder;
import net.pooleaf.core.modules.gui.bukkit.title.Title;
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
  public boolean isOnline() {
    return getPlatformSender() != null && getPlatformSender().isOnline();
  }

  @Override
  public boolean hasPermission(String node) {
    return getPlatformSender().isOp() || getPlatformSender().hasPermission(node);
  }

  @Override
  public void kickPlayer(String message) {
    getPlatformSender().kickPlayer(message);
  }

  public void sendTitleSafely(Title title) {
    title.sendSafely(getPlatformSender());
  }

  public void sendTitleSafely(String title) {
    new DefaultTitleBuilder()
            .title(title)
            .build()
            .sendSafely(getPlatformSender());
  }

  public void sendTitleSafely(String title, String subtitle) {
    new DefaultTitleBuilder()
            .title(title)
            .subtitle(subtitle)
            .build()
            .sendSafely(getPlatformSender());
  }

  public void playSoundSafely(XSound sound) {
    if (isOnline()) {
      sound.play(getPlatformSender(), 1F, 1F);
    }
  }

  public void playSoundSafely(XSound sound, Float volume) {
    if (isOnline()) {
      sound.play(getPlatformSender(), volume, 1F);
    }
  }

  public void playSoundSafely(XSound sound, Float volume, Float pitch) {
    if (isOnline()) {
      sound.play(getPlatformSender(), volume, pitch);
    }
  }

}
