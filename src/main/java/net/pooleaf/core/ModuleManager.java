package net.pooleaf.core;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.SneakyThrows;
import net.pooleaf.core.support.common.util.ReflectionUtil;
import org.bukkit.Bukkit;

public class ModuleManager {

  @Getter
  private static Map<String, CoreModule> modules = new HashMap<>();


  public static CoreModule getModule(String name) {
    return modules.get(name);
  }

  @SneakyThrows
  public static void registerModule(Class<? extends CoreModule> moduleClass) {
    CoreModule module = moduleClass.newInstance();
    modules.put(module.getName(), module);
  }

  public static void registerModules() {
    ReflectionUtil.getClasses(Core.getPlugin()).forEach(targetClass -> {
      if (CoreModule.class.isAssignableFrom(targetClass) && !Modifier.isAbstract(targetClass.getModifiers())) {
        registerModule(targetClass);
      }
    });
  }

  public static void initModules() {
    int beforeCount = modules.size();
    int count = 0;

    while (beforeCount != count) {
      beforeCount = count;
      count = 0;

      for (CoreModule module : modules.values()) {
        if (module.isEnable()) continue;

        boolean canEnable = true;

        // Depends에 있는 플러그인들이 활성화 되었는지 확인
        if (module.getDepends() != null) {
          int enabled = 0;

          for (String depend : module.getDepends()) {
            CoreModule dependModule = getModule(depend);
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
            CoreModule dependModule = getModule(depend);
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
          Bukkit.getConsoleSender().sendMessage(module.getName() + " v" + module.getVersion() + " 이(가) 초기화되었습니다.");

          count++;
        }
      }
    }
  }

}
