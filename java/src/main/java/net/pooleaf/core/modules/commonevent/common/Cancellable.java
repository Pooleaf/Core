package net.pooleaf.core.modules.commonevent.common;

public interface Cancellable {

    void setCancelled(boolean cancelled);

    boolean isCancelled();

}
