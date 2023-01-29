package net.pooleaf.core.modules.gui.bukkit.inventory.pageable;

import net.pooleaf.core.modules.support.bukkit.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SmallPageableGui extends PageableGui {

    public SmallPageableGui(String title) {
        super(title, 3);

        // Panel 생성
        setItemPanel(createPanel("itemPanel", 1, 1, 7, 3));
        setPagePanel(createPanel("pagePanel", 9, 1, 1, 1));

        // 장식 아이템 배치
        ItemStack decoItem = new ItemBuilder(Material.STAINED_GLASS_PANE).displayName("").build();
        getMainPanel().set(8, 1, decoItem);
        getMainPanel().set(8, 2, decoItem);
        getMainPanel().set(8, 3, decoItem);

        // 페이지 Icon 배치
        getPagePanel().set(9, 1, getPreviousPageIcon());
        getPagePanel().set(9, 2, getCurrentPageIcon());
        getPagePanel().set(9, 3, getNextPageIcon());
    }

}
