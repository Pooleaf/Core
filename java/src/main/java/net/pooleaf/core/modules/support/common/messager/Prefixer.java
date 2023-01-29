package net.pooleaf.core.modules.support.common.messager;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;

import net.pooleaf.core.Core;
import net.pooleaf.core.plugin.CorePlugin;
import lombok.Getter;

public class Prefixer {

  @Getter
  private static Map<String, String> prefixes = new HashMap<>();


  /**
   * 해당 Package의 Prefix를 설정합니다.
   * @param prefix 설정할 Prefix
   */
  public static void registerPrefix(String prefix) {
    // 패키지로 CorePlugin 찾아서 prefix 찾기
    String classPackage = Core.getLastClassName();
    CorePlugin plugin = Core.getPluginManager().getPluginByPackage(classPackage);
    Preconditions.checkNotNull(plugin, "해당 Package의 CorePlugin을 찾을 수 없습니다. (Package: %s)", classPackage);

    registerPrefix(plugin.getPluginPackage(), prefix);
  }

  /**
   * 해당 Package의 Prefix를 설정합니다.
   * @param prefix 설정할 Prefix
   */
  public static void registerPrefix(String pluginPackage, String prefix) {
    if (prefix == null) {
      prefixes.remove(pluginPackage);
    } else {
      prefixes.put(pluginPackage, prefix);
    }
  }

  /**
   * 현재 CorePlugin의 Prefix를 불러옵니다.
   * @return 현재 CorePlugin의 Prefix
   */
  protected static String getCurrentPluginPrefix(String suffix) {
    CorePlugin plugin = Core.getPluginManager().getCurrentPlugin();
    return getPluginPrefix(plugin, suffix);
  }

  /**
   * 패키지로 CorePlugin를 찾아 Prefix를 불러옵니다.
   */
  protected static String getPluginPrefix(String classPackage, String suffix) {
    CorePlugin plugin = Core.getPluginManager().getPluginByPackage(classPackage);
    return getPluginPrefix(plugin, suffix);
  }

  /**
   * CorePlugin으로 Prefix를 불러옵니다.
   */
  protected static String getPluginPrefix(CorePlugin plugin, String suffix) {
    String prefix = null;

    // CorePlugin으로 Prefix 찾기
    if (plugin != null) {
      prefix = prefixes.get(plugin.getPluginPackage()) + suffix;
    }

    // Prefix 못찾았으면 Core Prefix로
    if (prefix == null) {
      prefix = Core.getPlugin().getPrefix() + suffix;
    }

    return prefix;
  }

}
