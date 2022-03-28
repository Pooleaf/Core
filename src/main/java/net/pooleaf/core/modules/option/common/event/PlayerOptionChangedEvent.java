package net.pooleaf.core.modules.option.common.event;

import lombok.Data;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;

import java.util.UUID;

@Data
public class PlayerOptionChangedEvent extends CommonEvent {

    private final UUID uuid;


    public CommonPlayer getCommonPlayer() {
        return CommonSenderModule.getPlayer(uuid);
    }

}
