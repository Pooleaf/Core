package net.pooleaf.core.modules.option.common.events;

import lombok.Data;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;

import java.util.Collection;

@Data
public class ServerOptionChangedEvent extends CommonEvent {

    private final Collection<String> keys;

}