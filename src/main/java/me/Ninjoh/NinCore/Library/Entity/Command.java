package me.Ninjoh.NinCore.Library.Entity;


import me.Ninjoh.NinCore.Library.Exceptions.SubCommandAliasAlreadyRegistered;
import me.Ninjoh.NinCore.Library.Exceptions.SubCommandAlreadyExistsException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Command
{
    private String Name; // Required
    @Nullable private String Description; // Automatic
    @Nullable private String Usage; // Automatic
    @Nullable private String Permission; // Automatic
    private List<String> Aliases = new ArrayList<>(); // Automatic
    private List<SubCommand> SubCommands = new ArrayList<>(); // Optional
    private CommandExecutor Executor; // Required

    private JavaPlugin Plugin;


    /**
     * Constructor
     *
     * @param name The command's name.
     * @param subCommands The sum commands for this command.
     * @param Plugin The JavaPlugin this command belongs to.
     */
    public Command(String name, List<SubCommand> subCommands, JavaPlugin Plugin)
    {
        Name = name;
        Description = Plugin.getCommand(name).getDescription();
        Permission = Plugin.getCommand(name).getPermission();
        Aliases = Plugin.getCommand(name).getAliases();
        SubCommands = subCommands;

        this.Plugin = Plugin;
    }


    /**
     * Constructor
     *
     * @param name The command's name.
     * @param Plugin The JavaPlugin this command belongs to.
     */
    public Command(String name, JavaPlugin Plugin)
    {
        Name = name;
        Description = Plugin.getCommand(name).getDescription();
        Aliases = Plugin.getCommand(name).getAliases();
        Usage = Plugin.getCommand(name).getUsage();
    }


    /**
     * Set this command's CommandExecutor.
     *
     * @param executor The CommandExecutor for this command.
     */
    public void setExecutor(CommandExecutor executor)
    {
        Plugin.getCommand(Name).setExecutor(executor);
        Executor = executor;
    }


    /**
     * Get this command's CommandExecutor.
     *
     * @return this command's CommandExecutor.
     */
    public CommandExecutor getCommandExecutor()
    {
        return Executor;
    }


    /**
     * Get this command's name.
     *
     * @return This command's name.
     */
    public String getName()
    {
        return Name;
    }


    /**
     * Get this command's description.
     *
     * @return Get this command's description. Can be null.
     */
    @Nullable
    public String getDescription()
    {
        return Description;
    }


    /**
     * Check if this command has a description.
     *
     * @return True/False, does this command have a description set?
     */
    public boolean hasDescription()
    {
        return Description != null;
    }


    /**
     * Get this command's usage syntax.
     * NOTE: This excludes the command itself
     * e.g; "<player=you> <world>"
     *
     * @return This command's usage syntax. Can be null.
     */
    @Nullable
    public String getUsage()
    {
        return Usage;
    }


    /**
     * Check if this command has a usage syntax set.
     *
     * @return True/False, does this command have a usage syntax set?
     */
    public boolean hasUsage()
    {
        return Usage != null;
    }


    /**
     * Check if this command requires the CommandSender to have a permission.
     *
     * @return True/False, does this command require the CommandSender to have a certain permission?
     */
    public boolean requiresPermission()
    {
        return Permission != null;
    }


    /**
     * Get this command's required permission.
     *
     * @return This command's required permission.
     */
    @Nullable
    public String getRequiredPermission()
    {
        return Permission;
    }


    /**
     * Get this command's aliases.
     * NOTE: This does not include the main command alias/name.
     *
     * @return This command's aliases. Can be an empty ArrayList.
     */
    public List<String> getAliases()
    {
        return new ArrayList<>(Aliases);
    }


    /**
     * Get this command's aliases, including the main command/alias
     *
     * @return This command's aliases, including the main command/alias.
     */
    public List<String> getAliasesWithMainCmd()
    {
        List<String> copyOfAliases = new ArrayList<>(Aliases);
        copyOfAliases.add(0, Name.toLowerCase());

        return copyOfAliases;
    }


    /**
     * Get this command's sub commands.
     *
     * @return This command's sub commands. Can be an empty ArrayList.
     */
    public List<SubCommand> getSubCommands()
    {
        return new ArrayList<>(SubCommands);
    }


    /**
     * Add a sub command to this command.
     *
     * @param subCommand The SubCommand to add.
     * @throws SubCommandAlreadyExistsException
     * @throws SubCommandAliasAlreadyRegistered
     */
    public void addSubCommand(SubCommand subCommand) throws SubCommandAlreadyExistsException, SubCommandAliasAlreadyRegistered
    {
        if(subCommandExists(subCommand.getName()))
        {
            throw new SubCommandAlreadyExistsException();
        }

        // Check if the given sub command aliases aren't already registered.
        for (SubCommand subCmd : SubCommands)
        {
            if(subCommand.hasAliases() && subCmd.hasAliases()) // NPE checks.
            {
                for (String alias : subCommand.getAliases())
                {
                    if(subCmd.getAliases().contains(alias))
                    {
                        throw new SubCommandAliasAlreadyRegistered(alias);
                    }
                }
            }
        }

        SubCommands.add(subCommand);
    }


    /**
     * Delete a sub command from this command by name.
     *
     * @param name The name of the sub command to be deleted from this command.
     */
    public void deleteSubCommand(String name)
    {
        // If any sub commands exist for this command
        if (!SubCommands.isEmpty())
        {
            // Loop over all sub commands
            for (SubCommand subCommand : SubCommands)
            {
                // If this is the sub command queried for, return true.
                if (subCommand.getName().equals(name.toLowerCase()))
                {
                    SubCommands.remove(subCommand);
                }
            }
        }
    }


    /**
     * Get a sub command by name.
     *
     * @param name The name of the sub command to get.
     * @return The SubCommand queried for. Can be null.
     */
    @Nullable
    public SubCommand getSubCommand(String name)
    {
        // If any sub commands exist for this command
        if (!SubCommands.isEmpty())
        {
            // Loop over all sub commands
            for (SubCommand subCommand : SubCommands)
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
     * @return The SubCommand queried for. Can be null.
     */
    @Nullable
    public SubCommand getSubCommandByAlias(String alias)
    {
        for (SubCommand subCmd : SubCommands)
        {
            if(subCmd.getAliasesWithMainSubCmd().contains(alias.toLowerCase()))
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
    public boolean hasSubCommands()
    {
        return !SubCommands.isEmpty();
    }


    /**
     * Check if a sub command exists by name.
     *
     * @param name The name to query for.
     * @return True/False, does this sub command exist?
     */
    public boolean subCommandExists(String name)
    {
        // If any sub commands exist for this command
        if (!SubCommands.isEmpty())
        {
            // Loop over all sub commands
            for (SubCommand subCommand : SubCommands)
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
    public boolean subCommandExistsByAlias(String alias)
    {
        for (SubCommand subCmd : SubCommands)
        {
            if(subCmd.getAliasesWithMainSubCmd().contains(alias.toLowerCase()))
            {
                return true;
            }
        }

        return false;
    }
}
