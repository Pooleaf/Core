package net.pooleaf.core.modules.support.common.messager;

import lombok.AccessLevel;
import lombok.Setter;

public class Messager extends Prefixer {

    @Setter(AccessLevel.PROTECTED)
    private static MessagerAdapter messagerAdapter;


    public static void message(Object sender, Object message) {
        messagerAdapter.message(sender, getCurrentPluginPrefix() + " §f" + message);
    }

    public static void message(Object sender, Object message, Object... params) {
        message(sender, String.format((String) message, params));
    }

    public static void warning(Object sender, Object message) {
        message(sender, " §c" + message);
    }

    public static void warning(Object sender, Object message, Object... params) {
        warning(sender, String.format((String) message, params));
    }

    public static void broadcast(Object message) {
        broadcast(getCurrentPluginPrefix() + " §f" + message);
    }

    public static void nbroadcast(Object message) {
        broadcast(message);
    }

}
