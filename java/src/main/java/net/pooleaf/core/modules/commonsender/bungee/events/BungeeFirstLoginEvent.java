package net.pooleaf.core.modules.commonsender.bungee.events;

import lombok.Data;
import net.md_5.bungee.api.plugin.Event;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;

@Data
public class BungeeFirstLoginEvent extends Event {

    private final CommonPlayer commonPlayer;

}
