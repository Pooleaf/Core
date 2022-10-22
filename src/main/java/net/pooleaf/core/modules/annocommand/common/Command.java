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
    boolean async() default true;

    boolean usePlatformPrefix() default true;

}
