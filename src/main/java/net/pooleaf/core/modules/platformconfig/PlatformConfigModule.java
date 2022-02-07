package net.pooleaf.core.modules.platformconfig;

import net.pooleaf.core.module.CoreModule;

import java.io.File;

public class PlatformConfigModule extends CoreModule {

  @Override
  public String getName() {
    return "PlatformConfig";
  }

  @Override
  public String[] getDepends() {
    return new String[] { "Support" };
  }


  /**
   * Platform에 맞는 Config 객체를 생성합니다.
   * @param file Config 파일 경로
   * @return Platform에 맞는 Config 객체
   */
  public static Config createConfig(File file) {
    return ConfigFactory.createConfig(file);
  }

}