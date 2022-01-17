package net.pooleaf.core.support.common.logger;

import lombok.AccessLevel;
import lombok.Setter;
import net.pooleaf.core.support.common.messager.Prefixer;

public class Logger extends Prefixer {

  @Setter(AccessLevel.PROTECTED)
  private static LoggerAdapter loggerAdapter;


  public static void log(Object log) {
    loggerAdapter.log(getCurrentPluginPrefix() + " §f" + log);
  }

  public static void warning(Object log) {
    log(getCurrentPluginPrefix() + " §c" + log);
  }

}
