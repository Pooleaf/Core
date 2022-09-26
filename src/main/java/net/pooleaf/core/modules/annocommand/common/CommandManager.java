package net.pooleaf.core.modules.annocommand.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.common.platform.Platform;
import net.pooleaf.core.modules.support.common.util.ReflectionUtil;
import net.pooleaf.core.plugin.CorePlugin;

public class CommandManager {

    @Getter(AccessLevel.PACKAGE)
    private PlatformAdapter platformAdapter;

    @Setter
    @Getter
    private int helpCommandCountPerPage = 7;

    @Getter
    private List<AnnoCommand> commands = new ArrayList<>();


    public void init(CorePlugin plugin) {
        platformAdapter = PlatformAdapterFactory.createPlatformAdapter();

        platformAdapter.init(plugin);
    }

    /**
     * 명령어를 등록합니다.
     * @param command 등록할 명령어
     */
    public void registerCommand(AnnoCommand command) {
        if (!platformAdapter.registerCommand(command)) return;

        commands.add(command);
    }

    /**
     * Class에 작성된 명령어들을 등록합니다.
     * @Command Annotation을 가지고 있고, static으로 선언된 메소드만 등록됩니다.
     * @param commandClass 명령어를 등록할 Class
     */
    @SneakyThrows
    public void registerCommands(Class commandClass) {
        for (Method method : ReflectionUtil.getMethodsInOrder(commandClass)) {
            Command anno = method.getAnnotation(Command.class);
            if (anno == null) continue;

            // 파라미터 2자리인지 체크, 2번째 자리 CommandResult인지 체크
            if (method.getParameterCount() != 2) continue;
            if (!CommandResult.class.isAssignableFrom(method.getParameterTypes()[1])) continue;

            AnnoCommand command = new AnnoCommand(Core.getPluginManager().getPluginByPackage(commandClass.getCanonicalName()));

            // 부모 명령어 설정
            if (anno.parent().length() > 0) {
                command.setParent(anno.parent());
            }

            // 명령어 이름
            List<String> names = new ArrayList<>();
            for (String name : anno.name()) {
                names.add(name);

                if (anno.usePlatformPrefix()) {
                    names.add(Platform.getCurrentPlatform().getPrefix() + name);
                }
            }
            command.setName(names);

            // args
            if (anno.arguments().length() > 0) {
                command.setArguments(anno.arguments());
            }

            // 명령어 설명
            if (anno.description().length() > 0) {
                command.setDescription(anno.description());
            }

            // 권한
            if (anno.permission().length() > 0) {
                command.setPermission(anno.permission());
            }

            // 도움말 명령어인지
            command.setHelpCommand(anno.helpCommand());

            // 비동기 명령어인지
            command.setAsync(anno.async());

            // 명령어 메소드
            command.setExecuteMethod(method);

            registerCommand(command);
        }
    }

    /**
     * CorePlugin의 모든 Class들에서 명령어를 찾아 등록합니다.
     * @Command Annotation을 가지고 있고, static으로 선언된 메소드만 등록됩니다.
     * @param plugin 명령어를 등록할 CorePlugin
     */
    public void registerCommands(CorePlugin plugin) {
        ReflectionUtil.getClasses(plugin).forEach(targetClass -> registerCommands(targetClass));
    }

    /**
     * 입력한 명령어로 AnnoCommand를 찾아 반환합니다.
     * @param commandLine 입력한 명령어
     * @return 찾은 AnnoCommand
     */
    public AnnoCommand getCommand(String commandLine) {
        String[] split = commandLine.split(" ");

        String label = split[0];

        // args가 없을 경우
        if (split.length < 2) {
            for (AnnoCommand command : commands) {
                if (command.hasParent()) continue;

                for (String name : command.getName()) {
                    if (name.equalsIgnoreCase(label)) return command;
                }
            }
        }
        // args가 있을 경우
        else {
            AnnoCommand foundCommand = getCommand(label);
            if (foundCommand == null) return null;

            for (int i = 1; i < split.length; i++) {
                String argument = split[i];

                for (AnnoCommand command : commands) {
                    if (!command.hasParent()) continue;
                    if (command.getCommandLength() != i + 1) continue;

                    if (command.getCommandLine().startsWith(foundCommand.getCommandLine() + " ")) {
                        for (String name : command.getName()) {
                            if (name.equalsIgnoreCase(argument)) {
                                foundCommand =command;
                                break;
                            }
                        }
                    }
                }
            }

            return foundCommand;
        }

        return null;
    }

