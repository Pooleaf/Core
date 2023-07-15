package net.pooleaf.core.modules.annocommand.common;

import net.pooleaf.core.modules.support.common.CommonChatColor;

import java.lang.annotation.*;

@Repeatable(Commands.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String[] parent() default ""; // ""일 경우 부모가 없는 명령어로 취급함
    String[] name();

    String arguments() default "";
    String description() default "";

    String permission() default "";

    CommonChatColor color() default CommonChatColor.RESET;

    boolean helpCommand() default false;

    /**
     * 현재 명령어를 특정 명령어의 도움말 명령어로 설정합니다.
     * ""일 경우 현재 명령어를 도움말 명령어로 설정합니다.
     *
     * 예) '/돈 도움말' 명령어를 '/돈' 명령어의 도움말 명령어로 만들고 싶을 경우 '돈'으로 설정
     */
    String helpCommandTarget() default "";

    boolean async() default true;

    boolean usePlatformPrefix() default true;

}
