package net.pooleaf.core.modules.commonevent.common.events.player;

import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.pooleaf.core.modules.commonevent.common.CommonCancellableEvent;

@Getter
@AllArgsConstructor
public class CommonPlayerChatEvent<T> extends CommonCancellableEvent {

    private final CommonPlayer<T> player;

    private String message;

}
