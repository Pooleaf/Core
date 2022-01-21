package net.pooleaf.core.module;

import java.lang.reflect.Modifier;

import lombok.SneakyThrows;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.pooleaf.core.modules.support.common.util.AbstractManager;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;

public class ModuleManager extends AbstractManager<String, CoreModule> {

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

    while (beforeCount != count) {
      beforeCount = count;
      count = 0;

      for (CoreModule module : datas.values()) {
        if (module.isEnable()) continue;

        boolean canEnable = true;

        // Depends에 있는 플러그인들이 활성화 되었는지 확인
        if (module.getDepends() != null) {
          int enabled = 0;

          for (String depend : module.getDepends()) {
            CoreModule dependModule = get(depend);
            if (dependModule != null && dependModule.isEnable()) {
              enabled++;
            }
          }

          canEnable = canEnable && module.getDepends().length == enabled;
        }

        // SoftDepends에 있는 플러그인들이 활성화 되었는지 확인
        if (module.getSoftDepends() != null) {
          int enabled = 0;

          for (String depend : module.getSoftDepends()) {
            CoreModule dependModule = get(depend);
            if (dependModule == null || dependModule.isEnable()) {
              enabled++;
            }
          }

          canEnable = canEnable && module.getSoftDepends().length == enabled;
        }

        // Depends & SoftDepends 체크를 통과할 경우
        if (canEnable) {
          module.onEnable(Core.getPlugin());
          module.setEnable(true);
          Logger.log(module.getName() + " Module이 초기화되었습니다.");

          count++;
        }
      }
    }
  }

}
