package net.pooleaf.core.modules.gui.bukkit.inventory.pageable;

import net.pooleaf.core.modules.support.bukkit.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LargePageableGui extends PageableGui {

    public LargePageableGui(String title) {
        super(title, 6);

        // Panel 생성
        setItemPanel(createPanel("itemPanel", 1, 1, 9, 4));
        setPagePanel(createPanel("pagePanel", 1, 6, 9, 1));

        // 장식 아이템 배치
        ItemStack decoItem = new ItemBuilder(Material.STAINED_GLASS_PANE).displayName("").build();
        for (int i = 1; i <= 9; i++) {
            getMainPanel().set(i, 5, decoItem);
        }

        // 페이지 Icon 배치
        getPagePanel().set(4, 1, getPreviousPageIcon());
        getPagePanel().set(5, 1, getCurrentPageIcon());
        getPagePanel().set(6, 1, getNextPageIcon());
    }

}
