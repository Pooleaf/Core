package net.pooleaf.core.modules.commonevent.common.events.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import net.pooleaf.core.modules.commonplayer.common.CommonPlayer;

@Getter
@RequiredArgsConstructor
public class CommonPlayerQuitEvent<T> extends CommonEvent {

    private final CommonPlayer<T> player;

}
