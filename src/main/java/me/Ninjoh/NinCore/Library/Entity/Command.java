package me.Ninjoh.NinCore.Library.Entity;


import me.Ninjoh.NinCore.Library.Exceptions.SubCommandAlreadyExistsException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Command
{
    private String Name; // Required
    private String Description; // Automatic
    private String Usage; // Automatic
    private String Permission; // Automatic
    private List<String> Aliases = new ArrayList<>(); // Automatic
    private List<SubCommand> SubCommands = new ArrayList<>(); // Optional
    private CommandExecutor Executor; // Required

    private JavaPlugin Plugin;


    public Command(String name, List<SubCommand> subCommands, JavaPlugin Plugin)
    {
        Name = name;
        Description = Plugin.getCommand(name).getDescription();
        Permission = Plugin.getCommand(name).getPermission();
        Aliases = Plugin.getCommand(name).getAliases();
        SubCommands = subCommands;

        this.Plugin = Plugin;
    }


    public Command(String name, JavaPlugin Plugin)
    {
        Name = name;
        Description = Plugin.getCommand(name).getDescription();
        Aliases = Plugin.getCommand(name).getAliases();
        Usage = Plugin.getCommand(name).getUsage();
    }


    public void setExecutor(CommandExecutor executor)
    {
        Plugin.getCommand(Name).setExecutor(executor);
        Executor = executor;
    }


    public CommandExecutor getCommandExecutor()
    {
        return Executor;
    }


    public String getName()
    {
        return Name;
    }


    public String getDescription()
    {
        return Description;
    }


    public boolean hasDescription()
    {
        return Description != null;
    }


    public String getUsage()
    {
        return Usage;
    }


    public boolean hasUsage()
    {
        return Usage != null;
    }


    public boolean requiresPermission()
    {
        return Permission != null;
    }


    public String getRequiredPermission()
    {
        return Permission;
    }


    public List<String> getAliases()
    {
        return new ArrayList<>(Aliases);
    }


    public List<String> getAliasesWithMainCmd()
    {
        List<String> copyOfAliases = new ArrayList<>(Aliases);
        copyOfAliases.add(0, Name.toLowerCase());

        return copyOfAliases;
    }


    public List<SubCommand> getSubCommands()
    {
        return SubCommands;
    }


    public void addSubCommand(SubCommand subCommand) throws SubCommandAlreadyExistsException
    {
        if(subCommandExists(subCommand.getName()))
        {
            throw new SubCommandAlreadyExistsException();
        }

        SubCommands.add(subCommand);
    }


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


    public boolean hasSubCommands()
    {
        return !SubCommands.isEmpty();
    }


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
}
