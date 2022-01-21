package net.pooleaf.core.plugin;

import net.pooleaf.core.modules.support.common.util.AbstractManager;

public class CorePluginManager extends AbstractManager<String, CorePlugin> {

  /**
   * 플러그인의 Package로 플러그인을 등록합니다.
   * @param plugin 등록할 CorePlugin
   */
  public void register(CorePlugin plugin) {
    set(plugin.getPluginPackage(), plugin);
  }

  /**
   * CorePlugin 등록을 해제합니다.
   * @param plugin 등록 해제할 CorePlugin
   */
  public void unregister(CorePlugin plugin) {
    remove(plugin.getPluginPackage());
  }

  /**
   * Class 패키지로 CorePlugin을 찾습니다.
   * @param classPackage Class 패키지
   * @return 해당 Class의 CorePlugin
   */
  public CorePlugin getPluginByPackage(String classPackage) {
    String startClassPackage = classPackage + ".";
    for (String pluginPackage : datas.keySet()) {
      if (classPackage.startsWith(pluginPackage) // 현재 Class 패키지가 플러그인 패키지와 같거나
        || classPackage.startsWith(startClassPackage)) { // 현재 Class 패키지가 플러그인 패키지로 시작하면
        return get(pluginPackage);
      }
    }

    return null;
  }

  /**
   * 플러그인 이름으로 CorePlugin을 찾습니다.
   * @param name 플러그인 이름
   * @return 해당 이름의 CorePlugin
   */
  public CorePlugin getPluginByName(String name) {
    for (CorePlugin plugin : datas.values()) {
      if (plugin.getName().equalsIgnoreCase(name)) {
        return plugin;
      }
    }

    return null;
  }

}
