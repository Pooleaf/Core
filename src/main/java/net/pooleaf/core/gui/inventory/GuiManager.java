package net.pooleaf.core.gui.inventory;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiManager {

    @Getter
    private static Map<UUID, Gui> playerGuis = new HashMap<>();

}
