package net.pooleaf.core.plugin;

import java.io.File;
import java.io.InputStream;

public interface CorePlugin {

  void onStart();
  void onEnd();

  String getName();
  String getPrefix();
  String getVersion();
  String getPluginPackage();

  File getFile();
  File getDataFolder();
  InputStream getResource(String path);

  boolean detectPlugin(String name);

}
