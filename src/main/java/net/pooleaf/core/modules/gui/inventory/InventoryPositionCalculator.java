package net.pooleaf.core.modules.gui.inventory;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InventoryPositionCalculator {

    public static int getX(int position, int width) {
        return (position % width) + 1;
    }

    public static int getY(int position, int width) {
        return (position / width) + 1;
    }

    public static int calculatePosition(int x, int y) {
        System.out.println(x + " / " + y + " / " + (x + (y * 9) - 1));
        return x + ((y - 1) * 9) - 1;
    }

}
