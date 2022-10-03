package net.pooleaf.core.commands;

import com.cryptomorin.xseries.XSound;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.support.common.component.SimpleComponentBuilder;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand {

    @Command(
            name = {"ctest"},
            permission = "core.admin",
            helpCommand = true
    )
    public static void ctest(CommandSender sender, HelpCommandResult result) {
    }

    @Command(
            parent = "ctest",
            name = {"sound"},
            arguments = "<사운드> (볼륨) (Pitch)"
    )
    public static void ctestSound(Player player, CommandResult result) {
        String soundName = result.getArgument(0);
        Sound sound = Sound.valueOf(soundName);
        if (sound == null) {
            player.sendMessage("§c존재하지 않는 Sound입니다.");
            return;
        }

        Float volume = result.getArgumentAsFloat(1);
        if (volume == null) {
            volume = 1.0F;
        }

        Float pitch = result.getArgumentAsFloat(2);
        if (pitch == null) {
            pitch = 1.0F;
        }

        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    @Command(
            parent = "ctest",
            name = {"soundlist"},
            arguments = "(페이지) (볼륨) (Pitch)"
    )
    public static void ctestSoundlist(Player player, CommandResult result) {
        Integer page = result.getArgumentAsInt(0);
        if (page == null) {
            page = 1;
        }

        int numberPerPage = 15;
        int maxPage = (int) (Math.floor(Sound.values().length / numberPerPage) + 1);
        if (page < 1 || maxPage < page) {
            player.sendMessage("§c페이지는 1~" + maxPage + "의 정수만 입력할 수 있습니다.");
            return;
        }

        Float volume = result.getArgumentAsFloat(1);
        if (volume == null) {
            volume = 1.0F;
        }

        Float pitch = result.getArgumentAsFloat(2);
        if (pitch == null) {
            pitch = 1.0F;
        }

        player.sendMessage("");
        player.sendMessage(new SimpleComponentBuilder("§e[ Sound 목록 " + page + " / " + maxPage + " ] ")
                .addExtra(new SimpleComponentBuilder("§e§l◀").hoverShowText("클릭 시 이전 페이지로 이동합니다.").clickRunCommand("/ctest soundlist " + (page - 1) + " " + volume + " " + pitch).build())
                .addExtra(new SimpleComponentBuilder("  ").build())
                .addExtra(new SimpleComponentBuilder("§e§l▶").hoverShowText("클릭 시 다음 페이지로 이동합니다.").clickRunCommand("/ctest soundlist " + (page + 1) + " " + volume + " " + pitch).build())
                .build());
        for (int i = (page - 1) * numberPerPage; i < page * numberPerPage; i++) {
            if (i >= Sound.values().length - 1) {
                break;
            }

            Sound sound = Sound.values()[i];

            player.sendMessage(new SimpleComponentBuilder(sound.name())
                    .hoverShowText("클릭 시 " + sound.name() + "를 볼륨 " + volume + ", Pitch " + pitch + "로 들려줍니다.")
                    .clickRunCommand("/ctest sound " + sound.name() + " " + volume + " " + pitch)
                    .build());
        }
    }

}