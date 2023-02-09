package net.pooleaf.core.modules.commonsender.common;

import lombok.Data;
import net.md_5.bungee.api.chat.BaseComponent;
import net.pooleaf.core.modules.option.common.Option;
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
     * 해당 권한이 있는지 여부를 반환합니다.
     * @return 해당 권한이 있는지 여부
     */
    public abstract boolean hasPermission(String permission);

    /**
     * Sender에게 메시지를 보냅니다.
     * Messager에 등록된 Prefix를 사용합니다.
     * @param message 메시지
     */
    public void sendMessageWithPrefix(String message) {
        Messager.sendMessageWithPrefix(getPlatformSender(), message);
    }

    /**
     * Sender에게 BaseComponent를 보냅니다.
     * Messager에 등록된 Prefix를 사용합니다.
     * @param components BaseComponent
     */
    public void sendMessageWithPrefix(BaseComponent... components) {
        Messager.sendMessageWithPrefix(getPlatformSender(), components);
    }

    /**
     * Sender에게 Prefix가 없는 메시지를 보냅니다.
     * @param message 메시지
     */
    public void sendMessage(String message) {
        Messager.sendMessage(getPlatformSender(), message);
    }

    /**
     * Sender에게 Prefix가 없는 BaseComponent를 보냅니다.
     * @param components BaseComponent
     */
    public void sendMessage(BaseComponent... components) {
        Messager.sendMessage(getPlatformSender(), components);
    }

    /**
     * Sender에게 경고 메시지를 보냅니다.
     * @param message 경고 메시지
     */
    public void sendWarningWithPrefix(String message) {
        Messager.sendWarningWithPrefix(getPlatformSender(), message);
    }

    /**
     * Sender에게 경고 BaseComponent를 보냅니다.
     * @param components 경고 BaseComponent
     */
    public void sendWarningWithPrefix(BaseComponent... components) {
        Messager.sendWarningWithPrefix(getPlatformSender(), components);
    }

    /**
     * Sender에게 Prefix가 없는 경고 메시지를 보냅니다.
     * @param message 경고 메시지
     */
    public void sendWarning(String message) {
        Messager.sendWarning(getPlatformSender(), message);
    }

    /**
     * Sender에게 Prefix가 없는 경고 BaseComponent를 보냅니다.
     * @param components 경고 BaseComponent
     */
    public void sendWarning(BaseComponent... components) {
        Messager.sendWarning(getPlatformSender(), components);
    }

    public abstract Option option();

}
