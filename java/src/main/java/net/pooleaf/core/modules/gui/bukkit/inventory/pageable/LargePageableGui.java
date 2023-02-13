package net.pooleaf.core.modules.gui.bukkit.inventory.pageable;

import net.pooleaf.core.modules.support.bukkit.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LargePageableGui extends PageableGui {

    public LargePageableGui(String title) {
        super(title, 6);

        // Panel 생성
        setItemPanel(createPanel("itemPanel", 1, 1, 9, 4));
        setPagePanel(createPanel("pagePanel", 1, 5, 9, 2));

        // 장식 아이템 배치
        ItemStack decoItem = new ItemBuilder(Material.STAINED_GLASS_PANE).displayName("§f").build();
        for (int i = 1; i <= 9; i++) {
            getPagePanel().set(i, 1, decoItem);
        }

        // 페이지 Icon 배치
        getPagePanel().set(4, 2, createPreviousPageIcon());
        getPagePanel().set(5, 2, createCurrentPageIcon());
        getPagePanel().set(6, 2, createNextPageIcon());
    }

}
