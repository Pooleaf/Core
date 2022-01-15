package net.pooleaf.core;

import lombok.Getter;
import net.pooleaf.core.plugin.CorePlugin;

public class Core {

  @Getter
  private static CorePlugin plugin;


  protected static void init(CorePlugin plugin) {
    Core.plugin = plugin;

    // 모듈 자동 등록
    ModuleManager.registerModules();

    // 모듈 초기화
    ModuleManager.initModules();
  }

  public static String getLastClassName() {
    StackTraceElement[] ste = new Throwable().getStackTrace();

    for (int i = 0; i < ste.length; i++) {
      if (!ste[i].getClassName().startsWith(Core.class.getPackageName())) {
        return ste[i].getClassName();
      }
    }

    return null;
  }


}