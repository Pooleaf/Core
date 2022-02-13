package net.pooleaf.core.modules.commonconfig.bukkit;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import net.pooleaf.core.modules.commonconfig.common.CommonConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class BukkitConfig extends CommonConfig {

  private FileConfiguration config = new YamlConfiguration();


  public BukkitConfig(File file) {
    super(file);
  }

  @Override
  @SneakyThrows(IOException.class)
  public void save() {
    createFile();
    config.save(getFile());
  }

  @Override
  public CommonConfig load() {
    if (getFile().exists()) {
      config = YamlConfiguration.loadConfiguration(getFile());
    }

    return this;
  }

  @Override
  public CommonConfig copyDefaults(String resourcePath) {
    FileConfiguration tempConfig = YamlConfiguration.loadConfiguration(
        new InputStreamReader(getClass().getClassLoader().getResourceAsStream(resourcePath)));

    for (String key : tempConfig.getKeys(true)) {
      setDefault(key, tempConfig.get(key));
    }

    return this;
  }

  @Override
  public void clearValues() {
    for (String key : config.getKeys(false)) {
      set(key, null);
    }
  }

  @Override
  public void set(String path, Object value) {
    config.set(path, value);
  }

  @Override
  public Object get(String path) {
    return config.get(path);
  }

  @Override
  public List<String> getKeys(String path) {
    return new ArrayList(config.getConfigurationSection(path).getKeys(false));
  }

  @Override
  public boolean isSet(String path) {
    return config.isSet(path);
  }

  @Override
  public boolean isString(String path) {
    return config.isString(path);
  }

  @Override
  public String getString(String path) {
    return config.getString(path);
  }

  @Override
  public String getString(String path, String defaultValue) {
    return config.getString(path, defaultValue);
  }

  @Override
  public boolean isBoolean(String path) {
    return config.isBoolean(path);
  }

  @Override
  public boolean getBoolean(String path) {
    return config.getBoolean(path);
  }

  @Override
  public boolean getBoolean(String path, boolean defaultValue) {
    return getBoolean(path, defaultValue);
  }

  @Override
  public boolean isInt(String path) {
    return config.isInt(path);
  }

  @Override
  public int getInt(String path) {
    return config.getInt(path);
  }

  @Override
  public int getInt(String path, int defaultValue) {
    return config.getInt(path, defaultValue);
  }

  @Override
  public boolean isLong(String path) {
    return config.isLong(path);
  }

  @Override
  public long getLong(String path) {
    return config.getLong(path);
  }

  @Override
  public long getLong(String path, long defaultValue) {
    return config.getLong(path, defaultValue);
  }

  @Override
  public boolean isDouble(String path) {
    return config.isDouble(path);
  }

  @Override
  public double getDouble(String path) {
    return config.getDouble(path);
  }

  @Override
  public double getDouble(String path, double defaultValue) {
    return config.getDouble(path, defaultValue);
  }

  @Override
  public boolean isList(String path) {
    return config.isList(path);
  }

  @Override
  public List<?> getList(String path) {
    return config.getList(path);
  }

  @Override
  public List<?> getList(String path, List<?> def) {
    return config.getList(path, def);
  }

  @Override
  public List<String> getStringList(String path) {
    return config.getStringList(path);
  }

  public boolean isItemStack(String path) {
    return config.isItemStack(path);
  }

  public ItemStack getItemStack(String path) {
    return config.getItemStack(path);
  }

  public ItemStack getItemStack(String path, ItemStack defaultValue) {
    return config.getItemStack(path, defaultValue);
  }

}
