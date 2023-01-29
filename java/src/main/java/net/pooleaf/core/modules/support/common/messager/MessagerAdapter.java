package net.pooleaf.core.modules.support.common.messager;

import net.md_5.bungee.api.chat.BaseComponent;

public interface MessagerAdapter {

  void message(Object sender, Object message);
  void broadcast(Object message);

}
