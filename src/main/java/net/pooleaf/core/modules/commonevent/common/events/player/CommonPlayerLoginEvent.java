package net.pooleaf.core.modules.commonevent.common.events.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import net.pooleaf.core.modules.commonplayer.common.CommonPlayer;

@Getter
@RequiredArgsConstructor
public class CommonPlayerLoginEvent<T> extends CommonEvent {

    private final CommonPlayer<T> player;

    private boolean disallow; // 접속 차단 여부
    private String disallowMessage; // 접속 차단 메시지

}
