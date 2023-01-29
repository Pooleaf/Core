package net.pooleaf.core.modules.commonevent.common;

import lombok.Data;

@Data
public class CommonCancellableEvent extends CommonEvent {

    private boolean cancelled;

}
