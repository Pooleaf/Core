package net.pooleaf.core.modules.annocommand.common;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;
import lombok.SneakyThrows;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.commonscheduler.CommonSchedulerModule;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.plugin.CorePlugin;

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

    private boolean playerOnly;
    private boolean consoleOnly;

    private boolean helpCommand;
    private boolean async;

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

    public String getUsage(String entered) {
        String usage = "§f";
        if (entered == null) {
            usage += "/" + getCommandLine();
        } else {
            usage += "/" + entered + " " + name.get(0);
        }

        if (arguments != null) {
            usage += " " + arguments;
        }

        if (description != null) {
            if (plugin != null) {
                usage += plugin.getColor();
            }

            usage += " - " + description;
        }

        return usage;
    }

    @SneakyThrows
    public void execute(CommandResult result) {
        // 메소드 첫번째 파라미터가 CommonCommandSender일 경우 변환해서 호출
        if (CommonCommandSender.class.isAssignableFrom(executeMethod.getParameterTypes()[0])) {
            executeMethod.invoke(null, CommonSenderModule.getOnlineCommandSenderByPlatformSender(result.getSender()), result);
        } else {
            executeMethod.invoke(null, result.getSender(), result);
        }
    }

    public void executeAsync(CommandResult result) {
        CommonSchedulerModule.getScheduler().runAsync(Core.getPlugin(), () -> execute(result));
    }

}
