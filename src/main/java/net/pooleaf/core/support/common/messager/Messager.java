package net.pooleaf.core.support.common.messager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Messager {

    @Setter(AccessLevel.PROTECTED)
    private static MessagerAdapter messagerAdapter;

    @Setter
    @Getter
    private static String prefix = "";


    public static void message(Object sender, Object message) {
        messagerAdapter.message(sender, prefix + message);
    }

    public static void message(Object sender, Object message, Object... params) {
        message(sender, String.format(message.toString(), params));
    }

    public static void warning(Object sender, Object message) {
        message(sender, "§c" + message);
    }

    public static void warning(Object sender, Object message, Object... params) {
        message(sender, String.format("§c" + message, params));
    }

}
