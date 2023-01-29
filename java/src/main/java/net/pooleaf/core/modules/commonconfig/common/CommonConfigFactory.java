package net.pooleaf.core.modules.commonconfig.common;

import lombok.experimental.UtilityClass;
import net.pooleaf.core.modules.commonconfig.bukkit.BukkitConfig;
import net.pooleaf.core.modules.commonconfig.bungee.BungeeConfig;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;

import java.io.File;

@UtilityClass
public class CommonConfigFactory {

  /**
   * 해당 파일로 플랫폼에 맞는 Config 객체를 생성합니다.
   * @param file Config 파일
   * @return 플랫폼에 맞는 Config 객체
   */
  public static CommonConfig createConfig(File file) {
    if (ReflectionUtil.existsClass("org.bukkit.Bukkit")) return new BukkitConfig(file);
    else if (ReflectionUtil.existsClass("net.md_5.bungee.api.ProxyServer")) return new BungeeConfig(file);

    return null;
  }

}
