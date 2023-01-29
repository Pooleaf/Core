package net.pooleaf.core.modules.support.common.logger;

import lombok.AccessLevel;
import lombok.Setter;
import net.pooleaf.core.modules.support.common.messager.Prefixer;

public class Logger extends Prefixer {

  @Setter(AccessLevel.PROTECTED)
  private static LoggerAdapter loggerAdapter;


  public static boolean isInitialized() {
    return loggerAdapter != null;
  }

  private static String getThreadId() {
    return "[" + Thread.currentThread().getId() + "]";
  }

  public static void log(Object log) {
    loggerAdapter.log(getThreadId() + getCurrentPluginPrefix(" §f") + log);
  }

  public static void log(String classPackage, Object log) {
    loggerAdapter.log(getThreadId() + getPluginPrefix(classPackage, " §f") + log);
  }

  public static void nlog(Object log) {
    loggerAdapter.log(getThreadId() + log);
  }

  public static void warning(Object log) {
    loggerAdapter.log(getThreadId() + getCurrentPluginPrefix(" §c") + log);
  }

  public static void warning(String classPackage, Object log) {
    loggerAdapter.log(getThreadId() + getPluginPrefix(classPackage, " §c") + log);
  }

}
