package net.pooleaf.core.modules.channel.common.channel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChannelStatus {

    private int id;
    private String message;
    private String color;
    private String itemCode;

}
