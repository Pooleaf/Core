package net.pooleaf.core.modules.option.common.listener;

import net.pooleaf.core.modules.commonevent.common.CommonEventHandler;
import net.pooleaf.core.modules.commonevent.common.CommonEventListener;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerJoinEvent;
import net.pooleaf.core.modules.commonevent.common.events.player.CommonPlayerQuitEvent;
import net.pooleaf.core.modules.option.OptionModule;

public class PlayerOptionLoadListener implements CommonEventListener {

    @CommonEventHandler
    public void onJoin(CommonPlayerJoinEvent event) {
        OptionModule.getPlayerOptionManager().load(event.getPlayer().getUuid());
    }

    @CommonEventHandler
    public void onQuit(CommonPlayerQuitEvent event) {
        OptionModule.getPlayerOptionManager().remove(event.getPlayer().getUuid());
    }

}