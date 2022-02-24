package net.pooleaf.core.modules.game.bukkit.game;

import lombok.Data;

@Data
public class GameConfig {

    // 대기 퀵바
    private boolean useWaitQuickBar = true; // 대기 퀵바 사용
    private boolean useWaitQuickBarStartVote = true; // 대기 퀵바 게임 시작 투표 사용
    private boolean useWaitQuickBarMapVote = true; // 대기 퀵바 맵 투표 사용
    private boolean useWaitQuickBarLobby = true; // 대기 퀵바 로비로 돌아가기 사용

    // 관전 퀵바
    private boolean useObserverQuickBar = true; // 관전 퀵바 사용
    private boolean useObserverQuickBarTeleporter = true; // 관전 퀵바 플레이어 순간이동기 사용
    private boolean useObserverQuickBarLobby = true; // 관전 퀵바 로비로 돌아가기 사용

}
