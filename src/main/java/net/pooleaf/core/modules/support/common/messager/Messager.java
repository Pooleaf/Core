package net.pooleaf.core.modules.support.common.messager;

import lombok.AccessLevel;
import lombok.Setter;

public class Messager extends Prefixer {

    @Setter(AccessLevel.PROTECTED)
    private static MessagerAdapter messagerAdapter;


    public static void nmessage(Object sender, Object message) {
        messagerAdapter.message(sender, getCurrentPluginPrefix(" §f") + message);
    }

    public static void nmessage(Object sender, Object message, Object... params) {
        nmessage(sender, String.format((String) message, params));
    }

    public static void message(Object sender, Object message) {
        nmessage(sender, getCurrentPluginPrefix(" §f") + message);
    }

    public static void message(Object sender, Object message, Object... params) {
        message(sender, String.format((String) message, params));
    }


    public static void nwarning(Object sender, Object message) {
        nmessage(sender, "§c" + message);
    }

    public static void nwarning(Object sender, Object message, Object... params) {
        nwarning(sender, String.format((String) message, params));
    }

    public static void warning(Object sender, Object message) {
        nmessage(sender, getCurrentPluginPrefix(" §c") + message);
    }

    public static void warning(Object sender, Object message, Object... params) {
        warning(sender, String.format((String) message, params));
    }


    public static void nbroadcast(Object message) {
        messagerAdapter.broadcast(message);
    }

    public static void nbroadcast(Object message, Object... params) {
        messagerAdapter.broadcast(String.format((String) message, params));
    }

    public static void broadcast(Object message) {
        nbroadcast(getCurrentPluginPrefix(" §f") + message);
    }

    public static void broadcast(Object message, Object... params) {
        broadcast(String.format((String) message, params));
    }

}
