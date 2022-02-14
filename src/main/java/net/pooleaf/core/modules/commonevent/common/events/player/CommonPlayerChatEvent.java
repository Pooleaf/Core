package net.pooleaf.core.modules.commonevent.common.events.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.pooleaf.core.modules.commonevent.common.CommonCancellableEvent;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import net.pooleaf.core.modules.commonplayer.common.CommonPlayer;

@Getter
@AllArgsConstructor
public class CommonPlayerChatEvent<T> extends CommonCancellableEvent {

    private final CommonPlayer<T> player;

    private String message;

}
