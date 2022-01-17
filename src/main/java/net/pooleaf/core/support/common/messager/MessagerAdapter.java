package net.pooleaf.core.support.common.messager;

public interface MessagerAdapter {

  void message(Object sender, Object message);

  void broadcast(Object message);

}
