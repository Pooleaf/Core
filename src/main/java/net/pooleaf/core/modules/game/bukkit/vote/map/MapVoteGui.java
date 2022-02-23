package net.pooleaf.core.modules.game.bukkit.vote.map;

import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryGui;

public class MapVoteGui extends InventoryGui { // TODO Pageable

    private final MapVote vote;


    public MapVoteGui(MapVote vote) {
        super("맵 투표", 3);
        this.vote = vote;

        // TODO

        updateAsynchronously();
    }

}
