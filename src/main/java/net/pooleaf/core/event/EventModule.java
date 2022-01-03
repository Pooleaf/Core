package net.pooleaf.core.event;

import net.pooleaf.core.CoreModule;
import net.pooleaf.core.CorePlugin;

public class EventModule extends CoreModule {

    @Override
    public String getName() {
        return "Event";
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
