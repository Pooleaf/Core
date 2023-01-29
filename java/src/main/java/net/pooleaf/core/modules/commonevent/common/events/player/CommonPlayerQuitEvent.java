package net.pooleaf.core.modules.commonevent.common.events.player;

import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonPlayerQuitEvent<T> extends CommonEvent {

    private final CommonPlayer<T> player;

}
