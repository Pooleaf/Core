package net.pooleaf.core.modules.commonplayer.common;

import lombok.Data;
import net.pooleaf.core.modules.support.common.messager.Messager;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public abstract class CommonPlayer<T> { // T: 각 Platform에 맞는 Player

    protected UUID uuid; // 플레이어 UUID

    protected String name; // 플레이어 닉네임
    protected String displayName; // 가상닉네임

    protected String ip; // 플레이어 IP

    protected LocalDateTime lastLogin; // 마지막 로그인


    public boolean hasDisplayName() {
        return displayName != null;
    }

    /**
     * 플레이어의 가상닉네임을 반환합니다.
     * 가상닉네임이 없을 경우 닉네임을 반환합니다.
     * @return 플레이어의 가상닉네임 (없을 경우 실제 닉네임)
     */
    public String getDisplayName() {
        return hasDisplayName() ? displayName : name;
    }

    /**
     * 각 Platform에 맞는 Player 객체를 반환합니다.
     * @return 각 Platform에 맞는 Player 객체
     */
    public abstract T getPlatformPlayer();

    /**
     * 플레이어가 서버에 접속 중인지 확인합니다.
     * @return 플레이어의 서버 접속 상태
     */
    public boolean isOnline() {
        return getPlatformPlayer() != null;
    }

    public void message(String message) {
        Messager.message(getPlatformPlayer(), message);
    }

    public abstract void kickPlayer(String message);

}
