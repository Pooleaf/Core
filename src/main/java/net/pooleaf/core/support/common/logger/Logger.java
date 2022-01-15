package net.pooleaf.core.support.common.logger;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.pooleaf.core.Core;
import net.pooleaf.core.plugin.CorePlugin;
import net.pooleaf.core.plugin.CorePluginManager;
import net.pooleaf.core.support.common.messager.Prefixer;

public class Logger extends Prefixer {

  @Setter(AccessLevel.PROTECTED)
  private static LoggerAdapter loggerAdapter;


  public static void log(Object log) {
    loggerAdapter.log(getCurrentPluginPrefix() + log);
  }

  public static void warning(Object log) {
    log(getCurrentPluginPrefix() + "§c" + log);
  }

}
