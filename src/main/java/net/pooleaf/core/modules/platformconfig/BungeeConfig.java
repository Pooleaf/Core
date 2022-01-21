package net.pooleaf.core.modules.platformconfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeeConfig extends Config {

  private Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load("");


  protected BungeeConfig(File file) {
    super(file);
  }

  @Override
  @SneakyThrows(IOException.class)
  public void save() {
    createFile();
    ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, getFile());
  }

  @Override
  @SneakyThrows(IOException.class)
  public Config load() {
    if (getFile().exists()) {
      config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getFile());
    }

    return this;
  }

  @Override
  public Config copyDefaults(String resourcePath) {
    Configuration tempConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getClass().getClassLoader().getResourceAsStream(resourcePath));

    for (String key : tempConfig.getKeys()) {
      setDefault(key, tempConfig.get(key));
    }

    return this;
  }

  @Override
  public void clearValues() {
    for (String key : config.getKeys()) {
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
    return new ArrayList(config.getSection(path).getKeys());
  }

  @Override
  public boolean isSet(String path) {
    return config.get(path) != null;
  }

  @Override
  public boolean isString(String path) {
    Object val = get(path);
    return val != null && val instanceof String;
  }

  @Override
  public String getString(String path) {
    return isSet(path) ? config.getString(path) : null;
  }

  @Override
  public String getString(String path, String defaultValue) {
    return config.getString(path, defaultValue);
  }

  @Override
  public boolean isBoolean(String path) {
    Object val = get(path);
    return val != null && val instanceof Boolean;
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
    Object val = get(path);
    return val != null && val instanceof Integer;
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
    Object val = get(path);
    return val != null && val instanceof Long;
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
    Object val = get(path);
    return val != null && val instanceof Double;
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
    Object val = get(path);
    return val != null && val instanceof List;
  }

  @Override
  public List<?> getList(String path) {
    return config.getList(path);
  }

  @Override
  public List<?> getList(String path, List<?> defaultValue) {
    return config.getList(path, defaultValue);
  }

  @Override
  public List<String> getStringList(String path) {
    return config.getStringList(path);
  }

}
