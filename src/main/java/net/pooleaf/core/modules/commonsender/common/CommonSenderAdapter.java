package net.pooleaf.core.modules.commonsender.common;

import java.util.UUID;

import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.commonsender.common.CommonConsoleSender;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;

public abstract class CommonSenderAdapter<P extends CommonPlayer, C extends CommonConsoleSender> {

  public abstract void registerListeners();

  public P getPlayer(UUID uuid) {
    return (P) CommonSenderModule.getCommonPlayerManager().getOrLoadNoCache(uuid);
  }

  public P getPlayerByName(String name) {
    return (P) CommonSenderModule.getCommonPlayerManager().getOrLoadNoCacheByName(name);
  }

  public P getPlayerByDisplayName(String displayName) {
    return (P) CommonSenderModule.getCommonPlayerManager().getOrLoadNoCacheByDisplayName(displayName);
  }

  public abstract P getPlayerByPlatformSender(Object platformSender);

  public abstract CommonCommandSender getCommandSenderByPlatformSender(Object platformSender);

}
