package net.pooleaf.core.modules.commonsender.common;

import net.pooleaf.core.modules.commonsender.CommonSenderModule;

import java.util.UUID;

public abstract class CommonSenderAdapter<P extends CommonPlayer, C extends CommonConsoleSender> {

  public P getPlayer(UUID uuid) {
    return (P) CommonSenderModule.getCommonPlayerManager().getOrLoadWithoutCache(uuid);
  }

  public P getPlayerByName(String name) {
    return (P) CommonSenderModule.getCommonPlayerManager().getOrLoadByNameWithoutCache(name);
  }

  public P getPlayerByDisplayName(String displayName) {
    return (P) CommonSenderModule.getCommonPlayerManager().getOrLoadByDisplayNameWithoutCache(displayName);
  }

  public abstract P getPlayerByPlatformSender(Object platformSender);

  public abstract CommonCommandSender getCommandSenderByPlatformSender(Object platformSender);

}
