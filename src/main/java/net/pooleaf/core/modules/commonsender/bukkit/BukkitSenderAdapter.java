package net.pooleaf.core.modules.commonsender.bukkit;

import com.google.common.base.Preconditions;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.commonsender.common.CommonSenderAdapter;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.bukkit.listener.BukkitPlayerListener;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BukkitSenderAdapter extends CommonSenderAdapter<BukkitPlayer, BukkitConsoleSender> {

  @Override
  public void registerListeners() {
    Bukkit.getPluginManager().registerEvents(new BukkitPlayerListener(),(Plugin) Core.getPlugin());
  }

  @Override
  public BukkitPlayer getPlayerByPlatformSender(Object platformSender) {
    Preconditions.checkArgument(platformSender instanceof Player, "platformSender가 Player가 아닙니다.");

    return (BukkitPlayer) CommonSenderModule.getPlayer(((Player) platformSender).getUniqueId());
  }

  @Override
  public CommonCommandSender getCommandSenderByPlatformSender(Object platformSender) {
    Preconditions.checkArgument(platformSender instanceof CommandSender, "platformSender가 CommandSender가 아닙니다.");

    if (platformSender instanceof ConsoleCommandSender) { // 콘솔일 경우
      return CommonSenderModule.getConsoleSender();
    } else { // 플레이어일 경우
      return getPlayerByPlatformSender(platformSender);
    }
  }

}
