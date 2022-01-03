package net.pooleaf.core;

import lombok.Getter;

public class Core {

  @Getter
  private static CorePlugin plugin;


  public static void init(CorePlugin plugin) {
    Core.plugin = plugin;

    // 모듈 자동 등록
    ModuleManager.registerModules();

    // 모듈 초기화
    ModuleManager.initModules();
  }

}