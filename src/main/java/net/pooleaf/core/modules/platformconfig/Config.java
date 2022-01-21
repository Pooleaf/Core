package net.pooleaf.core.modules.platformconfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public abstract class Config {

  @Setter
  @Getter
  private File file;


  @SneakyThrows(IOException.class)
  public boolean createFile() {
    if (getFile().exists()) return false;

    getFile().getParentFile().mkdirs();
    getFile().createNewFile();

    return true;
  }

  public abstract void save();
  public abstract Config load();

  public void setDefault(String path, Object value) {
    if (isSet(path)) return;

    set(path, value);
  }

  public Config copyDefaults() {
    return copyDefaults(getFile().getName());
  }

  public abstract Config copyDefaults(String resourcePath);

  public abstract void clearValues();

  public abstract void set(String path, Object value);
  public abstract Object get(String path);

  public abstract List<String> getKeys(String path);

  public abstract boolean isSet(String path);
  public abstract boolean isString(String path);

  public abstract String getString(String path);
  public abstract String getString(String path, String defaultValue);

  public abstract boolean isBoolean(String path);
  public abstract boolean getBoolean(String path);
  public abstract boolean getBoolean(String path, boolean defaultValue);

  public abstract boolean isInt(String path);
  public abstract int getInt(String path);
  public abstract int getInt(String path, int defaultValue);

  public abstract boolean isLong(String path);
  public abstract long getLong(String path);
  public abstract long getLong(String path, long defaultValue);

  public abstract boolean isDouble(String path);
  public abstract double getDouble(String path);
  public abstract double getDouble(String path, double defaultValue);

  public abstract boolean isList(String path);
  public abstract List<?> getList(String path);
  public abstract List<?> getList(String path, List<?> defaultValue);
  public abstract List<String> getStringList(String path);

}