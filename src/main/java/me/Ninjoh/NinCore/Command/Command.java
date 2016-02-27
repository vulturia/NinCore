package me.Ninjoh.NinCore.command;


import me.Ninjoh.NinCore.NinCore;
import me.Ninjoh.NinCore.command.handlers.NinCommandHandler;
import me.Ninjoh.NinCore.exceptions.SubCommandAliasAlreadyRegistered;
import me.Ninjoh.NinCore.exceptions.SubCommandAlreadyExistsException;
import me.Ninjoh.NinCore.interfaces.NinCommandExecutor;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Command
{
    private String Name; // Required
    @Nullable private String Description; // Automatic
    @Nullable private String Permission; // Automatic
    private List<String> Aliases; // Automatic
    @Nullable
    private List<SubCommand> SubCommands; // Optional
    private NinCommandExecutor Executor; // Required
    @Nullable
    private List<Argument> arguments;


    /**
     * Constructor
     *
     * @param name The command's name.
     * @param subCommands The sum commands for this command.
     */
    public Command(@NotNull String name, @Nullable  List<SubCommand> subCommands, @Nullable List<Argument> arguments, @NotNull NinCommandExecutor executor)
    {
        Name = name;
        Description = NinCore.getPlugin().getCommand(name).getDescription();
        Permission = NinCore.getPlugin().getCommand(name).getPermission();
        Aliases = NinCore.getPlugin().getCommand(name).getAliases();
        this.Executor = executor.init(this);


        if(subCommands != null)
        {
            SubCommands = subCommands;
        }

        if(arguments != null)
        {
            this.arguments = arguments;
        }


        if(Aliases == null)
        {
            Aliases = new ArrayList<>();
        }

        NinCore.getPlugin().getCommand(name).setExecutor(new NinCommandHandler(this));
    }


    /**
     * Set this command's CommandExecutor.
     *
     * @param executor The CommandExecutor for this command.
     */
    public void setExecutor(@NotNull NinCommandExecutor executor)
    {
        Executor = executor.init(this);
    }


    public NinCommandExecutor getExecutor()
    {
        return this.Executor;
    }


    /**
     * Get this command's name.
     *
     * @return This command's name.
     */
    @NotNull
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
     * e.g; "<\player=you> <\world>"
     *
     * @return This command's usage syntax. Can be null.
     */
    @Nullable
    public String getUsage()
    {
        List<String> list = new ArrayList<>();

        for (Argument arg : this.arguments)
        {
            if(arg.getArgumentType() == ArgumentType.OPTIONAL)
            {
                list.add(ArgumentColor.OPTIONAL + "[" + arg.getArgumentDataType().getHumanFriendlyName() + "]");
            }
            else if (arg.getArgumentType() == ArgumentType.REQUIRED)
            {
                list.add(ArgumentColor.REQUIRED + "<" + arg.getArgumentDataType().getHumanFriendlyName() + ">");
            }
        }

        return StringUtils.join(list, " ");
    }


    /**
     * Check if this command has a usage syntax set.
     *
     * @return True/False, does this command have a usage syntax set?
     */
    public boolean hasUsage()
    {
        return (this.arguments != null && !this.arguments.isEmpty());
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
    @NotNull
    public List<String> getAliases()
    {
        return new ArrayList<>(Aliases);
    }


    /**
     * Get this command's aliases, including the main command/alias
     *
     * @return This command's aliases, including the main command/alias.
     */
    @NotNull
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
    @NotNull
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
    public void addSubCommand(@NotNull SubCommand subCommand) throws SubCommandAlreadyExistsException, SubCommandAliasAlreadyRegistered
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
                //noinspection ConstantConditions
                for (String alias : subCommand.getAliases())
                {
                    //noinspection ConstantConditions
                    if(subCmd.getAliases().contains(alias))
                    {
                        throw new SubCommandAliasAlreadyRegistered(alias);
                    }
                }
            }
        }

        SubCommands.add(subCommand);
    }


    public void setSubCommands(List<SubCommand> subCommands)
    {
        this.SubCommands = subCommands;
    }


    /**
     * Delete a sub command from this command by name.
     *
     * @param name The name of the sub command to be deleted from this command.
     */
    public void deleteSubCommand(@NotNull String name)
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
    public SubCommand getSubCommand(@NotNull String name)
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
    public SubCommand getSubCommandByAlias(@NotNull String alias)
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
    public boolean subCommandExists(@NotNull String name)
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
    public boolean subCommandExistsByAlias(@NotNull String alias)
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