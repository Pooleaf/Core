package net.pooleaf.core.support.common.messager;

import lombok.AccessLevel;
import lombok.Setter;

public class Messager extends Prefixer {

    @Setter(AccessLevel.PROTECTED)
    private static MessagerAdapter messagerAdapter;


    public static void message(Object sender, Object message) {
        messagerAdapter.message(sender, getCurrentPluginPrefix() + message);
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
