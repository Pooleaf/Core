package net.pooleaf.core.modules.game.bukkit.quickbar;

import lombok.Data;
import net.pooleaf.core.modules.game.bukkit.game.Game;
import net.pooleaf.core.modules.gui.bukkit.quickbar.QuickBar;
import net.pooleaf.core.modules.gui.bukkit.quickbar.Slot;
import net.pooleaf.core.modules.gui.bukkit.quickbar.event.SlotClickEvent;
import net.pooleaf.core.modules.support.bukkit.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Data
public class WaitQuickBar extends QuickBar {

    private Game game;

    public WaitQuickBar(Game game) {
        this.game = game;
    }


    public WaitQuickBar() {
        // 게임 시작 투표
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

        // 맵 투표
        if (game.getConfig().isUseWaitQuickBarMapVote()) {
            Slot mapVoteSlot = new Slot() {
                @Override
                protected ItemStack updateItem() {
                    return new ItemBuilder(Material.MAP)
                            .displayName("§b§l맵 투표")
                            .lore("§f우클릭 시 맵 투표에 참여합니다.")
                            .build();
                }

                @Override
                public void onClick(SlotClickEvent event) {
                    // GUI 열기
                    game.getMapVote().getGui().open(event.getPlayer());
                }
            };

            // 게임 시작 투표를 사용하면 첫번째 슬롯, 사용안하면 두번째 슬롯에 배치
            if (!game.getConfig().isUseWaitQuickBarStartVote()) {
                setSlot(1, mapVoteSlot);
            } else {
                setSlot(2, mapVoteSlot);
            }
        }

        // 로비로 돌아가기
        if (game.getConfig().isUseWaitQuickBarLobby()) {
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