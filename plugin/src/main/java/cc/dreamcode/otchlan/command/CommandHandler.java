package cc.dreamcode.otchlan.command;

import cc.dreamcode.otchlan.OtchlanPlugin;
import cc.dreamcode.otchlan.command.annotations.RequiredPermission;
import cc.dreamcode.otchlan.command.annotations.RequiredPlayer;
import cc.dreamcode.otchlan.config.MessageConfig;
import cc.dreamcode.otchlan.exception.PluginRuntimeException;
import cc.dreamcode.otchlan.exception.PluginValidatorException;
import cc.dreamcode.otchlan.notice.NoticeSender;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public abstract class CommandHandler extends Command implements PluginIdentifiableCommand, NoticeSender, CommandPlatform {

    private final List<Class<? extends ArgumentHandler>> argumentHandlers = new ArrayList<>();

    public CommandHandler(String name, List<String> aliases) {
        super(name);
        if (aliases != null) {
            setAliases(aliases);
        }
    }

    @NonNull
    @Override
    public Plugin getPlugin() {
        return OtchlanPlugin.getOtchlanPlugin();
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String commandLabel, @NonNull String[] arguments) {
        final MessageConfig messageConfig = OtchlanPlugin.getOtchlanPlugin().getInject(MessageConfig.class)
                .orElseThrow(() -> new PluginRuntimeException("Plugin can not get an object from a injector."));

        final CommandPlatform commandPlatform = this.getCommandMethods(arguments);
        try {
            RequiredPermission requiredPermission = commandPlatform.getClass().getAnnotation(RequiredPermission.class);
            if (requiredPermission != null && !sender.hasPermission(requiredPermission.permission().equals("")
                    ? "rpl." + this.getName()
                    : requiredPermission.permission())) {
                throw new PluginValidatorException(messageConfig.noPermission);
            }

            RequiredPlayer requiredPlayer = commandPlatform.getClass().getAnnotation(RequiredPlayer.class);
            if (requiredPlayer != null && !(sender instanceof Player)) {
                throw new PluginValidatorException(messageConfig.notPlayer);
            }

            commandPlatform.handle(sender, arguments);
        }
        catch (PluginValidatorException e) {
            if (e.getReplaceMap().isEmpty()) {
                this.send(e.getNotice(), sender);
            }
            else {
                this.send(e.getNotice(), sender, e.getReplaceMap());
            }
        }
        return true;
    }

    public @NonNull List<String> tabComplete(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commandCompletions = this.getCommandMethods(args).tab((Player) sender, args);
        if (commandCompletions != null) {
            completions.addAll(commandCompletions);
        }

        this.argumentHandlers
                .stream()
                .map(argumentClass -> OtchlanPlugin.getOtchlanPlugin().createInstance(argumentClass))
                .filter(argumentHandler -> args.length == argumentHandler.getArg())
                .forEach(argumentHandler -> {
                    RequiredPermission requiredPermission = argumentHandler.getClass().getAnnotation(RequiredPermission.class);
                    if (requiredPermission == null || !sender.hasPermission(requiredPermission.permission())) {
                        return;
                    }

                    completions.add(argumentHandler.getName());
                });

        return completions;
    }

    private CommandPlatform getCommandMethods(@NonNull String[] args) {
        final AtomicReference<CommandPlatform> commandMethodsReference = new AtomicReference<>(this);
        this.argumentHandlers
                .stream()
                .map(argumentClass -> OtchlanPlugin.getOtchlanPlugin().createInstance(argumentClass))
                .filter(argumentHandler -> args.length >= argumentHandler.getArg() &&
                        args[argumentHandler.getArg() - 1].equalsIgnoreCase(argumentHandler.getName()))
                .findFirst()
                .ifPresent(commandMethodsReference::set);

        return commandMethodsReference.get();
    }

    public void addArgument(@NonNull Class<? extends ArgumentHandler> argumentClass) {
        this.argumentHandlers.add(argumentClass);
    }

}
