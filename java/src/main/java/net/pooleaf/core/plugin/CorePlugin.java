package net.pooleaf.core.plugin;

import net.pooleaf.core.modules.commonevent.common.CommonEventListener;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.common.CommonChatColor;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface CorePlugin {

  void onStart();
  void onEnd();

  String getName();
  String getPrefix();
  CommonChatColor getColor();
  String getVersion();
  String getPluginPackage();

  boolean isEnabled();

  File getFile();
  File getDataFolder();
  InputStream getResource(String path);

  void onConfigLoaded();
  void loadConfig();
  void loadConfig(CommonCommandSender sender);

  /**
   * 해당 이름을 가진 플러그인이 존재하는지 확인합니다.
   */
  boolean detectPlugin(String name);

  /**
   * 해당 플러그인의 플랫폼에 맞는 모든 EventListener를 등록합니다.
   * @return 등록된 Listener 클래스 목록
   */
  List<Class> registerEventListeners();

  /**
   * 해당 플러그인 패키지의 플랫폼에 맞는 모든 EventListener를 등록합니다.
   * @return 등록된 Listener 클래스 목록
   */
  List<Class> registerEventListeners(String packageName);

  /**
   * 해당 플러그인의 모든 CommonEventListener를 등록합니다.
   * @return 등록된 CommonEventListener 클래스 목록
   */
  List<Class> registerCommonEventListeners();

  /**
   * 해당 플러그인 패키지의 모든 CommonEventListener를 등록합니다.
   * @return 등록된 CommonEventListener 클래스 목록
   */
  List<Class> registerCommonEventListeners(String packageName);

  /**
   * CommonEventListener를 등록합니다.
   */
  void registerCommonEventListener(CommonEventListener commonEventListener);

  /**
   * 해당 플러그인의 모든 Command를 등록합니다.
   */
  void registerCommands();

  /**
   * 해당 플러그인 패키지의 모든 Command를 등록합니다.
   */
  void registerCommands(String packageName);

  /**
   * Command를 등록합니다.
   */
  void registerCommand(Class<?> commandClass);

}
