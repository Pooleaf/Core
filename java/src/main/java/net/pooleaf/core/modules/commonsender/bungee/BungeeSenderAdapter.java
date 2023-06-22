package net.pooleaf.core.modules.commonsender.bungee;

import com.google.common.base.Preconditions;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.commonsender.common.CommonSenderAdapter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;

public class BungeeSenderAdapter extends CommonSenderAdapter<BungeePlayer, BungeeConsoleSender> {

  @Override
  public BungeePlayer getPlayerByPlatformSenderWithoutCache(Object platformSender) {
    Preconditions.checkArgument(platformSender instanceof ProxiedPlayer, "platformSender가 ProxiedPlayer가 아닙니다.");

    return (BungeePlayer) CommonSenderModule.getOfflinePlayer(((ProxiedPlayer) platformSender).getUniqueId());
  }

  @Override
  public CommonCommandSender getCommandSenderByPlatformSenderWithoutCache(Object platformSender) {
    Preconditions.checkArgument(platformSender instanceof CommandSender, "platformSender가 CommandSender가 아닙니다.");

    // 콘솔일 경우
    if (!(platformSender instanceof ProxiedPlayer)) {
      return CommonSenderModule.getConsoleSender();
    }
    // 플레이어일 경우
    else {
      return getPlayerByPlatformSenderWithoutCache(platformSender);
    }
  }

}
