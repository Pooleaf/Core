package net.pooleaf.core.commands;

import net.pooleaf.core.CorePermission;
import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.annocommand.common.HelpCommandResult;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.support.bukkit.particle.Particle;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.component.SimpleComponentBuilder;
import net.pooleaf.core.modules.support.common.pageable.PageableCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;
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
            arguments = "<사운드> (볼륨) (Pitch) (플레이어)",
            permission = CorePermission.ADMIN
    )
    public void test_sound(CommandSender sender, CommandResult result) {
        String soundName = result.getArgument(0);
        Sound sound = Sound.valueOf(soundName);
        if (sound == null) {
            sender.sendMessage("§c존재하지 않는 사운드입니다.");
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

        String targetPlayerName = result.getArgument(3);
        Player targetPlayer = null;
        if (targetPlayerName == null) {
            if (sender instanceof Player) {
                targetPlayer = (Player) sender;
            }
        } else {
            targetPlayer = Bukkit.getPlayer(targetPlayerName);
        }

        if (targetPlayer == null) {
            sender.sendMessage("§c존재하지 않는 플레이어입니다.");
            return;
        }

        targetPlayer.playSound(targetPlayer.getLocation(), sound, volume, pitch);
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

    @Command(
            parent = "core test",
            name = {"particle"},
            arguments = "<파티클> (개수) (X) (Y) (Z) (OffsetX) (OffsetY) (OffsetZ)",
            permission = CorePermission.ADMIN
    )
    public void test_particle(Player player, CommandResult result) {
        String particleName = result.getArgument(0);
        Particle particle = Particle.valueOf(particleName);
        if (particle == null) {
            player.sendMessage("§c존재하지 않는 파티클입니다.");
            return;
        }

        Integer count = result.getArgumentAsInt(1);
        if (count == null) {
            count = 1;
        }

        Double x = result.getArgumentAsDouble(2);
        if (x == null) {
            x = player.getTargetBlock((Set<Material>) null, 3).getLocation().getX();
        }

        Double y = result.getArgumentAsDouble(3);
        if (y == null) {
            y = player.getTargetBlock((Set<Material>) null, 3).getLocation().getY();
        }

        Double z = result.getArgumentAsDouble(4);
        if (z == null) {
            z = player.getTargetBlock((Set<Material>) null, 3).getLocation().getZ();
        }

        Float offsetX = result.getArgumentAsFloat(5);
        if (offsetX == null) {
            offsetX = 0.0F;
        }

        Float offsetY = result.getArgumentAsFloat(6);
        if (offsetY == null) {
            offsetY = 0.0F;
        }

        Float offsetZ = result.getArgumentAsFloat(7);
        if (offsetZ == null) {
            offsetZ = 0.0F;
        }

        particle.spawn(x, y, z, offsetX, offsetY, offsetZ, 0, count);
    }

    @Command(
            parent = "core test",
            name = {"particleList"},
            arguments = "(페이지) (개수) (X) (Y) (Z) (R) (G) (B)",
            permission = CorePermission.ADMIN
    )
    public void test_particleList(CommonPlayer<Player> player, CommandResult result) {
        Integer count = result.getArgumentAsInt(1);
        if (count == null) {
            count = 1;
        }

        Double x = result.getArgumentAsDouble(2);
        if (x == null) {
            x = player.getPlatformSender().getTargetBlock((Set<Material>) null, 3).getLocation().getX();
        }

        Double y = result.getArgumentAsDouble(3);
        if (y == null) {
            y = player.getPlatformSender().getTargetBlock((Set<Material>) null, 3).getLocation().getY();
        }

        Double z = result.getArgumentAsDouble(4);
        if (z == null) {
            z = player.getPlatformSender().getTargetBlock((Set<Material>) null, 3).getLocation().getZ();
        }

        Float offsetX = result.getArgumentAsFloat(5);
        if (offsetX == null) {
            offsetX = 0.0F;
        }

        Float offsetY = result.getArgumentAsFloat(6);
        if (offsetY == null) {
            offsetY = 0.0F;
        }

        Float offsetZ = result.getArgumentAsFloat(7);
        if (offsetZ == null) {
            offsetZ = 0.0F;
        }

        Integer finalCount = count;
        Double finalX = x;
        Double finalY = y;
        Double finalZ = z;
        Float finalOffsetX = offsetX;
        Float finalOffsetY = offsetY;
        Float finalOffsetZ = offsetZ;
        new PageableCommand<Particle>(result.getEntered(), Arrays.stream(Particle.values()).filter(Particle::isSupported).collect(Collectors.toList()), 15) {
            @Override
            public CommonChatColor getHeaderColor() {
                return CommonChatColor.YELLOW;
            }

            @Override
            public String getHeaderMessage() {
                return "파티클 목록";
            }

            @Override
            public String getPageMoveCommand(int page) {
                return "/" + getEntered() + " " + page + " " + finalCount + " " + finalX + " " + finalY + " " + finalZ + " " + finalOffsetX + " " + finalOffsetY + " " + finalOffsetZ;
            }

            @Override
            public Object handleValue(Particle value, int index) {
                return new SimpleComponentBuilder(value.name())
                        .hoverShowText("클릭 시 " + value.name() + "를 [X: " + finalX + ", Y: " + finalY + ", Z: " + finalZ + "]에 보여줍니다.")
                        .clickRunCommand("/core test particle " + value.name() + " " + finalCount + " " + finalX + " " + finalY + " " + finalZ + " " + finalOffsetX + " " + finalOffsetY + " " + finalOffsetZ)
                        .build();
            }
        }.sendPage(player, result.getArgumentAsInt(0));
    }

}