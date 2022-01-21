package net.pooleaf.core.modules.event;

import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.plugin.CorePlugin;

@Getter
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
