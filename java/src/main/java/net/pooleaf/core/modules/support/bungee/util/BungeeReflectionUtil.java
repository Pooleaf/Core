package net.pooleaf.core.modules.support.bungee.util;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.pooleaf.core.modules.support.common.AutoRegisterExclude;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.ArrayList;
import java.util.List;

public class BungeeReflectionUtil {

    public static List<Class> registerListeners(CorePlugin plugin) {
        return registerListeners(plugin, "");
    }

    public static List<Class> registerListeners(CorePlugin plugin, String packageName) {
        List<Class> registeredClass = new ArrayList<>();

        for (Class targetClass : ReflectionUtil.getClasses(plugin)) {
            try {
                // 패키지 확인
                if (!targetClass.getPackage().getName().startsWith(packageName)) {
                    continue;
                }

                // Listener 클래스인지 확인
                if (!Listener.class.isAssignableFrom(targetClass)) {
                    continue;
                }

                Listener listener = (Listener) targetClass.newInstance();

                // 자동 등록 제외 Listener
                if (listener.getClass().getAnnotation(AutoRegisterExclude.class) != null) {
                    continue;
                }

                ProxyServer.getInstance().getPluginManager().registerListener((Plugin) plugin, listener);
                registeredClass.add(targetClass);
            } catch (Exception e) {
            } catch (Error e) {
            }
        }

        return registeredClass;
    }

}
