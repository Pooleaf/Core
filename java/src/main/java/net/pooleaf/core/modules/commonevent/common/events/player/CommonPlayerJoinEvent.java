package net.pooleaf.core.modules.commonevent.common.events.player;

import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;

@Getter
@RequiredArgsConstructor
public class CommonPlayerJoinEvent<T> extends CommonEvent {

    private final CommonPlayer<T> player;

}
