package net.pooleaf.core.modules.support.common.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractPlayer<T> {

    protected UUID uuid;

    /**
     * CommonPlayer를 반환합니다.
     * 플레이어가 오프라인일 경우 캐싱 없이 불러와 반환합니다.
     * @return CommonPlayer
     */
    public CommonPlayer<T> getCommonPlayer() {
        return CommonSenderModule.getOfflinePlayer(uuid);
    }

    /**
     * 플랫폼에 맞는 Player를 반환합니다.
     * 오프라인일 경우 null을 반환합니다.
     * @return 플랫폼에 맞는 Player
     */
    public T getPlayer() {
        return getCommonPlayer().getPlatformSender();
    }

    /**
     * 플레이어 이름을 반환합니다.
     * @return 플레이어 이름
     */
    public String getName() {
        return getCommonPlayer().getName();
    }

    /**
     * 플레이어 가상닉네임을 반환합니다.
     * @return 플레이어 가상닉네임
     */
    public String getDisplayName() {
        return getCommonPlayer().getDisplayName();
    }

    /**
     * 플레이어 접속 여부를 반환합니다.
     * @return 플레이어 접속 여부
     */
    public boolean isOnline() {
        return getCommonPlayer() != null && getCommonPlayer().isOnline();
    }

    /**
     * 플레이어가 번지코드 서버에 접속 중인지 확인합니다.
     * @return 플레이어의 번지코드 서버 접속 상태
     */
    public boolean isOnlineBungeeCord() {
        return getCommonPlayer() != null && getCommonPlayer().isOnlineBungeeCord();
    }

}
