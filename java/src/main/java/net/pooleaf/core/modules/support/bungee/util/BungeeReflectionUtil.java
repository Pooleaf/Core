package net.pooleaf.core.modules.support.bungee.util;

import net.pooleaf.core.modules.support.common.AutoRegisterExclude;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;
import net.pooleaf.core.plugin.CorePlugin;

public class BungeeReflectionUtil {

    public static int registerListeners(CorePlugin plugin) {
        int count = 0;

        for (Class targetClass : ReflectionUtil.getClasses(plugin)) {
            try {
                // Listener 클래스인지 확인
                if (Listener.class.isAssignableFrom(targetClass)) {
                    continue;
                }

                Listener listener = (Listener) targetClass.newInstance();

                // 자동 등록 제외 Listener
                if (listener.getClass().getAnnotation(AutoRegisterExclude.class) != null) {
                    continue;
                }

                ProxyServer.getInstance().getPluginManager().registerListener((Plugin) plugin, listener);
                count++;
            } catch (Exception e) {
            } catch (Error e) {
            }
        }

        return count;
    }

}
