package net.pooleaf.core.modules.gui.bukkit.quickbar;

import lombok.SneakyThrows;
import net.pooleaf.core.modules.support.bukkit.util.BukkitReflectionUtil;
import net.pooleaf.core.modules.support.bukkit.util.CraftItemUtil;
import net.pooleaf.core.modules.support.bukkit.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public abstract class FakeSlot extends Slot {

    @Override
    protected ItemStack updateItem() {
        return new ItemBuilder(Material.STONE_BUTTON)
                .displayName("§f")
                .build();
    }

    protected abstract ItemStack updateItem(Player player);

    public final void update(Player player, int itemPosition) {
        ItemStack itemStack = updateItem(player);
        sendPacket(player, itemPosition, itemStack);
    }

    @SneakyThrows
    private void sendPacket(Player player, int itemPosition, ItemStack itemStack) {
        Object packetObject = BukkitReflectionUtil.getNmsClass("PacketPlayOutSetSlot").newInstance();
        Field aField = packetObject.getClass().getDeclaredField("a");
        Field bField = packetObject.getClass().getDeclaredField("b");
        Field cField = packetObject.getClass().getDeclaredField("c");

        aField.setAccessible(true);
        bField.setAccessible(true);
        cField.setAccessible(true);

        aField.set(packetObject, 0);
        bField.set(packetObject, 36 + itemPosition);
        cField.set(packetObject, CraftItemUtil.asNMSCopy(itemStack));

        BukkitReflectionUtil.sendPacket(player, packetObject);
    }


}
