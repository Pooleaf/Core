package net.pooleaf.core.modules.support.common.platform;

import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;

@UtilityClass
public class PlatformDetector {

  public static void detectPlatform() {
    if (ReflectionUtil.existsClass("org.bukkit.Bukkit")) Platform.setCurrentPlatform(Platform.BUKKIT);
    else if (ReflectionUtil.existsClass("net.md_5.bungee.api.ProxyServer")) Platform.setCurrentPlatform(Platform.BUNGEECORD);

    Preconditions.checkNotNull(Platform.getCurrentPlatform(), "지원하지 않는 플랫폼입니다.");
  }

}
