package net.pooleaf.core.plugin;

import java.io.File;
import java.io.InputStream;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.pooleaf.core.modules.support.common.messager.Messager;

public abstract class BungeeCorePlugin extends Plugin implements CorePlugin {

  @Getter
  private String prefix;


  @Override
  public void onEnable() {
    Core.getCorePluginManager().register(this);
    setPrefix("§7[ " + getName() + " ] ");

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

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public void registerLoggerPrefix() {
    Logger.registerPrefix(getPluginPackage(), prefix);
  }

  public void registerMessagerPrefix() {
    Messager.registerPrefix(getPluginPackage(), prefix);
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
