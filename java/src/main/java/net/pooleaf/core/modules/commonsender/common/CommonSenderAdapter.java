package net.pooleaf.core.modules.commonsender.common;

import net.pooleaf.core.modules.commonsender.CommonSenderModule;

import java.util.UUID;

public abstract class CommonSenderAdapter<P extends CommonPlayer, C extends CommonConsoleSender> {

  public P getPlayerWithoutCache(UUID uuid) {
    return (P) CommonSenderModule.getCommonPlayerManager().getOrLoadWithoutCache(uuid);
  }

  public P getPlayerByNameWithoutCache(String name) {
    return (P) CommonSenderModule.getCommonPlayerManager().getOrLoadByNameWithoutCache(name);
  }

  public P getPlayerByDisplayNameWithoutCache(String displayName) {
    return (P) CommonSenderModule.getCommonPlayerManager().getOrLoadByDisplayNameWithoutCache(displayName);
  }

  public abstract P getPlayerByPlatformSenderWithoutCache(Object platformSender);

  public abstract CommonCommandSender getCommandSenderByPlatformSenderWithoutCache(Object platformSender);

}
