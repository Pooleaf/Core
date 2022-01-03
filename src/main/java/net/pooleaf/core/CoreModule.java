package net.pooleaf.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class CoreModule {

  @Setter(AccessLevel.PROTECTED)
  @Getter
  boolean enable = false;


  public abstract String getName();

  public String[] getDepends() {
    return null;
  }

  public String[] getSoftDepends() {
    return null;
  }

  public void onEnable(CorePlugin plugin) {}

  public void onDisable(CorePlugin plugin) {}

}
