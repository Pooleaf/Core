package net.pooleaf.core.gui.inventory;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PositionCalculator {

    public static int getX(int position, int width) {
        return (position % width) + 1;
    }

    public static int getY(int position, int width) {
        return (position / width) + 1;
    }

    public static int calculatePosition(int x, int y) {
        return (x - 1) * y;
    }

}
