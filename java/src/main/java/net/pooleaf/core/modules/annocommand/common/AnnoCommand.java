package net.pooleaf.core.modules.annocommand.common;

import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import lombok.Data;
import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.commonscheduler.CommonSchedulerModule;
import net.pooleaf.core.modules.support.common.component.SimpleComponentBuilder;
import net.pooleaf.core.plugin.CorePlugin;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class AnnoCommand {

    private final String ARGUMENT_REGEX = "<.[^>]*>|\\[.[^\\]]*\\]"; // <argument> or [argument]
    private final Pattern ARGUMENT_PATTERN = Pattern.compile(ARGUMENT_REGEX);

    private final CorePlugin plugin;

    private String parent;
    private List<String> name;

    private String arguments;
    private int argumentsLength;

    private String description;

    private String permission;

    private CommonChatColor color;

    private boolean playerOnly;
    private boolean consoleOnly;

    private boolean helpCommand;
    private String helpCommandTarget;

    private boolean async;

    private Object executeInstance;
    private Method executeMethod;


    public void setArguments(String arguments) {
        this.arguments = arguments;

        Matcher matcher = ARGUMENT_PATTERN.matcher(arguments);

        argumentsLength = 0;
        while (matcher.find()) {
            argumentsLength++;
        }
    }

    public boolean hasParent() {
        return parent != null;
    }

    public boolean hasPermission() {
        return permission != null;
    }

    public String getCommandLine() {
        if (!hasParent()) {
            return name.get(0);
        } else {
            return parent + " " + name.get(0);
        }
    }

    public int getCommandLength() {
        String commandLine = getCommandLine();

        int length = 1;
        int lastIndex = 0;
        while ((lastIndex = commandLine.indexOf(" ", lastIndex)) != -1) {
            length++;
            lastIndex++;
        }

        return length;
    }

    public BaseComponent getUsage(String entered) {
        SimpleComponentBuilder builder = new SimpleComponentBuilder();

        // 명령어
        String command = "";
        if (entered == null) {
            command = "/" + getCommandLine();
        } else {
            command = "/" + entered + " " + name.get(0);
        }
        builder.text(command)
                .hoverShowText("클릭시 명령어가 채팅창에 입력됩니다.")
                .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, command);

        if (arguments != null) {
            builder.addExtra(" " + arguments);
        }

        if (description != null) {
            String descriptionColor = "";
            if (color != null) {
                descriptionColor = color.toString();
            } else if (plugin != null) {
                descriptionColor = plugin.getColor().toString();
            }

            builder.addExtra(descriptionColor + " - " + description);
        }

        return builder.build();
    }

    @SneakyThrows
    public void execute(CommandResult result) {
        // 메소드 첫번째 파라미터가 CommonCommandSender일 경우 변환해서 호출
        if (CommonCommandSender.class.isAssignableFrom(executeMethod.getParameterTypes()[0])) {
            executeMethod.invoke(executeInstance, CommonSenderModule.getOnlineCommandSenderByPlatformSender(result.getSender()), result);
        } else {
            executeMethod.invoke(executeInstance, result.getSender(), result);
        }
    }

    public void executeAsync(CommandResult result) {
        CommonSchedulerModule.getScheduler().runAsync(Core.getPlugin(), () -> execute(result));
    }

}
