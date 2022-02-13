package net.pooleaf.core.modules.commonplayer;

import java.util.UUID;
import net.pooleaf.core.modules.commonplayer.common.CommonPlayer;

public abstract class CommonPlayerService<E extends CommonPlayer> {

  public abstract void registerListeners();

  public E getPlayer(UUID uuid) {
    return (E) CommonPlayerModule.getPlayerInfoManager().getOrLoad(uuid);
  }

  public E getPlayerByName(String name) {
    return (E) CommonPlayerModule.getPlayerInfoManager().getOrLoadByName(name);
  }

  public E getPlayerByDisplayName(String displayName) {
    return (E) CommonPlayerModule.getPlayerInfoManager().getOrLoadByDisplayName(displayName);
  }

}
