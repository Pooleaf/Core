package net.pooleaf.core.modules.commonconfig;

import net.pooleaf.core.module.CoreModule;

import java.io.File;
import net.pooleaf.core.modules.commonconfig.common.CommonConfig;
import net.pooleaf.core.modules.commonconfig.common.CommonConfigFactory;

public class CommonConfigModule extends CoreModule {

  @Override
  public String getName() {
    return "CommonConfig";
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
  public static CommonConfig createConfig(File file) {
    return CommonConfigFactory.createConfig(file);
  }

}