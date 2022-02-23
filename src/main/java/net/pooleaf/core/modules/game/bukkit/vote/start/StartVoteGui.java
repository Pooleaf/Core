package net.pooleaf.core.modules.game.bukkit.vote.start;

import net.pooleaf.core.modules.game.GameModule;
import net.pooleaf.core.modules.game.bukkit.player.GamePlayer;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryGui;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryIcon;
import net.pooleaf.core.modules.gui.bukkit.inventory.event.InevntoryGuiClickEvent;
import net.pooleaf.core.modules.support.bukkit.util.ItemBuilder;
import org.bukkit.inventory.ItemStack;

public class StartVoteGui extends InventoryGui {

    private final StartVote vote;


    public StartVoteGui(StartVote vote) {
        super("게임 시작 투표", 3);
        this.vote = vote;


        InventoryIcon agreeIcon = new InventoryIcon() {
            @Override
            protected ItemStack updateItem() {
                return new ItemBuilder("351:10")
                        .amount(vote.agreeCount())
                        .displayName("§a§l찬성")
                        .lore("§f클릭 시 게임 시작 투표에 §a찬성§f합니다.")
                        .build();
            }

            @Override
            public void onClick(InevntoryGuiClickEvent event) {
                GamePlayer gamePlayer = GameModule.getGamePlayerManager().get(event.getPlayer().getUniqueId());
                vote.vote(gamePlayer, true);
            }
        };

        InventoryIcon disagreeIcon = new InventoryIcon() {
            @Override
            protected ItemStack updateItem() {
                return new ItemBuilder("351:13")
                        .amount(vote.disagreeCount())
                        .displayName("§c§l반대")
                        .lore("§f클릭 시 게임 시작 투표에 §c반대§f합니다.")
                        .build();
            }

            @Override
            public void onClick(InevntoryGuiClickEvent event) {
                GamePlayer gamePlayer = GameModule.getGamePlayerManager().get(event.getPlayer().getUniqueId());
                vote.vote(gamePlayer, false);
            }
        };

        getMainPanel().set(4, 2, agreeIcon);
        getMainPanel().set(6, 2, disagreeIcon);


        updateAsynchronously();
    }

}
