package net.pooleaf.core.modules.commonsender.common;

import lombok.Data;
import net.pooleaf.core.modules.support.common.messager.Messager;

@Data
public abstract class CommonCommandSender<T> { // T: 각 Platform에 맞는 Sender

    protected String name; // 닉네임
    protected String displayName; // 가상닉네임


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

    public abstract boolean isConsole();

    /**
     * 각 Platform에 맞는 Sender 객체를 반환합니다.
     * @return 각 Platform에 맞는 Sender 객체
     */
    public abstract T getPlatformSender();

    /**
     * Sender에게 메시지를 보냅니다.
     * Messager에 등록된 Prefix를 사용합니다.
     * @param message
     */
    public void message(String message) {
        Messager.message(getPlatformSender(), message);
    }

    /**
     * Sender에게 Prefix가 없는 메시지를 보냅니다.
     * @param message
     */
    public void nmessage(String message) {
        Messager.nmessage(getPlatformSender(), message);
    }

}