    /**
     * 부모 명령어의 자식 명령어를 deep만큼 깊이에서 반환합니다.
     * @param parentCommand 부모 명령어
     * @param deep args 깊이
     * @return 자식 명령어
     */
    public List<AnnoCommand> getSubCommands(AnnoCommand parentCommand, int deep) {
        String parentCommandLine = parentCommand.getCommandLine() + " ";
        int commandLengthLimit = parentCommand.getCommandLength() + deep;

        List<AnnoCommand> subCommands = new ArrayList<>();

        for (AnnoCommand command : commands) {
            if (command.getCommandLine().startsWith(parentCommandLine) && command.getCommandLength() <= commandLengthLimit) {
                subCommands.add(command);
            }
        }

        return subCommands;
    }

    /**
     * 입력한 명령어에 대한 권한이 있는 추천 명령어 목록을 반환합니다.
     * @param sender 입력자
     * @param commandLine 입력한 명령어
     * @return 추천 명령어 목록
     */
    public List<String> getSuggestions(Object sender, String commandLine) {
        List<String> suggestions = new ArrayList<>();

        String lastArgument = commandLine.substring(commandLine.lastIndexOf(" ")).trim();

        AnnoCommand parentCommand = getCommand(commandLine);
        getSubCommands(parentCommand, 1)
                .stream()
                .filter(subCommand -> subCommand.getPermission() == null || platformAdapter.hasPermission(sender, subCommand.getPermission()))
                .map(subCommand -> subCommand.getName())
                .forEach(name -> suggestions.addAll(name));

        return suggestions;
    }

    /**
     * sender가 입력한 명령어를 찾아 실행시킵니다.
     * @param sender 입력자
     * @param commandLine 입력한 명령어
     * @return 성공 여부
     */
    public boolean executeCommand(Object sender, String commandLine) {
        AnnoCommand command = getCommand(commandLine);
        if (command != null) {
            // 플레이어만
            if (command.isPlayerOnly()
                    && (!platformAdapter.isPlayer(sender) || (sender instanceof CommonCommandSender && ((CommonCommandSender) sender).isConsole()))) {
                platformAdapter.sendMessage(sender, "§c플레이어만 사용할 수 있는 명령어입니다.");
                return true;
            }
            // 콘솔만
            else if (command.isConsoleOnly() && !platformAdapter.isConsole(sender)
                    && (!platformAdapter.isPlayer(sender) || (sender instanceof CommonCommandSender && !((CommonCommandSender) sender).isConsole()))) {
                platformAdapter.sendMessage(sender, "§c콘솔에서만 사용할 수 있는 명령어입니다.");
                return true;
            }
            // 권한 체크
            else if (command.getPermission() != null && !platformAdapter.hasPermission(sender, command.getPermission())) {
                platformAdapter.sendMessage(sender, "§c명령어를 사용할 권한이 없습니다.");
                return true;
            }
            // args 길이 체크
            else if (commandLine.split(" ").length < command.getCommandLength() + command.getArgumentsLength()) {
                platformAdapter.sendMessage(sender, command.getUsage(null));
                return true;
            }
            // 도움말 명령어
            else if (command.isHelpCommand()) {
                HelpCommandResult result = new HelpCommandResult(command, sender, commandLine);

                List<AnnoCommand> subCommands = getSubCommands(command, 1)
                        .stream()
                        .filter(subCommand -> subCommand.getPermission() == null || platformAdapter.hasPermission(sender, subCommand.getPermission()))
                        .collect(Collectors.toList());

                Integer page = result.getArgumentAsInt(0);
                if (page == null) {
                    page = 1;
                }

                int maxPage = (int) Math.ceil((float) subCommands.size() / helpCommandCountPerPage);
                if (maxPage < 1) {
                    platformAdapter.sendMessage(sender, "§c하위 명령어가 없습니다.");
                    return true;
                }

                if (page < 1 || page > maxPage) {
                    platformAdapter.sendMessage(sender, "§c페이지는 1~" + maxPage + "만 입력할 수 있습니다.");
                    return true;
                }

                result.setPage(page);
                result.setMaxPage(maxPage);

                command.execute(result);

                platformAdapter.sendMessage(sender, command.getPlugin().getColor() + "[ " + command.getName().get(0) + " 명령어 목록 ] ( " + page + " / " + maxPage + " )");
                for (int i = (page - 1) * helpCommandCountPerPage; i < page * helpCommandCountPerPage; i++) {
                    if (i >= subCommands.size()) break;

                    platformAdapter.sendMessage(sender, subCommands.get(i).getUsage(null));
                }
            }
            // 명령어 실행
            else {
                if (command.isAsync()) {
                    command.executeAsync(new CommandResult(command, sender, commandLine));
                } else {
                    command.execute(new CommandResult(command, sender, commandLine));
                }
            }

            return true;
        }

        return false;
    }

}