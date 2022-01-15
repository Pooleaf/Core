package net.pooleaf.core.plugin;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class CorePluginManager {

  @Getter
  private static Map<String, CorePlugin> plugins = new HashMap<>();


  /**
   * 플러그인의 Package로 플러그인을 등록합니다.
   * @param plugin 등록할 CorePlugin
   */
  public static void registerPlugin(CorePlugin plugin) {
    plugins.put(plugin.getPluginPackage(), plugin);
  }

  /**
   * CorePlugin 등록을 해제합니다.
   * @param plugin 등록 해제할 CorePlugin
   */
  public static void unregisterPlugin(CorePlugin plugin) {
    plugins.remove(plugin.getPluginPackage());
  }

  /**
   * Class 패키지로 CorePlugin을 찾습니다.
   * @param classPackage Class 패키지
   * @return 해당 Class의 CorePlugin
   */
  public static CorePlugin getPluginByPackage(String classPackage) {
    String startClassPackage = classPackage + ".";
    for (String pluginPackage : plugins.keySet()) {
      if (classPackage.startsWith(pluginPackage) // 현재 Class 패키지가 플러그인 패키지와 같거나
        || classPackage.startsWith(startClassPackage)) { // 현재 Class 패키지가 플러그인 패키지로 시작하면
        return plugins.get(pluginPackage);
      }
    }

    return null;
  }

  /**
   * 플러그인 이름으로 CorePlugin을 찾습니다.
   * @param name 플러그인 이름
   * @return 해당 이름의 CorePlugin
   */
  public static CorePlugin getPluginByName(String name) {
    for (CorePlugin plugin : plugins.values()) {
      if (plugin.getName().equalsIgnoreCase(name)) {
        return plugin;
      }
    }

    return null;
  }

}
