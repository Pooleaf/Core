package net.pooleaf.core.modules.support.common.logger;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import net.pooleaf.core.modules.support.common.messager.Prefixer;

public class Logger extends Prefixer {

  @Setter(AccessLevel.PROTECTED)
  private static LoggerAdapter loggerAdapter;


  public static void log(Object log) {
    loggerAdapter.log(getCurrentPluginPrefix(" §f") + log);
  }

  public static void nlog(Object log) {
    loggerAdapter.log(log);
  }

  public static void warning(Object log) {
    loggerAdapter.log(getCurrentPluginPrefix(" §c") + log);
  }

}
