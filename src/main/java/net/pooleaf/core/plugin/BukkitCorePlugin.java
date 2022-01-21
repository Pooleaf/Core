package net.pooleaf.core.plugin;

import java.io.File;
import lombok.Getter;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.support.common.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitCorePlugin extends JavaPlugin implements CorePlugin {

  @Getter
  private String prefix;


  @Override
  public final void onEnable() {
    Core.getCorePluginManager().register(this);
    setPrefix("§7[ " + getName() + " ] ");

    onStart();
  }

  @Override
  public void onDisable() {
    onEnd();
  }

  public void onStart() {}

  public void onEnd() {}

  @Override
  public String getVersion() {
    return getDescription().getVersion();
  }

  @Override
  public String getPluginPackage() {
    return getDescription().getMain().substring(0, getDescription().getMain().lastIndexOf("."));
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
    Logger.registerPrefix(getPluginPackage(), prefix);
  }

  @Override
  public File getFile() {
    return super.getFile();
  }

  @Override
  public boolean detectPlugin(String name) {
    return Bukkit.getPluginManager().getPlugin(name) != null;
  }

}
