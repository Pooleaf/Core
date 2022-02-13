package net.pooleaf.core.modules.commonevent;

import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.eventsupport.common.EventRegistererFactory;
import net.pooleaf.core.plugin.CorePlugin;

@Getter
public class CommonEventModule extends CoreModule {

    @Override
    public String getName() {
        return "CommonEvent";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "Support" };
    }

    @Override
    public void onEnable(CorePlugin plugin) {
        EventRegistererFactory.createEventRegisterer().registerEvents();
    }

}
