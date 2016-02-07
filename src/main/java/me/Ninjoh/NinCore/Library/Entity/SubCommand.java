package me.Ninjoh.NinCore.Library.Entity;


import com.sun.istack.internal.Nullable;
import me.Ninjoh.NinCore.Library.Interfaces.SubCommandExecutor;

import java.util.ArrayList;
import java.util.List;

public class SubCommand
{
    private String Name; // Required
    private List<String> Aliases; // Optional
    private String Usage; // Optional
    private String Description; // Optional
    private String RequiredPermission; // Optional.
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
    public SubCommand(String name, @Nullable List<String> aliases, @Nullable String usage
            , @Nullable String description, @Nullable String permission, SubCommandExecutor executor)
    {
        Name = name.toLowerCase(); // SubCommand names are always lower case.
        Aliases = aliases;
        Usage = usage;
        Description = description;
        RequiredPermission = permission;
        Executor = executor;
    }


    public String getName()
    {
        return Name;
    }


    public List<String> getAliases()
    {
        return new ArrayList<>(Aliases);
    }


    public List<String> getAliasesWithMainSubCmd()
    {
        if(Aliases == null || Aliases.isEmpty())
        {
            List<String> aliases = new ArrayList<>();
            aliases.add(0, Name);

            return aliases;
        }
        else
        {
            List<String> copyOfAliases = new ArrayList<>(Aliases);
            copyOfAliases.add(0, Name);

            return copyOfAliases;
        }
    }


    public boolean hasAliases()
    {
        return !Aliases.isEmpty();
    }


    public boolean requiresPermission()
    {
        return RequiredPermission != null;
    }


    public String getRequiredPermission()
    {
        return RequiredPermission;
    }


    public String getUsage()
    {
        return Usage;
    }


    public String getDescription()
    {
        return Description;
    }


    public boolean hasDescription()
    {
        return Description != null;
    }


    public SubCommandExecutor getExecutor()
    {
        return Executor;
    }
}
