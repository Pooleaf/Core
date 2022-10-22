package net.pooleaf.core.module;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.pooleaf.core.plugin.CorePlugin;

public abstract class CoreModule {

  @Setter(AccessLevel.PROTECTED)
  @Getter
  boolean enabled = false;


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
