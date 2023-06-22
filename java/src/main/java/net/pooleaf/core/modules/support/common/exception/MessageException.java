package net.pooleaf.core.modules.support.common.exception;

/**
 * 예외 발생 시 메시지를 출력할 때 사용하는 Exception
 * 예) teleportPlayer(player) 메소드에서 player가 오프라인일 경우, "플레이어가 오프라인입니다." 라는 메시지를 반환하기 위해 사용
 */
public class MessageException extends Exception {

    public MessageException(String message) {
        super(message);
    }

}
