package net.pooleaf.core.modules.commonsender;

import java.util.UUID;

import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.commonsender.common.CommonConsoleSender;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;

public abstract class CommonSenderAdapter<P extends CommonPlayer, C extends CommonConsoleSender> {

  public abstract void registerListeners();

  public P getPlayer(UUID uuid) {
    return (P) CommonSenderModule.getPlayerInfoManager().getOrLoad(uuid);
  }

  public P getPlayerByName(String name) {
    return (P) CommonSenderModule.getPlayerInfoManager().getOrLoadByName(name);
  }

  public P getPlayerByDisplayName(String displayName) {
    return (P) CommonSenderModule.getPlayerInfoManager().getOrLoadByDisplayName(displayName);
  }

  public abstract P getCommonPlayerByPlatformSender(Object platformSender);

  public abstract CommonCommandSender getCommonCommandSenderByPlatformSender(Object platformSender);

}
