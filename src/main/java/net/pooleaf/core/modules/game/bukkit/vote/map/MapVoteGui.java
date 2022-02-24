package net.pooleaf.core.modules.game.bukkit.vote.map;

import net.pooleaf.core.modules.game.GameModule;
import net.pooleaf.core.modules.game.bukkit.map.GameMap;
import net.pooleaf.core.modules.game.bukkit.player.GamePlayer;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryIcon;
import net.pooleaf.core.modules.gui.bukkit.inventory.event.InevntoryGuiClickEvent;
import net.pooleaf.core.modules.gui.bukkit.inventory.pageable.LargePageableGui;
import net.pooleaf.core.modules.support.bukkit.util.ItemBuilder;
import net.pooleaf.core.modules.support.common.messager.Messager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MapVoteGui extends LargePageableGui { // TODO Pageable

    private final MapVote vote;


    public MapVoteGui(MapVote vote) {
        super("맵 투표");
        this.vote = vote;


        // 랜덤 투표 Icon
        InventoryIcon randomIcon = new InventoryIcon() {
            @Override
            protected ItemStack updateItem() {
                int count = vote.getRandomCount();
                return new ItemBuilder(Material.EMPTY_MAP)
                        .amount(count)
                        .displayName("§e§l랜덤 (투표 수: §f" + count + "§e명)")
                        .lore("§e클릭 시 §f랜덤§e에 투표합니다.")
                        .build();
            }

            @Override
            public void onClick(InevntoryGuiClickEvent event) {
                vote.getVotedTo().remove(event.getPlayer().getUniqueId());
                Messager.message(event.getPlayer(), "랜덤§e에 투표했습니다.");

                event.getPlayer().closeInventory();

                MapVoteGui.this.updateAsynchronously();
            }
        };
        addItem(randomIcon);

        // 맵 투표 Icon
        for (GameMap map : GameModule.getGameMapManager().getDatas().values()) {
            addItem(new InventoryIcon() {
                private GameMap gameMap = map;

                @Override
                protected ItemStack updateItem() {
                    int count = vote.getVotedCount(map);
                    return new ItemBuilder(Material.MAP)
                            .amount(count)
                            .displayName("§e§l" + gameMap.getName() + " (투표 수: §f" + count + "§e명)")
                            .lore("§e클릭 시 §f" + gameMap.getName() + "§e에 투표합니다.")
                            .build();
                }

                @Override
                public void onClick(InevntoryGuiClickEvent event) {
                    GamePlayer gamePlayer = GameModule.getGamePlayerManager().get(event.getPlayer().getUniqueId());
                    vote.vote(gamePlayer, gameMap);
                    Messager.message(event.getPlayer(), gameMap.getName() + " §e맵에 투표했습니다.");

                    event.getPlayer().closeInventory();

                    MapVoteGui.this.updateAsynchronously();
                }
            });
        }


        updateAsynchronously();
    }

}
