package net.pooleaf.core.modules.gui.bukkit.inventory;

import lombok.SneakyThrows;
import net.pooleaf.core.modules.support.bukkit.util.BukkitReflectionUtil;
import net.pooleaf.core.modules.support.bukkit.util.CraftItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public abstract class FakeInventoryIcon extends InventoryIcon {

    @Override
    protected final ItemStack updateItem() {
        return null;
    }

    protected abstract ItemStack updateItem(Player player);

    @Override
    public final void update() {
    }

    public final void update(Player player, int itemPosition) {
        ItemStack itemStack = updateItem(player);
        sendPacket(player, itemPosition, itemStack);
    }

    @SneakyThrows
    private void sendPacket(Player player, int itemPosition, ItemStack itemStack) {
        Object entityPlayerObject = BukkitReflectionUtil.getHandle(player);
        Object activeContainerObject = entityPlayerObject.getClass().getField("activeContainer").get(entityPlayerObject);
        int windowId = activeContainerObject.getClass().getField("windowId").getInt(activeContainerObject);

        Object packetObject = BukkitReflectionUtil.getNmsClass("PacketPlayOutSetSlot").newInstance();
        Field aField = packetObject.getClass().getDeclaredField("a");
        Field bField = packetObject.getClass().getDeclaredField("b");
        Field cField = packetObject.getClass().getDeclaredField("c");

        aField.setAccessible(true);
        bField.setAccessible(true);
        cField.setAccessible(true);

        aField.set(packetObject, windowId);
        bField.set(packetObject, itemPosition);
        cField.set(packetObject, CraftItemUtil.asNMSCopy(itemStack));

        BukkitReflectionUtil.sendPacket(player, packetObject);
    }

}
