package tk.martijn_heil.nincore.command;


import org.bukkit.plugin.java.JavaPlugin;
import tk.martijn_heil.nincore.NcCore;
import tk.martijn_heil.nincore.api.CoreModule;
import tk.martijn_heil.nincore.api.command.CommandImplementation;
import tk.martijn_heil.nincore.api.command.NinCommand;
import tk.martijn_heil.nincore.api.command.NinSubCommand;
import tk.martijn_heil.nincore.api.command.executors.NinCommandExecutor;
import tk.martijn_heil.nincore.api.command.executors.NinSubCommandExecutor;
import tk.martijn_heil.nincore.api.localization.LocalizedString;

import java.util.List;

public class NcCommandImplementation extends CoreModule<NcCore> implements CommandImplementation
{
    public NcCommandImplementation(NcCore core)
    {
        super(core);
    }


    @Override
    public String getName()
    {
        return "NcCommandImplementation";
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
