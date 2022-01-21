package net.pooleaf.core.modules.gui.inventory.pageable;

import com.google.common.base.Preconditions;
import lombok.Data;
import net.pooleaf.core.modules.gui.inventory.InventoryGui;
import net.pooleaf.core.modules.gui.inventory.InventoryIcon;
import net.pooleaf.core.modules.gui.inventory.InventoryPanel;
import net.pooleaf.core.modules.gui.inventory.event.InevntoryGuiClickEvent;
import net.pooleaf.core.modules.support.bukkit.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageableGui extends InventoryGui {

    private InventoryPanel itemPanel;
    private InventoryPanel pagePanel;

    private int currentPage;

    private List<Object> items = new ArrayList<>();


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
        int panelSize = (itemPanel.getWidth() * itemPanel.getHeight());
        for (int i = (currentPage - 1) * panelSize; i < getMaxPage() * panelSize; i++) {
            if (items.size() <= i) break;

            itemPanel.add(items.get(i));
        }


        // TODO 이거 만들다 만듯
        return null;
    }

    /**
     * 최대 페이지를 반환합니다.
     * 이 메소드를 오버라이딩하여 DB 등에서 동적 로딩할 수 있습니다.
     * @return 최대 페이지
     */
    public int getMaxPage() {
        return (int) Math.ceil((float) items.size() / itemPanel.getWidth() * itemPanel.getHeight());
    }

    /**
     * 해당하는 페이지로 이동합니다.
     * @param page 이동할 페이지
     */
    public void gotoPage(int page) {
        Preconditions.checkArgument(page > getMaxPage(), "page는 최대 페이지보다 클 수 없습니다. (page: %d, 최대 페이지: %d)", page, getMaxPage());

        currentPage = page;

        itemPanel.getItems().clear();
        getPageItems(page).forEach(item -> itemPanel.add(item));

        itemPanel.updateAsynchronously();
        pagePanel.updateAsynchronously();
    }

    /**
     * 이전 페이지 Icon을 반환합니다.
     * @return 이전 페이지 Icon
     */
    public InventoryIcon getPreviousPageIcon() {
        return new InventoryIcon() {
            @Override
            protected ItemStack updateItem() {
                if (currentPage == 1) return null;

                return new ItemBuilder(Material.PAPER)
                        .displayName("§e§l이전")
                        .build();
            }

            @Override
            public void onClick(InevntoryGuiClickEvent event) {
                if (getItem() == null) return;

                gotoPage(--currentPage);
            }
        };
    }

    /**
     * 다음 페이지 Icon을 반환합니다.
     * @return 다음 페이지 Icon
     */
    public InventoryIcon getNextPageIcon() {
        return new InventoryIcon() {
            @Override
            protected ItemStack updateItem() {
                if (currentPage == getMaxPage()) return null;

                return new ItemBuilder(Material.PAPER)
                        .displayName("§e§l다음")
                        .build();
            }

            @Override
            public void onClick(InevntoryGuiClickEvent event) {
                if (getItem() == null) return;

                gotoPage(++currentPage);
            }
        };
    }

    public InventoryIcon getCurrentPageIcon() {
        return new InventoryIcon() {
            @Override
            protected ItemStack updateItem() {
                return new ItemBuilder(Material.BOOK)
                        .displayName("§f§l" + currentPage + " §e§l페이지")
                        .build();
            }
        };
    }

}
