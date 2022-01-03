package net.pooleaf.core.support.common.logger;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Logger {

  @Setter(AccessLevel.PROTECTED)
  private static LoggerAdapter loggerAdapter;

  @Setter
  @Getter
  private static String prefix;


  public static void log(Object log) {
    loggerAdapter.log(prefix + log);
  }

  public static void warning(Object log) {
    log("§c" + log);
  }

}
