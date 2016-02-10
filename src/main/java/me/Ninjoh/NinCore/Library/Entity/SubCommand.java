package me.Ninjoh.NinCore.Library.Entity;


import me.Ninjoh.NinCore.Library.Interfaces.SubCommandExecutor;
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
    @Nullable private String Usage; // Optional
    @Nullable private String[] Description; // Optional
    @Nullable private String RequiredPermission; // Optional.
    private SubCommandExecutor Executor; // Required


    /**
     * Constructor
     *
     * @param name Name of the sub command.
     * @param aliases List of aliases, Can be null.
     * @param usage Usage string, Can be null.
     * @param description Description of the sub command, Can be null.
     * @param executor The SubCommandExecutor for this sub command.
     */
    public SubCommand(@NotNull String name, @Nullable List<String> aliases, @Nullable String usage
            , @Nullable String[] description, @Nullable String permission, @NotNull SubCommandExecutor executor)
    {
        Name = name.toLowerCase(); // SubCommand names are always lower case.
        Usage = usage;
        Description = description;
        RequiredPermission = permission;
        Executor = executor;

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
        return Usage;
    }


    public boolean hasUsage()
    {
        return Usage != null;
    }


    /**
     * Get this sub command's description.
     *
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
     * Check if this sub command has a description.
     *
     * @return True/False, does this sub command have a description?
     */
    public boolean hasDescription()
    {
        return Description != null;
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
}
