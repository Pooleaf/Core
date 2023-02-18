package net.pooleaf.core.modules.gui.bukkit.sign;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import net.pooleaf.core.modules.gui.GuiModule;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

@Data
public class SignGui {

    // 표지판 텍스트 4줄
    private String[] lines = {"", "", "", ""};


    public SignGui(String... lines) {
        for (int i = 0; i < lines.length; i++) {
            this.lines[i] = lines[i];
        }
    }

    /**
     * lineNumber번째 줄의 텍스트를 설정합니다.
     * lineNumber은 1부터 4까지 설정할 수 있습니다.
     */
    public void setLine(int lineNumber, String text) {
        Preconditions.checkArgument(1 <= lineNumber && lineNumber <= 4, "lineNumber는 1부터 4까지만 사용할 수 있습니다.");

        if (text == null) {
            lines[lineNumber - 1] = "";
        } else {
            lines[lineNumber - 1] = text;
        }
    }

    /**
     * lineNumber번째 줄의 텍스트를 반환합니다.
     * lineNumber은 1부터 4까지 설정할 수 있습니다.
     */
    public String getLine(int lineNumber) {
        Preconditions.checkArgument(1 <= lineNumber && lineNumber <= 4, "lineNumber는 1부터 4까지만 사용할 수 있습니다.");

        return lines[lineNumber - 1];
    }

    /**
     * 플레이어에게 표지판 GUI를 보여줍니다.
     */
    @SneakyThrows
    public void open(Player player) {
        player.closeInventory();

        GuiModule.getSignGuiManager().set(player.getUniqueId(), this);

        // 가상 표지판 블럭 생성
        Location location = new Location(player.getWorld(), 0, 0, 0);

        BlockPosition blockPosition = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        player.sendBlockChange(blockPosition.toLocation(player.getLocation().getWorld()), Material.WALL_SIGN, (byte) 0);

        // 표지판 패킷 생성
        PacketContainer openSignPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        PacketContainer signDataPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TILE_ENTITY_DATA);

        openSignPacket.getBlockPositionModifier().write(0, blockPosition);

        NbtCompound signNBT = (NbtCompound) signDataPacket.getNbtModifier().read(0);
        for (int i = 0; i < 4; i++) {
            signNBT.put("Text" + (i + 1), "{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', lines[i]) + "\"}");
        }

        signNBT.put("x", location.getBlockX());
        signNBT.put("y", location.getBlockY());
        signNBT.put("z", location.getBlockZ());
        signNBT.put("id", "minecraft:sign");

        signDataPacket.getBlockPositionModifier().write(0, blockPosition);
        signDataPacket.getIntegers().write(0, 9);
        signDataPacket.getNbtModifier().write(0, signNBT);

        // 패킷 보내기
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, signDataPacket);
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, openSignPacket);
    }

    /**
     * 이 GUI를 보고 있는 플레이어 목록을 반환합니다.
     * @return
     */
    public List<Player> getViewers() {
        return GuiModule.getSignGuiManager().getViewers(this);
    }

    /**
     * 표지판 작성 완료 시 호출됩니다.
     */
    public void onSignComplete(Player player, String[] lines) {}

}
