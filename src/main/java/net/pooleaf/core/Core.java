package net.pooleaf.core;

import lombok.Getter;
import lombok.SneakyThrows;
import net.pooleaf.core.module.ModuleManager;
import net.pooleaf.core.plugin.CorePlugin;
import net.pooleaf.core.plugin.CorePluginManager;
import net.pooleaf.core.sql.CoreSqlManager;

import java.io.File;

public class Core {

  @Getter
  private static CorePlugin plugin;

  @Getter
  private static ModuleManager moduleManager = new ModuleManager();

  @Getter
  private static CorePluginManager corePluginManager = new CorePluginManager();

  @Getter
  private static CoreSqlManager coreSqlManager;


  protected static void init(CorePlugin plugin) {
    Core.plugin = plugin;

    // Manager 초기화
    coreSqlManager = new CoreSqlManager();

    // 모듈 자동 등록
    moduleManager.registerModules();

    // 모듈 초기화
    moduleManager.initModules();
  }

  /**
   * 마지막으로 사용한 클래스 이름을 반환합니다.
   * @return 마지막으로 사용한 클래스 이름
   */
  public static String getLastClassName() {
    StackTraceElement[] ste = new Throwable().getStackTrace();

    for (int i = 0; i < ste.length; i++) {
      if (!ste[i].getClassName().startsWith(Core.class.getPackageName())) {
        return ste[i].getClassName();
      }
    }

    return null;
  }

  /**
   * 서버 폴더 이름을 반환합니다.
   * @return 서버 폴더 이름
   */
  @SneakyThrows
  public static String getServerFolderName() {
    String path = new File("").getCanonicalPath();
    String folderName = path.substring(path.lastIndexOf(File.separator) + 1, path.length());
    return folderName;
  }

}