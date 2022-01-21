package net.pooleaf.core.modules.support.common.messager;

public interface MessagerAdapter {

  void message(Object sender, Object message);

  void broadcast(Object message);

}
