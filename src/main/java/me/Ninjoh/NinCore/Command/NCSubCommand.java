package me.ninjoh.nincore.command;


import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.command.NinArgument;
import me.ninjoh.nincore.api.command.NinArgumentType;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.executors.SubCommandExecutor;
import me.ninjoh.nincore.command.handlers.NCNinSubCommandHandler;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class NCSubCommand implements NinSubCommand
{
    private String Name; // Required && Always lowercase.
    private List<String> aliases; // Optional
    private String[] description; // Optional
    private String requiredPermission; // Optional.
    private List<NinArgument> arguments;
    private SubCommandExecutor executor; // Required
    private NCNinSubCommandHandler handler;
    private NCCommand parentCommand;


    /**
     * Constructor
     *
     * @param name Name of the sub command.
     * @param aliases List of aliases, Can be null.
     * @param description description of the sub command, Can be null.
     * @param executor The SubCommandExecutor for this sub command.
     */
    public NCSubCommand(@NotNull String name, @Nullable List<String> aliases, @Nullable String[] description,
                        @Nullable String permission, @Nullable List<NinArgument> arguments,
                        @NotNull SubCommandExecutor executor, @NotNull NCCommand parentCommand)
    {
        Name = name.toLowerCase(); // NCSubCommand names are always lower case.
        this.description = description;
        requiredPermission = permission;
        this.arguments = arguments;
        this.executor = executor.init(this);
        this.parentCommand = parentCommand;
        this.handler = new NCNinSubCommandHandler(this);

        // Make all alias entries lowercase.
        if(aliases != null)
        {
            for(int i=0; i < aliases.size(); i++) {
                aliases.set(i, aliases.get(i).toLowerCase());
            }

            this.aliases = aliases;
        }
        else
        {
            this.aliases = new ArrayList<>();
        }

        if(this.arguments == null)
        {
            this.arguments = new ArrayList<>();
        }
    }


    /**
     * Get the name of this sub command.
     *
     * @return The name of this sub command.
     */
    @Override
    public String getName()
    {
        return Name;
    }


    /**
     * Get all aliases of this sub command.
     *
     * @return All aliases except the sub command name.
     */
    @Override
    public List<String> getAliases()
    {
        return new ArrayList<>(aliases);
    }


    /**
     * Get all aliases of this sub command.
     * Including the name which is always the main
     * alias as well.
     *
     * @return All aliases including the sub command name.
     */
    @Override
    public List<String> getAliasesWithMainSubCmd()
    {
        List<String> copyOfAliases = new ArrayList<>(aliases);
        copyOfAliases.add(0, Name);

        return copyOfAliases;
    }


    /**
     * Does this sub command have any aliases?
     *
     * @return True/False, Does this sub command have any aliases?
     */
    @Override
    public boolean hasAliases()
    {
        return !aliases.isEmpty();
    }


    /**
     * Does this sub command require a CommandSender
     * to have a certain permission?
     *
     * @return True/False, Does this sub command require a permission?
     */
    @Override
    public boolean requiresPermission()
    {
        return requiredPermission != null;
    }


    /**
     * Get the required permission for this sub command.
     *
     * @return The required permission for this sub command.
     */
    @Override
    public String getRequiredPermission()
    {
        return requiredPermission;
    }


    /**
     * Get this sub command's syntax.
     * ( Only the last part of the syntax, e.g;
     * "<player=you> <world>" )
     *
     * @return The usage string.
     */
    @Override
    public String getUsage()
    {
        if(this.hasArguments())
        {
            List<String> list = new ArrayList<>();

            //noinspection ConstantConditions
            for (NinArgument arg : this.getArguments())
            {
                if(arg.getArgumentType() == NinArgumentType.OPTIONAL)
                {
                    list.add(NCArgumentColor.OPTIONAL + "[(" +
                            StringUtils.capitalize(arg.getArgumentDataType().getHumanFriendlyName()) + ") " +
                            arg.getName() + "]");
                }
                else if (arg.getArgumentType() == NinArgumentType.REQUIRED)
                {
                    list.add(NCArgumentColor.REQUIRED + "<" +
                            StringUtils.capitalize(arg.getArgumentDataType().getHumanFriendlyName()) + ">");
                }
            }

            return StringUtils.join(list, " ");
        }
        else
        {
            return null;
        }
    }


    /**
     * Get this sub command's description.
     *
     * @param locale The locale to return the description in.
     * @return This sub command's description.
     */
    @Override
    public String getDescription(@NotNull Locale locale)
    {
        if(hasDescription())
        {
            ResourceBundle messages = ResourceBundle.getBundle(description[1], locale);

            return messages.getString(description[0]);
        }

        return null;
    }


    /**
     * Get this sub command's description.
     *
     * @return This sub command's description.
     */
    @Override
    public String getDescription()
    {
        if(hasDescription())
        {
            ResourceBundle messages = ResourceBundle.getBundle(description[1],
                    NinCore.getImplementation().getDefaultMinecraftLocale().toLocale());

            return messages.getString(description[0]);
        }

        return null;
    }


    /**
     * Check if this sub command has a description.
     *
     * @return True/False, does this sub command have a description?
     */
    @Override
    public boolean hasDescription()
    {
        return description != null;
    }


    /**
     * Check if this sub command has any arguments.
     *
     * @return True if it has any arguments, else false.
     */
    @Override
    public boolean hasArguments()
    {
        return this.arguments != null;
    }


    /**
     * Get this sub command's arguments.
     *
     * @return A list of all arguments for this sub command.
     */
    @Override
    public List<NinArgument> getArguments()
    {
        return this.arguments;
    }


    /**
     * Get an argument by index.
     *
     * @param i the index to query.
     * @return The argument found, can be null.
     */
    @Override
    public NinArgument getArgumentByIndex(int i)
    {
        if(!this.hasArguments())
        {
            return null;
        }

        //noinspection ConstantConditions
        if(this.getArguments().size() > i)
        {
            return this.getArguments().get(i);
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<NinArgument> getRequiredArguments()
    {
        if(this.hasArguments())
        {
            List<NinArgument> list = new ArrayList<>();

            //noinspection ConstantConditions
            for (NinArgument arg : this.getArguments())
            {
                if(arg.isRequired())
                {
                    list.add(arg);
                }
            }

            return list;
        }
        else
        {
            return null;
        }
    }


    @Override
    public boolean hasRequiredArguments()
    {
        if(this.hasArguments())
        {
            //noinspection ConstantConditions
            for (NinArgument arg : this.getArguments())
            {
                if(arg.isRequired())
                {
                    return true;
                }
            }
        }

        // We only get here if this sub command has no arguments
        // or does not have any required arguments
        return false;
    }


    /**
     * Get the executor for this sub command.
     *
     * @return The executor for this sub command.
     */
    public SubCommandExecutor getExecutor()
    {
        return executor;
    }


    @Override
    public NCCommand getParentCommand()
    {
        return this.parentCommand;
    }
}