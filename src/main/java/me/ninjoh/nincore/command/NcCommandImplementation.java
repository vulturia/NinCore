package me.ninjoh.nincore.command;


import me.ninjoh.nincore.api.Core;
import me.ninjoh.nincore.api.CoreModule;
import me.ninjoh.nincore.api.command.CommandImplementation;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.executors.NinCommandExecutor;
import me.ninjoh.nincore.api.command.executors.NinSubCommandExecutor;
import me.ninjoh.nincore.api.localization.LocalizedString;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class NcCommandImplementation extends CoreModule implements CommandImplementation
{
    public NcCommandImplementation(Core core)
    {
        super(core);
    }


    @Override
    public NinCommand constructCommand(String name, boolean useStaticDescription, LocalizedString localizedDescription, String requiredPermission, NinCommandExecutor executor, List<NinSubCommand> subCommands, JavaPlugin plugin)
    {
        return NcCommand.construct(name, useStaticDescription, localizedDescription, requiredPermission, executor, subCommands, plugin);
    }


    @Override
    public NinSubCommand constructSubCommand(String name, boolean useStaticDescription, String staticDescription, LocalizedString localizedDescription, String requiredPermission, String usage, List<String> aliases, NinSubCommandExecutor executor, NinCommand parentCommand)
    {
        return NcSubCommand.construct(name, useStaticDescription, staticDescription, localizedDescription, requiredPermission, usage, aliases, executor, parentCommand);
    }
}
