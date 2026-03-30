package net.pooleaf.core.modules.commonsender.bukkit;

import com.google.common.base.Preconditions;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.commonsender.common.CommonSenderAdapter;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

public class BukkitSenderAdapter extends CommonSenderAdapter<BukkitPlayer, BukkitConsoleSender> {

  @Override
  public BukkitPlayer getPlayerByPlatformSenderWithoutCache(Object platformSender) {
    Preconditions.checkArgument(platformSender instanceof Player, "platformSender가 Player가 아닙니다.");

    return (BukkitPlayer) CommonSenderModule.getOfflinePlayer(((Player) platformSender).getUniqueId());
  }

  @Override
  public CommonCommandSender getCommandSenderByPlatformSenderWithoutCache(Object platformSender) {
    Preconditions.checkArgument(platformSender instanceof CommandSender, "platformSender가 CommandSender가 아닙니다.");

    // 콘솔일 경우
    if (platformSender instanceof ConsoleCommandSender || platformSender instanceof RemoteConsoleCommandSender) {
      return CommonSenderModule.getConsoleSender();
    }
    // 플레이어일 경우
    else {
      return getPlayerByPlatformSenderWithoutCache(platformSender);
    }
  }

}
