package me.ninjoh.nincore.command;


import me.ninjoh.nincore.command.handlers.NinSubCommandHandler;
import me.ninjoh.nincore.interfaces.SubCommandExecutor;
import me.ninjoh.nincore.util.LocaleUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class SubCommand
{
    private String Name; // Required && Always lowercase.
    @NotNull private List<String> Aliases; // Optional
    @Nullable private String[] Description; // Optional
    @Nullable private String RequiredPermission; // Optional.
    @Nullable
    private List<Argument> arguments;
    private SubCommandExecutor Executor; // Required
    private NinSubCommandHandler handler;
    private Command parentCommand;


    /**
     * Constructor
     *
     * @param name Name of the sub command.
     * @param aliases List of aliases, Can be null.
     * @param description Description of the sub command, Can be null.
     * @param executor The SubCommandExecutor for this sub command.
     */
    public SubCommand(@NotNull String name, @Nullable List<String> aliases, @Nullable String[] description,
                      @Nullable String permission, @Nullable List<Argument> arguments,
                      @NotNull SubCommandExecutor executor, @NotNull Command parentCommand)
    {
        Name = name.toLowerCase(); // SubCommand names are always lower case.
        Description = description;
        RequiredPermission = permission;
        this.arguments = arguments;
        Executor = executor.init(this);
        this.parentCommand = parentCommand;
        this.handler = new NinSubCommandHandler(this);

        // Make all alias entries lowercase.
        if(aliases != null)
        {
            for(int i=0; i < aliases.size(); i++) {
                aliases.set(i, aliases.get(i).toLowerCase());
            }

            Aliases = aliases;
        }
        else
        {
            Aliases = new ArrayList<>();
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
    @NotNull
    public String getName()
    {
        return Name;
    }


    /**
     * Get all aliases of this sub command.
     *
     * @return All aliases except the sub command name.
     */
    @Nullable
    public List<String> getAliases()
    {
        return new ArrayList<>(Aliases);
    }


    /**
     * Get all aliases of this sub command.
     * Including the name which is always the main
     * alias as well.
     *
     * @return All aliases including the sub command name.
     */
    @NotNull
    public List<String> getAliasesWithMainSubCmd()
    {
        List<String> copyOfAliases = new ArrayList<>(Aliases);
        copyOfAliases.add(0, Name);

        return copyOfAliases;
    }


    /**
     * Does this sub command have any aliases?
     *
     * @return True/False, Does this sub command have any aliases?
     */
    public boolean hasAliases()
    {
        return !Aliases.isEmpty();
    }


    /**
     * Does this sub command require a CommandSender
     * to have a certain permission?
     *
     * @return True/False, Does this sub command require a permission?
     */
    public boolean requiresPermission()
    {
        return RequiredPermission != null;
    }


    /**
     * Get the required permission for this sub command.
     *
     * @return The required permission for this sub command.
     */
    @Nullable
    public String getRequiredPermission()
    {
        return RequiredPermission;
    }


    /**
     * Get this sub command's syntax.
     * ( Only the last part of the syntax, e.g;
     * "<player=you> <world>" )
     *
     * @return The usage string.
     */
    @Nullable
    public String getUsage()
    {
        if(this.hasArguments())
        {
            List<String> list = new ArrayList<>();

            for (Argument arg : this.arguments)
            {
                if(arg.getArgumentType() == ArgumentType.OPTIONAL)
                {
                    list.add(ArgumentColor.OPTIONAL + "[(" +
                            StringUtils.capitalize(arg.getArgumentDataType().getHumanFriendlyName()) + ") " +
                            arg.getName() + "]");
                }
                else if (arg.getArgumentType() == ArgumentType.REQUIRED)
                {
                    list.add(ArgumentColor.REQUIRED + "<" +
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
    @Nullable
    public String getDescription(@NotNull Locale locale)
    {
        if(hasDescription())
        {
            ResourceBundle messages = ResourceBundle.getBundle(Description[1], locale);

            return messages.getString(Description[0]);
        }

        return null;
    }


    /**
     * Get this sub command's description.
     *
     * @return This sub command's description.
     */
    @Nullable
    public String getDescription()
    {
        if(hasDescription())
        {
            ResourceBundle messages = ResourceBundle.getBundle(Description[1], LocaleUtils.getDefaultMinecraftLocale().toLocale());

            return messages.getString(Description[0]);
        }

        return null;
    }


    /**
     * Check if this sub command has a description.
     *
     * @return True/False, does this sub command have a description?
     */
    public boolean hasDescription()
    {
        return Description != null;
    }


    /**
     * Check if this sub command has any arguments.
     *
     * @return True if it has any arguments, else false.
     */
    public boolean hasArguments()
    {
        return this.arguments != null;
    }


    /**
     * Get this sub command's arguments.
     *
     * @return A list of all arguments for this sub command.
     */
    @Nullable
    public List<Argument> getArguments()
    {
        return this.arguments;
    }


    /**
     * Get an argument by index.
     *
     * @param i the index to query.
     * @return The argument found, can be null.
     */
    @Nullable
    public Argument getArgumentByIndex(int i)
    {
        if(this.arguments.size() >= i)
        {
            return this.arguments.get(i);
        }
        else
        {
            return null;
        }
    }


    /**
     * Get the executor for this sub command.
     *
     * @return The executor for this sub command.
     */
    @NotNull
    public SubCommandExecutor getExecutor()
    {
        return Executor;
    }


    public Command getParentCommand()
    {
        return this.parentCommand;
    }
}