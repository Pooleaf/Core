package net.pooleaf.core.modules.option.common.events;

import lombok.Data;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;

import java.util.Collection;
import java.util.UUID;

@Data
public class PlayerOptionChangedEvent extends CommonEvent {

    private final UUID uuid;

    private final Collection<String> keys;

    private CommonPlayer commonPlayer;


    public CommonPlayer getCommonPlayer() {
        return (commonPlayer == null) ? commonPlayer = CommonSenderModule.getOfflinePlayer(uuid) : commonPlayer;
    }

}