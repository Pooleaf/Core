package net.pooleaf.core;

import java.io.File;
import java.io.InputStream;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class BungeeCorePlugin extends Plugin implements CorePlugin {

  @Override
  public void onEnable() {
    Core.init(this);

    super.onEnable();
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }

  public void onStart() {}

  public void onEnd() {}

  @Override
  public String getName() {
    return getDescription().getName();
  }

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
  public InputStream getResource(String path) {
    return getResourceAsStream(path);
  }

  @Override
  public boolean detectPlugin(String name) {
    return ProxyServer.getInstance().getPluginManager().getPlugin(name) != null;
  }

}
