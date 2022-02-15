package net.pooleaf.core.modules.commonsender.bungee;

import com.google.common.base.Preconditions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.commonsender.CommonSenderAdapter;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.bungee.listener.BungeePlayerListener;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;

public class BungeeSenderAdapter extends CommonSenderAdapter<BungeePlayer, BungeeConsoleSender> {

  @Override
  public void registerListeners() {
    ProxyServer.getInstance().getPluginManager().registerListener((Plugin) Core.getPlugin(), new BungeePlayerListener());
  }

  @Override
  public BungeePlayer getPlayerByPlatformSender(Object platformSender) {
    Preconditions.checkArgument(platformSender instanceof ProxiedPlayer, "platformSender가 ProxiedPlayer가 아닙니다.");

    return (BungeePlayer) CommonSenderModule.getPlayer(((ProxiedPlayer) platformSender).getUniqueId());
  }

  @Override
  public CommonCommandSender getCommandSenderByPlatformSender(Object platformSender) {
    Preconditions.checkArgument(platformSender instanceof CommandSender, "platformSender가 CommandSender가 아닙니다.");

    if (!(platformSender instanceof ProxiedPlayer)) { // 콘솔일 경우
      return CommonSenderModule.getConsoleSender();
    } else { // 플레이어일 경우
      return getPlayerByPlatformSender(platformSender);
    }
  }

}
