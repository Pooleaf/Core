package net.pooleaf.core.modules.game.bukkit.quickbar;

import net.pooleaf.core.modules.game.bukkit.game.Game;
import net.pooleaf.core.modules.game.bukkit.player.GamePlayer;
import net.pooleaf.core.modules.gui.bukkit.inventory.pageable.LargePageableGui;
import net.pooleaf.core.modules.support.bukkit.util.ItemBuilder;
import net.pooleaf.core.modules.support.bukkit.util.TeleportUtil;
import net.pooleaf.core.modules.support.common.messager.Messager;
import org.bukkit.inventory.ItemStack;

import javax.swing.*;

public class ObserverTeleporterGui extends LargePageableGui {

    private Game<GamePlayer> game;

    public ObserverTeleporterGui(Game game) {
        super("관전할 플레이어를 선택하세요.");

        this.game = game;

        updateAsynchronously();
    }

    @Override
    public void onUpdate() {
        clear();

/*        game.getLivePlayers().stream()
                .filter(player -> player.isShowToObserver())
                .forEach(player -> {
                    addItem(new Icon() {
                        private GamePlayer gamePlayer = player;

                        @Override
                        protected ItemStack updateItem() {
                            return new ItemBuilder()
                                    .skull(player.getName())
                                    .displayName("§e§l" + player.getDisplayName())
                                    .lore("클릭 시 §e순간이동§f합니다.")
                                    .build();
                        }

                        @Override
                        public void onClick(GuiClickEvent event) {
                            if (gamePlayer.isDropout()) {
                                Messager.warning(event.getPlayer(), "이미 탈락한 플레이어입니다.");
                                return;
                            } else if (!gamePlayer.isOnline()) {
                                Messager.warning(event.getPlayer(), "접속 중이 아닌 플레이어입니다.");
                                return;
                            }

                            TeleportUtil.teleport(event.getPlayer(), gamePlayer.getPlayer().getLocation());
                            Messager.message(event.getPlayer(), gamePlayer.getDisplayName() + " §e님께 텔레포트했습니다.");
                        }
                    });
                });*/
    }

}
