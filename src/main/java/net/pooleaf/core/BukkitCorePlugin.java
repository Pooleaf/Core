package net.pooleaf.core;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

public class BukkitCorePlugin extends JavaPlugin implements CorePlugin {

  @Override
  public final void onEnable() {
    Core.init(this);

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

  @Override
  public File getFile() {
    return super.getFile();
  }

  @Override
  public boolean detectPlugin(String name) {
    return Bukkit.getPluginManager().getPlugin(name) != null;
  }

}
