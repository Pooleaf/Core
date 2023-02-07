package net.pooleaf.core.module;

import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.List;

import net.pooleaf.core.modules.option.OptionModule;
import net.pooleaf.core.modules.support.common.logger.Logger;
import lombok.SneakyThrows;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.support.common.manager.AbstractManager;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;

public class ModuleManager extends AbstractManager<String, CoreModule> {

  private List<CoreModule> enabledOrder = new ArrayList<>();


  @SneakyThrows
  public void registerModule(Class<? extends CoreModule> moduleClass) {
    CoreModule module = moduleClass.newInstance();
    datas.put(module.getName(), module);
  }

  public void registerModules() {
    ReflectionUtil.getClasses(Core.getPlugin()).forEach(targetClass -> {
      if (CoreModule.class.isAssignableFrom(targetClass) && !Modifier.isAbstract(targetClass.getModifiers())) {
        registerModule(targetClass);
      }
    });
  }

  public void initModules() {
    int beforeCount = datas.size();
    int count = 0;

    // 모듈 불러오기
    while (beforeCount != count) {
      beforeCount = count;
      count = 0;

      for (CoreModule module : datas.values()) {
        if (module.isEnabled()) continue;

        boolean canEnable = true;

        // Depends에 있는 모듈들이 활성화 되었는지 확인
        if (module.getDepends() != null) {
          int enabled = 0;

          for (String depend : module.getDepends()) {
            CoreModule dependModule = get(depend);
            if (dependModule != null && dependModule.isEnabled()) {
              enabled++;
            }
          }

          canEnable = canEnable && module.getDepends().length == enabled;
        }

        // SoftDepends에 있는 모듈들이 활성화 되었는지 확인
        if (module.getSoftDepends() != null) {
          int enabled = 0;

          for (String depend : module.getSoftDepends()) {
            CoreModule dependModule = get(depend);
            if (dependModule == null || dependModule.isEnabled()) {
              enabled++;
            }
          }

          canEnable = canEnable && module.getSoftDepends().length == enabled;
        }

        // Depends & SoftDepends 체크를 통과할 경우
        if (canEnable) {
          if (Logger.isInitialized()) {
            Logger.log(module.getName() + " Module을 초기화하는 중입니다..");
          }

          module.onEnable(Core.getPlugin());
          module.setEnabled(true);
          enabledOrder.add(module);

          Logger.log(module.getName() + " Module이 초기화되었습니다.");
          Logger.log("");

          count++;
        }
      }
    }

    // 불러오지 못한 모듈 알림
    values().stream()
            .filter(module -> !module.isEnabled())
            .forEach(module -> Logger.warning(module.getName() + " 모듈 초기화에 실패했습니다."));
  }

  public void endModules() {
    for (int i = enabledOrder.size(); i > 0; i--) {
      CoreModule module = enabledOrder.get(i - 1);
      module.onDisable(Core.getPlugin());
      module.setEnabled(false);

      Logger.log(module.getName() + " Module이 종료되었습니다.");
    }
  }

}
