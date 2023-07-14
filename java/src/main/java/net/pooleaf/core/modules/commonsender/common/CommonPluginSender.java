package net.pooleaf.core.modules.commonsender.common;

import net.pooleaf.core.modules.option.common.Option;

/**
 * 플러그인 명령어 전송자
 *
 * 어떤 기능의 처리자를 쉽게 지정할 수 있도록 추가함
 *
 * 예) 밴 처리자 로그를 남길 때
 * 플레이어가 처리했을 경우 CommonPlayer,
 * 콘솔이 처리했을 경우 CommonConsoleSender,
 * 플러그인이 처리했을 경우 CommonPluginSender
 */
public class CommonPluginSender extends CommonCommandSender {

    @Override
    public String getId() {
        return "PLUGIN";
    }

    @Override
    public String getName() {
        return "시스템";
    }

    @Override
    public String getDisplayName() {
        return "시스템";
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public Object getPlatformSender() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPermission(String permission) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Option option() {
        throw new UnsupportedOperationException();
    }

}