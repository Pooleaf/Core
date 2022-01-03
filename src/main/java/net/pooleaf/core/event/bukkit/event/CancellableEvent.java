package net.pooleaf.core.event.bukkit.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

public class CancellableEvent extends HandlerEvent implements Cancellable {

    @Setter
    @Getter
    private boolean cancelled;

}
