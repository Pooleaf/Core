package net.pooleaf.core.modules.playerinfo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PlayerInfo {

    private UUID uuid; // 플레이어 UUID

    private String name; // 플레이어 닉네임
    private String displayName; // 가상닉네임

    private String ip; // 플레이어 IP

    private LocalDateTime lastLogin; // 마지막 로그인


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

}
