package net.pooleaf.core.modules.commonevent.common.events.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.pooleaf.core.modules.commonevent.common.CommonCancellableEvent;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;

@Data
@AllArgsConstructor
public class CommonPlayerChatEvent<T> extends CommonCancellableEvent {

    private final CommonPlayer<T> player;

    private String message;

}
