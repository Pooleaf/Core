package net.pooleaf.core.modules.gui.bukkit.inventory.pageable;

import lombok.Data;
import lombok.SneakyThrows;
import net.pooleaf.core.modules.gui.bukkit.inventory.FakeInventoryIcon;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryGui;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryPanel;
import net.pooleaf.core.modules.gui.bukkit.inventory.events.InevntoryGuiClickEvent;
import net.pooleaf.core.modules.gui.bukkit.inventory.events.InventoryGuiCloseEvent;
import net.pooleaf.core.modules.support.bukkit.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PageableGui extends InventoryGui {

    private InventoryPanel itemPanel;
    private InventoryPanel pagePanel;

    private List<Object> items = new ArrayList<>();

    private Map<Player, PageGui> playerPageGuis = new HashMap<>();


    public PageableGui(String title, int row) {
        super(title, row);
    }

    /**
     * ItemStack 또는 Icon을 추가합니다.
     * @param item ItemStack 또는 Icon
     */
    public void addItem(Object item) {
        items.add(item);
    }

    /**
     * 모든 항목을 삭제합니다.
     */
    public void clear() {
        items.clear();
    }

    /**
     * 해당 페이지에 해당하는 ItemStack 또는 Icon들을 반환합니다.
     * 이 메소드를 오버라이딩하여 DB 등에서 동적 로딩할 수 있습니다.
     * @param page 목록을 가져올 페이지
     * @return ItemStack 또는 Icon 목록
     */
    public List<Object> getPageItems(int page) {
        List<Object> pageItems = new ArrayList<>();

        int panelSize = (itemPanel.getWidth() * itemPanel.getHeight());
        for (int i = (page - 1) * panelSize; i < page * panelSize; i++) {
            if (items.size() <= i) break;

            pageItems.add(items.get(i));
        }

        return pageItems;
    }

    /**
     * 최대 페이지를 반환합니다.
     * 이 메소드를 오버라이딩하여 DB 등에서 동적 로딩할 수 있습니다.
     * @return 최대 페이지
     */
    public int getMaxPage() {
        return items.isEmpty() ? 1 : (int) Math.ceil((float) items.size() / (itemPanel.getWidth() * itemPanel.getHeight()));
    }

    public void open(Player player, int page) {
        PageGui pageGui = new PageGui(getTitle(), getRow(), this, player);
        playerPageGuis.put(player, pageGui);
        pageGui.setCurrentPage(page);
        pageGui.updateAsynchronously();
        pageGui.open(player);
    }

    @Override
    public void open(Player player) {
        open(player, 1);
    }

    @Override
    public void update() {
        onUpdate();

        for (PageGui pageGui : playerPageGuis.values()) {
            pageGui.updateAsynchronously();
        }
    }

    /**
     * 이전 페이지 Icon을 반환합니다.
     * @return 이전 페이지 Icon
     */
    public FakeInventoryIcon createPreviousPageIcon() {
        return new FakeInventoryIcon() {

            private PageableGui parentPageableGui = PageableGui.this;

            @Override
            protected ItemStack updateItem(Player player) {
                PageGui pageGui = parentPageableGui.playerPageGuis.get(player);
                if (pageGui == null || pageGui.getCurrentPage() == parentPageableGui.getMaxPage()) return null;

                return new ItemBuilder(Material.PAPER)
                        .displayName("§e§l이전")
                        .build();
            }

            @Override
            public void onClick(InevntoryGuiClickEvent event) {
                if (getItem() == null) return;

                Player player = event.getPlayer();
                PageGui pageGui = parentPageableGui.playerPageGuis.get(player);
                parentPageableGui.open(player, pageGui.getCurrentPage() - 1);
            }

        };
    }

    /**
     * 다음 페이지 Icon을 반환합니다.
     * @return 다음 페이지 Icon
     */
    public FakeInventoryIcon createNextPageIcon() {
        return new FakeInventoryIcon() {

            private PageableGui parentPageableGui = PageableGui.this;

            @Override
            protected ItemStack updateItem(Player player) {
                PageGui pageGui = parentPageableGui.playerPageGuis.get(player);
                if (pageGui == null || pageGui.getCurrentPage() == parentPageableGui.getMaxPage()) return null;

                return new ItemBuilder(Material.PAPER)
                        .displayName("§e§l다음")
                        .build();
            }

            @Override
            public void onClick(InevntoryGuiClickEvent event) {
                if (getItem() == null) return;

                Player player = event.getPlayer();
                PageGui pageGui = parentPageableGui.playerPageGuis.get(player);
                parentPageableGui.open(player, pageGui.getCurrentPage() + 1);

                event.setCancelled(true);
            }

        };
    }

    /**
     * 현재 페이지 Icon을 반환합니다.
     * @return 현재 페이지 Icon
     */
    public FakeInventoryIcon createCurrentPageIcon() {
        return new FakeInventoryIcon() {

            private PageableGui parentPageableGui = PageableGui.this;

            @Override
            protected ItemStack updateItem(Player player) {
                PageGui pageGui = parentPageableGui.playerPageGuis.get(player);
                if (pageGui == null) return null;

                return new ItemBuilder(Material.BOOK)
                        .displayName("§f§l" + pageGui.getCurrentPage() + " / " + parentPageableGui.getMaxPage() +" §e§l페이지")
                        .build();
            }

        };
    }


    @Data
    class PageGui extends InventoryGui {

        private final PageableGui parentPageableGui;
        private final Player player;


        private InventoryPanel itemPanel;
        private InventoryPanel pagePanel;

        private int currentPage = 1;


        @SneakyThrows
        public PageGui(String title, int row, PageableGui parentPageableGui, Player player) {
            super(title, row);

            this.parentPageableGui = parentPageableGui;
            this.player = player;

            // 부모 GUI와 똑같이 패널 생성
            for (Map.Entry<Integer, InventoryPanel> entry : parentPageableGui.getPanels().entrySet()) {
                int position = entry.getKey();
                InventoryPanel parentPanel = entry.getValue();

                // 아이템 패널은 새로 생성
                if (parentPanel.getName().equals(parentPageableGui.getItemPanel().getName())) {
                    this.itemPanel = createPanel(parentPanel.getName(), position, parentPanel.getWidth(), parentPanel.getHeight());
                }
                // 페이지 패널은 부모 GUI거 사용
                else if (parentPanel.getName().equals(parentPageableGui.getPagePanel().getName())) {
                    this.pagePanel = parentPanel;
                    getPanels().put(position, parentPanel);
                }
            }
        }

        @Override
        public void onUpdate() {
            if (currentPage > parentPageableGui.getMaxPage()) {
                currentPage = parentPageableGui.getMaxPage();
            }

            itemPanel.getItems().clear();

            for (Object pageItem : parentPageableGui.getPageItems(currentPage)) {
                itemPanel.add(pageItem);
            }

            itemPanel.update();
            pagePanel.update();
        }

        @Override
        public void onClose(InventoryGuiCloseEvent event) {
            parentPageableGui.playerPageGuis.remove(player);
        }

    }

}
