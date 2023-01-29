package net.pooleaf.core.modules.annoconfig;

import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.annoconfig.bukkit.serializer.LocationSerializer;
import net.pooleaf.core.modules.annoconfig.common.AnnoConfig;
import net.pooleaf.core.modules.support.common.platform.Platform;
import net.pooleaf.core.plugin.CorePlugin;
import lombok.Getter;

import java.io.File;

public class AnnoConfigModule extends CoreModule {

  @Getter
  private static AnnoConfig annoConfig = new AnnoConfig();


  @Override
  public String getName() {
    return "AnnoConfig";
  }

  @Override
  public String[] getDepends() {
    return new String[] { "Support", "CommonConfig" };
  }

  @Override
  public void onEnable(CorePlugin plugin) {
    // 버킷 기본 Serializer
    if (Platform.getCurrentPlatform() == Platform.BUKKIT) {
      AnnoConfig.getDefaultSerializer().put(org.bukkit.Location.class, LocationSerializer.class);
    }
  }


  /**
   * Config 파일에서 해당 객체에 맞게 설정을 불러옵니다.
   * @param file 설정을 불러올 파일
   * @param configObject 불러온 설정을 저장할 객체
   */
  public static void load(File file, Object configObject) {
    AnnoConfig.load(file, configObject);
  }

  /**
   * 해당 객체를 Config 파일에 저장합니다.
   * @param file 저장할 파일
   * @param configObject 저장할 객체
   */
  public static void save(File file, Object configObject) {
    AnnoConfig.save(file, configObject);
  }

}
