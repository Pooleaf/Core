package net.pooleaf.core.commands;

import net.pooleaf.core.CorePermission;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.component.SimpleComponentBuilder;
import net.pooleaf.core.modules.support.common.pageable.PageableCommand;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TestCommand {

    @Command(
            parent = {"core"},
            name = {"test"},
            description = "테스트 명령어 목록을 확인합니다.",
            helpCommand = true,
            permission = CorePermission.ADMIN
    )
    public void test(CommandSender sender, HelpCommandResult result) {
    }

    @Command(
            parent = "core test",
            name = {"sound"},
            arguments = "<사운드> (볼륨) (Pitch)",
            permission = CorePermission.ADMIN
    )
    public void test_sound(Player player, CommandResult result) {
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
            parent = "core test",
            name = {"soundList"},
            arguments = "(페이지) (볼륨) (Pitch)",
            permission = CorePermission.ADMIN
    )
    public void test_soundList(CommonPlayer player, CommandResult result) {
        Float volume = result.getArgumentAsFloat(1);
        if (volume == null) {
            volume = 1.0F;
        }

        Float pitch = result.getArgumentAsFloat(2);
        if (pitch == null) {
            pitch = 1.0F;
        }

        Float finalVolume = volume;
        Float finalPitch = pitch;
        new PageableCommand<Sound>(result.getEntered(), Arrays.stream(Sound.values()).collect(Collectors.toList()), 15) {
            @Override
            public CommonChatColor getHeaderColor() {
                return CommonChatColor.YELLOW;
            }

            @Override
            public String getHeaderMessage() {
                return "사운드 목록";
            }

            @Override
            public String getPageMoveCommand(int page) {
                return "/" + getEntered() + " " + page + " " + finalVolume + " " + finalPitch;
            }

            @Override
            public Object handleValue(Sound value, int index) {
                return new SimpleComponentBuilder(value.name())
                        .hoverShowText("클릭 시 " + value.name() + "를 볼륨 " + finalVolume + ", Pitch " + finalPitch + "로 들려줍니다.")
                        .clickRunCommand("/core test sound " + value.name() + " " + finalVolume + " " + finalPitch)
                        .build();
            }
        }.sendPage(player, result.getArgumentAsInt(0));
    }

}