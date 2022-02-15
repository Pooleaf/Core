package net.pooleaf.core.modules.commonsender.common;

public abstract class CommonConsoleSender<T> extends CommonCommandSender<T> {

    public CommonConsoleSender() {
        this.name = "콘솔";
        this.displayName = "콘솔";
    }

    @Override
    public boolean isConsole() {
        return true;
    }

    public abstract T getPlatformSender();

}
