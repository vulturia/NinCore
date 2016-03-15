package me.ninjoh.nincore.command;


import me.ninjoh.nincore.api.MinecraftLocale;
import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.builders.SubCommandBuilder;
import me.ninjoh.nincore.api.command.executors.NinCommandExecutor;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.Nullable;
import me.ninjoh.nincore.api.exceptions.technicalexceptions.SubCommandAlreadyExistsException;
import me.ninjoh.nincore.api.exceptions.technicalexceptions.SubCommandAliasAlreadyRegisteredException;
import me.ninjoh.nincore.api.util.TranslationUtils;
import me.ninjoh.nincore.command.handlers.NCNinCommandHandler;
import me.ninjoh.nincore.command.handlers.NCNinCommandHelpHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class NCCommand implements NinCommand
{
    private String name; // Required
    private String descriptionKey; // Required
    private String descriptionBundleBaseName;
    private String permission; // Automatic
    private List<String> aliases; // Automatic
    private List<NinSubCommand> subCommands; // Optional
    private NinCommandExecutor executor; // Required
    private String usage;



    public NCCommand(@NotNull String name, @Nullable String descriptionKey, @Nullable  String descriptionBundleBaseName, @Nullable  List<NinSubCommand> subCommands, @NotNull NinCommandExecutor executor, JavaPlugin plugin)
    {
        this.name = name;
        this.descriptionKey = descriptionKey;
        this.descriptionBundleBaseName = descriptionBundleBaseName;
        permission = plugin.getCommand(name).getPermission();
        aliases = plugin.getCommand(name).getAliases();
        this.usage = plugin.getCommand(name).getUsage();
        this.executor = executor.init(this);


        if(subCommands != null)
        {
            this.subCommands = subCommands;
        }
        else
        {
            this.subCommands = new ArrayList<>();
        }


        if(aliases == null)
        {
            aliases = new ArrayList<>();
        }

        plugin.getCommand(name).setExecutor(new NCNinCommandHandler(this));
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
     * Get this command's name.
     *
     * @return This command's name.
     */
    @Override
    public String getName()
    {
        return name;
    }


    /**
     * Get this command's description.
     *
     * @return Get this command's description. Can be null.
     */
    @Override
    public String getDescription()
    {
        return TranslationUtils.getStaticMsg(ResourceBundle.getBundle(this.descriptionBundleBaseName, MinecraftLocale.getDefault().toLocale()), this.descriptionKey);
    }


    @Override
    public String getDescription(Locale inLocale)
    {
        return TranslationUtils.getStaticMsg(ResourceBundle.getBundle(this.descriptionBundleBaseName, inLocale), this.descriptionKey);
    }


    /**
     * Check if this command has a description.
     *
     * @return True/False, does this command have a description set?
     */
    @Override
    public boolean hasDescription()
    {
        return ((descriptionKey != null) && (descriptionBundleBaseName != null));
    }


    /**
     * Get this command's usage syntax.
     * NOTE: This excludes the command itself
     * e.g; (player=you) (world)
     *
     * @return This command's usage syntax. Can be null.
     */
    @Override
    public String getUsage()
    {
        return this.usage;
    }


    @Override
    public void setUsage(String value)
    {
        this.usage = value;
    }


    /**
     * Check if this command requires the CommandSender to have a permission.
     *
     * @return True/False, does this command require the CommandSender to have a certain permission?
     */
    @Override
    public boolean requiresPermission()
    {
        return permission != null;
    }


    /**
     * Get this command's required permission.
     *
     * @return This command's required permission.
     */
    @Override
    public String getRequiredPermission()
    {
        return permission;
    }


    /**
     * Get this command's aliases.
     * NOTE: This does not include the main command alias/name.
     *
     * @return This command's aliases. Can be an empty ArrayList.
     */
    @Override
    public List<String> getAliases()
    {
        return new ArrayList<>(aliases);
    }


    /**
     * Get this command's aliases, including the main command/alias
     *
     * @return This command's aliases, including the main command/alias.
     */
    @Override
    public List<String> getAliasesWithMainCmd()
    {
        List<String> copyOfAliases = new ArrayList<>(aliases);
        copyOfAliases.add(0, name.toLowerCase());

        return copyOfAliases;
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
     * @throws SubCommandAlreadyExistsException If the sub command is already exists.
     * @throws SubCommandAliasAlreadyRegisteredException If the provided sub command has an alias which is already in use.
     */
    @Override
    public void addSubCommand(NinSubCommand subCommand) throws SubCommandAlreadyExistsException, SubCommandAliasAlreadyRegisteredException
    {
        if(subCommandExists(subCommand.getName()))
        {
            throw new SubCommandAlreadyExistsException();
        }

        // Check if the given sub command aliases aren't already registered.
        for (NinSubCommand subCmd : this.getSubCommands())
        {
            if(subCmd.hasAliases() && subCmd.hasAliases()) // NPE checks.
            {
                //noinspection ConstantConditions
                for (String alias : subCmd.getAliases())
                {
                    //noinspection ConstantConditions
                    if(subCmd.getAliases().contains(alias))
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
            // Loop over all sub commands
            for (NinSubCommand subCommand : subCommands)
            {
                // If this is the sub command queried for, return true.
                if (subCommand.getName().equals(name.toLowerCase()))
                {
                    subCommands.remove(subCommand);
                }
            }
        }
    }


    /**
     * Get a sub command by name.
     *
     * @param name The name of the sub command to get.
     * @return The NCSubCommand queried for. Can be null.
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
     * @return The NCSubCommand queried for. Can be null.
     */
    @Override
    public NinSubCommand getSubCommandByAlias(String alias)
    {
        for (NinSubCommand subCmd : subCommands)
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
            if(subCmd.getAliasesWithMainSubCmd().contains(alias.toLowerCase()))
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
        SubCommandBuilder subCmd_list_builder = new SubCommandBuilder();
        subCmd_list_builder.setName("help");
        subCmd_list_builder.addAlias("?");
        subCmd_list_builder.setDescriptionKey("subCmdDesc.help");
        subCmd_list_builder.setUsage("<sub command?>");
        subCmd_list_builder.setExecutor(new NCNinCommandHelpHandler());
        subCmd_list_builder.setParentCommand(this);

        try
        {
            this.addSubCommand(subCmd_list_builder.construct());
        }
        catch (SubCommandAlreadyExistsException | SubCommandAliasAlreadyRegisteredException e)
        {
            NinCore.getImplementingPlugin().getLogger().warning("Could not add default help sub command to command with name: '" +
                    this.getName() + "' due to an exception: '" + e.getClass().getName() + "'");
        }
    }
}
