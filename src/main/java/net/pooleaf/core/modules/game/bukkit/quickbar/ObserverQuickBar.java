package net.pooleaf.core.modules.game.bukkit.quickbar;

import net.pooleaf.core.modules.game.bukkit.game.Game;
import net.pooleaf.core.modules.gui.bukkit.quickbar.QuickBar;
import net.pooleaf.core.modules.gui.bukkit.quickbar.Slot;
import net.pooleaf.core.modules.gui.bukkit.quickbar.event.SlotClickEvent;
import net.pooleaf.core.modules.support.bukkit.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ObserverQuickBar extends QuickBar {

    private Game game;

    public ObserverQuickBar(Game game) {
        this.game = game;
    }


    public ObserverQuickBar() {
        // 플레이어 순간이동기
        if (game.getConfig().isUseWaitQuickBarStartVote()) {
            Slot startVoteSlot = new Slot() {
                @Override
                protected ItemStack updateItem() {
                    return new ItemBuilder(Material.PAPER)
                            .displayName("§e§l게임 시작 투표")
                            .lore("§f우클릭 시 게임 시작 투표에 참여합니다.")
                            .build();
                }

                @Override
                public void onClick(SlotClickEvent event) {
                    // GUI 열기
                    game.getStartVote().getGui().open(event.getPlayer());
                }
            };

            setSlot(1, startVoteSlot);
        }

        // 로비로 돌아가기
        if (game.getConfig().isUseObserverQuickBarLobby()) {
            Slot lobbySlot = new Slot() {
                @Override
                protected ItemStack updateItem() {
                    return new ItemBuilder(Material.BED)
                            .displayName("§e§l로비로 돌아가기")
                            .lore("§f우클릭 시 로비로 돌아갑니다.")
                            .build();
                }

                @Override
                public void onClick(SlotClickEvent event) {
                    // 로비로 이동
                    game.sendToLobbyChannel(event.getPlayer());
                }
            };

            setSlot(9, lobbySlot);
        }
    }

}