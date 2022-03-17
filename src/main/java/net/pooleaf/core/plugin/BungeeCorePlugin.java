package net.pooleaf.core.plugin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.annocommand.AnnoCommandModule;
import net.pooleaf.core.modules.annoconfig.common.SimpleAnnoConfig;
import net.pooleaf.core.modules.commonconfig.CommonConfigModule;
import net.pooleaf.core.modules.commonconfig.common.CommonConfig;
import net.pooleaf.core.modules.commonevent.CommonEventModule;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.bungee.util.BungeeReflectionUtil;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.pooleaf.core.modules.support.common.messager.Messager;

import java.io.File;
import java.io.InputStream;

public abstract class BungeeCorePlugin extends Plugin implements CorePlugin {

  @Setter
  @Getter
  private String prefix;

  @Setter
  @Getter
  private CommonChatColor color = CommonChatColor.WHITE;

  @Getter
  private boolean enabled;

  @Setter(AccessLevel.PROTECTED)
  @Getter
  private SimpleAnnoConfig annoConfig;

  @Getter
  private CommonConfig commonConfig;


  @Override
  public void onEnable() {
    Core.getPluginManager().register(this);
    setPrefix("§f[ " + getName() + " ] ");

    commonConfig = CommonConfigModule.createConfig(new File(getDataFolder(), "config.yml"));

    onStart();
    enabled = true;
  }

  @Override
  public void onDisable() {
    onEnd();
    enabled = false;
  }

  @Override
  public void onStart() {}

  @Override
  public void onEnd() {}

  @Override
  public void onConfigLoaded() {}

  @Override
  public void loadConfig() {
    loadConfig(null);
  }

  @Override
  public void loadConfig(CommonCommandSender sender) {
    long startTime = System.currentTimeMillis();

    if (!commonConfig.getKeys("").isEmpty()) {
      commonConfig.load();
      commonConfig.save();
    }

    if (annoConfig != null) {
      annoConfig.load();
      annoConfig.save();
    }

    onConfigLoaded();

    String message = "설정을 불러왔습니다. (" + (System.currentTimeMillis() - startTime) + " ms)";
    Logger.log(message);
    if (sender != null && !sender.isConsole()) {
      sender.message(message);
    }
  }

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

  @Override
  public int registerEventListeners() {
    return BungeeReflectionUtil.registerListeners(this);
  }

  @Override
  public void registerCommonEventListeners() {
    CommonEventModule.registerListeners(this);
  }

  @Override
  public void registerCommands() {
    AnnoCommandModule.registerCommands(this);
  }

}
