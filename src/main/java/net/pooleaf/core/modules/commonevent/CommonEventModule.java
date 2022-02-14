package net.pooleaf.core.modules.commonevent;

import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import net.pooleaf.core.modules.commonevent.common.CommonEventListener;
import net.pooleaf.core.modules.commonevent.common.CommonEventManager;
import net.pooleaf.core.modules.eventsupport.common.EventRegistererFactory;
import net.pooleaf.core.plugin.CorePlugin;

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

    public static void registerListeners(CorePlugin plugin) {
        commonEventManager.registerListeners(plugin);
    }

    public static void callEvent(CommonEvent event) {
        commonEventManager.callEvent(event);
    }

}
