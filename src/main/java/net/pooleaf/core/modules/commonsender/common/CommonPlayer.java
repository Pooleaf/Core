package net.pooleaf.core.modules.commonsender.common;

import lombok.Data;
import net.pooleaf.core.modules.option.OptionModule;
import net.pooleaf.core.modules.option.common.Option;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CommonPlayer<T> extends CommonCommandSender<T> { // T: 각 Platform에 맞는 Player

    protected UUID uuid; // 플레이어 UUID

    protected String ip; // 플레이어 IP

    protected LocalDateTime lastLogin; // 마지막 로그인


    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public T getPlatformSender() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPermission(String permission) {
        throw new UnsupportedOperationException();
    }

    /**
     * 플레이어가 서버에 접속 중인지 확인합니다.
     * @return 플레이어의 서버 접속 상태
     */
    public boolean isOnline() {
        return getPlatformSender() != null;
    }

    public void kickPlayer(String message) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Option option() {
        return OptionModule.getPlayerOption(uuid);
    }

}