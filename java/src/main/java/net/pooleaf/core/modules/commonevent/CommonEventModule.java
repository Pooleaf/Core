package net.pooleaf.core.modules.commonevent;

import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import net.pooleaf.core.modules.commonevent.common.CommonEventListener;
import net.pooleaf.core.modules.commonevent.common.CommonEventManager;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.List;

public class CommonEventModule extends CoreModule {

    @Getter
    private static CommonEventManager commonEventManager = new CommonEventManager();


    @Override
    public String getName() {
        return "CommonEvent";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "Support" };
    }


    public static void registerListener(CorePlugin plugin, CommonEventListener listener) {
        commonEventManager.registerListener(plugin, listener);
    }

    public static List<Class> registerListeners(CorePlugin plugin) {
        return commonEventManager.registerListeners(plugin);
    }

    public static List<Class> registerListeners(CorePlugin plugin, String packageName) {
        return commonEventManager.registerListeners(plugin, packageName);
    }

    public static void callEvent(CommonEvent event) {
        commonEventManager.callEvent(event);
    }

}
