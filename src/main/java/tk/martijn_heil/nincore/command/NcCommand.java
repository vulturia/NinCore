package tk.martijn_heil.nincore.command;


import com.google.common.base.Preconditions;
import tk.martijn_heil.nincore.NcCore;
import tk.martijn_heil.nincore.command.executors.HelpSubCmdExecutor;
import tk.martijn_heil.nincore.command.executors.InfoSubCmdExecutor;
import tk.martijn_heil.nincore.command.executors.ReloadSubCmdExecutor;
import tk.martijn_heil.nincore.command.handlers.NcCommandHandler;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import tk.martijn_heil.nincore.api.command.NinCommand;
import tk.martijn_heil.nincore.api.command.NinSubCommand;
import tk.martijn_heil.nincore.api.command.builders.SubCommandBuilder;
import tk.martijn_heil.nincore.api.command.executors.NinCommandExecutor;
import tk.martijn_heil.nincore.api.exceptions.technicalexceptions.SubCommandAliasAlreadyRegisteredException;
import tk.martijn_heil.nincore.api.exceptions.technicalexceptions.SubCommandAlreadyExistsException;
import tk.martijn_heil.nincore.api.localization.LocalizedString;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NcCommand extends CommandBase implements NinCommand
{
    private List<NinSubCommand> subCommands; // Optional
    private NinCommandExecutor executor; // Required
    private JavaPlugin plugin;


    public NcCommand(String name, boolean useStaticDescription, String staticDescription, LocalizedString localizedDescription, String requiredPermission, String usage, List<String> aliases, NinCommandExecutor executor, List<NinSubCommand> subCommands, JavaPlugin plugin)
    {
        super(name, useStaticDescription, staticDescription, localizedDescription, requiredPermission, usage, aliases);

        if (executor != null) this.executor = executor.init(this);
        this.subCommands = subCommands;
        this.plugin = plugin;

        plugin.getCommand(name).setExecutor(new NcCommandHandler(this));
    }


    public JavaPlugin getPlugin()
    {
        return plugin;
    }


    public static NcCommand construct(String name, boolean useStaticDescription, LocalizedString localizedDescription, String requiredPermission, NinCommandExecutor executor, List<NinSubCommand> subCommands, JavaPlugin plugin)
    {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(plugin);

        Command command = plugin.getCommand(name);

        String staticDescription = command.getDescription();
        if (requiredPermission == null) requiredPermission = command.getPermission();

        String usage = command.getUsage();
        List<String> aliases = command.getAliases();

        return new NcCommand(name, useStaticDescription, staticDescription, localizedDescription, requiredPermission, usage, aliases, executor, subCommands, plugin);
    }


    /**
     * Set this command's CommandExecutor.
     *
     * @param executor The CommandExecutor for this command.
     */
    @Override
    public void setExecutor(NinCommandExecutor executor)
    {
        this.executor = executor.init(this);
    }


    @Override
    public NinCommandExecutor getExecutor()
    {
        return this.executor;
    }


    /**
     * Get this command's sub commands.
     *
     * @return This command's sub commands. Can be an empty ArrayList.
     */
    @Override
    public List<NinSubCommand> getSubCommands()
    {
        return new ArrayList<>(subCommands);
    }


    /**
     * Add a sub-command to this command.
     *
     * @param subCommand The {@link NinSubCommand} to add.
     * @throws SubCommandAlreadyExistsException          If the sub command is already exists.
     * @throws SubCommandAliasAlreadyRegisteredException If the provided sub command has an alias which is already in use.
     */
    @Override
    public void addSubCommand(NinSubCommand subCommand) throws SubCommandAlreadyExistsException, SubCommandAliasAlreadyRegisteredException
    {
        if (subCommandExists(subCommand.getName()))
        {
            throw new SubCommandAlreadyExistsException();
        }

        // Check if the given sub command aliases aren't already registered.
        for (NinSubCommand subCmd : this.getSubCommands())
        {
            if (subCmd.hasAliases()) // NPE checks.
            {
                //noinspection ConstantConditions
                for (String alias : subCmd.getAliases(true))
                {
                    //noinspection ConstantConditions
                    if (subCommand.getAliases(true).contains(alias))
                    {
                        throw new SubCommandAliasAlreadyRegisteredException(alias);
                    }
                }
            }
        }

        // We only get here if all validation passes.
        subCommands.add(subCommand);
    }


    /**
     * Set this' {@link NinCommand}s sub-commands.
     *
     * @param subCommands The {@link NinSubCommand}s to set.
     */
    @Override
    public void setSubCommands(List<NinSubCommand> subCommands)
    {
        this.subCommands = subCommands;
    }


    /**
     * Delete a {@link NinSubCommand} from this command by name.
     *
     * @param name The name of the sub command to be deleted from this command.
     */
    @Override
    public void removeSubCommand(String name)
    {
        // If any sub commands exist for this command
        if (!subCommands.isEmpty())
        {
            Iterator<NinSubCommand> iter = subCommands.iterator();

            while(iter.hasNext())
            {
                NinSubCommand cmd =  iter.next();

                if(cmd.getName().equalsIgnoreCase(name))
                {
                    iter.remove();
                }
            }
        }
    }


    /**
     * Get a sub command by name.
     *
     * @param name The name of the sub command to get.
     * @return The NcSubCommand queried for. Can be null.
     */
    @Override
    public NinSubCommand getSubCommand(String name)
    {
        // If any sub commands exist for this command
        if (!subCommands.isEmpty())
        {
            // Loop over all sub commands
            for (NinSubCommand subCommand : subCommands)
            {
                // If this is the sub command queried for, return true.
                // Sub commands are case in-sensitive
                if (subCommand.getName().equals(name.toLowerCase()))
                {
                    return subCommand;
                }
            }
        }

        return null;
    }


    /**
     * Get a sub command by alias.
     *
     * @param alias The sub command alias to query for.
     * @return The NcSubCommand queried for. Can be null.
     */
    @Override
    public NinSubCommand getSubCommandByAlias(String alias)
    {
        for (NinSubCommand subCmd : subCommands)
        {
            if (subCmd.getAliases(true).contains(alias.toLowerCase()))
            {
                return subCmd;
            }
        }

        return null;
    }


    /**
     * Check if this command has sub commands.
     *
     * @return True/False, does this command have any sub commands?
     */
    @Override
    public boolean hasSubCommands()
    {
        return !subCommands.isEmpty();
    }


    /**
     * Check if a sub command exists by name.
     *
     * @param name The name to query for.
     * @return True/False, does this sub command exist?
     */
    @Override
    public boolean subCommandExists(String name)
    {
        // If any sub commands exist for this command
        if (subCommands != null && !subCommands.isEmpty())
        {
            // Loop over all sub commands
            for (NinSubCommand subCommand : subCommands)
            {
                // If this is the sub command queried for, return true.
                if (subCommand.getName().equals(name.toLowerCase()))
                {
                    return true;
                }
            }
        }

        // If the sub command doesn't exist.
        return false;
    }


    /**
     * Check if a sub command exists by alias.
     *
     * @param alias The alias to query for.
     * @return True/False, does this sub command exist?
     */
    @Override
    public boolean subCommandExistsByAlias(String alias)
    {
        for (NinSubCommand subCmd : subCommands)
        {
            if (subCmd.getAliases(true).contains(alias.toLowerCase()))
            {
                return true;
            }
        }

        return false;
    }


    @Override
    public void addDefaultHelpSubCmd()
    {
        // /command help. sub command.
        new SubCommandBuilder()
                .setName("help")
                .addAlias("?")
                .setUseStaticDescription(false)
                .setLocalizedDescription(new LocalizedString(NcCore.class.getClassLoader(), "tk.martijn_heil.nincore.res.messages", "subCmdDesc.help"))
                .setUsage("<sub command?>")
                .setExecutor(new HelpSubCmdExecutor())
                .setParentCommand(this)
                .construct();
    }


    @Override
    public void addDefaultInfoSubCmd()
    {
        // /command help. sub command.
        new SubCommandBuilder()
                .setName("info")
                .addAlias("i")
                .setUseStaticDescription(false)
                .setLocalizedDescription(new LocalizedString(NcCore.class.getClassLoader(), "tk.martijn_heil.nincore.res.messages", "subCmdDesc.info"))
                .setExecutor(new InfoSubCmdExecutor(this.plugin))
                .setParentCommand(this)
                .construct();
    }


    /**
     * Reloads the plugin this belongs to.
     */
    @Override
    public void addDefaultReloadSubCmd()
    {
        new SubCommandBuilder()
                .setName("reload")
                .setUseStaticDescription(false)
                .setLocalizedDescription(new LocalizedString(NcCore.class.getClassLoader(), "tk.martijn_heil.nincore.res.messages", "subCmdDesc.reload"))
                .setExecutor(new ReloadSubCmdExecutor())
                .setParentCommand(this)
                .construct();
    }
}
