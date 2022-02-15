package net.pooleaf.core.plugin;

import java.io.File;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.annocommand.AnnoCommandModule;
import net.pooleaf.core.modules.annoconfig.common.SimpleAnnoConfig;
import net.pooleaf.core.modules.commonevent.CommonEventModule;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.bukkit.util.BukkitReflectionUtil;
import net.pooleaf.core.modules.support.common.debugger.Debugger;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.pooleaf.core.modules.support.common.messager.Messager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitCorePlugin extends JavaPlugin implements CorePlugin {

  @Getter
  private String prefix;

  @Getter
  private SimpleAnnoConfig coreConfig;


  @Override
  public final void onEnable() {
    Core.getCorePluginManager().register(this);
    setPrefix("§7[ " + getName() + " ] ");

    Debugger.addListener(Bukkit.getConsoleSender());

    onStart();
  }

  @Override
  public void onDisable() {
    onEnd();
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
    Preconditions.checkNotNull(coreConfig, "config가 설정되지 않았습니다.");

    long startTime = System.currentTimeMillis();

    coreConfig.load();
    coreConfig.save();

    onConfigLoaded();

    String message = "설정을 불러왔습니다. (" + (System.currentTimeMillis() - startTime) + " ms)";
    Logger.log(message);
    if (sender != null && !sender.isConsole()) {
      sender.message(message);
    }
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
  public boolean detectPlugin(String name) {
    return Bukkit.getPluginManager().getPlugin(name) != null;
  }

  @Override
  public int registerEventListeners() {
    return BukkitReflectionUtil.registerListeners(this);
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
