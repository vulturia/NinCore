package me.Ninjoh.NinCore.command.handlers;


import me.Ninjoh.NinCore.command.Command;
import me.Ninjoh.NinCore.command.SubCommand;
import me.Ninjoh.NinCore.exceptions.AccessDeniedException;
import me.Ninjoh.NinCore.util.MessageUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NinCommandHandler implements CommandExecutor
{
    private Command command;

    public NinCommandHandler(Command command)
    {
        this.command = command;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, String label, @NotNull String[] args)
    {
        if(cmd.getName().equalsIgnoreCase(command.getName()))
        {
            // Check if the sender has the required permission for this command.
            if(this.command.requiresPermission() && !sender.hasPermission(this.command.getRequiredPermission()))
            {
                try
                {
                    throw new AccessDeniedException(sender);
                }
                catch(AccessDeniedException e)
                {
                    //
                }
                return true;
            }

            if(this.command.hasSubCommands())
            {
                if(args.length < 1)
                {
                    // Sub command not specified, send command help.
                    MessageUtil.sendCommandHelp(sender, this.command);
                    return true;
                }


                SubCommand subCmd = this.command.getSubCommandByAlias(args[0].toLowerCase());

                if(subCmd != null)
                {
                    if(subCmd.requiresPermission() && !sender.hasPermission(subCmd.getRequiredPermission()))
                    {
                        try
                        {
                            throw new AccessDeniedException(sender);
                        }
                        catch(AccessDeniedException e)
                        {
                            //
                        }

                        return true;
                    }

                    // The first argument is the sub command, so remove that one
                    List<String> list = new ArrayList<>();

                    int count = 0;

                    for (String arg : args)
                    {
                        if(count > 0)
                        {
                            list.add(arg);
                        }

                        count++;
                    }

                    // Generate new args without the first argument, wich would be the sub command.
                    String[] newArgs = list.toArray(new String[list.size()]);



                    // Handle sub command.
                    new NinSubCommandHandler(subCmd);
                    //subCmd.getExecutor().Handle(sender, newArgs, this, subCmd);
                }
                else
                {
                    MessageUtil.sendCommandHelp(sender, this.command);
                }
            }
            else
            {
                this.command.getExecutor().execute(sender, cmd, label, args);
            }
        }
        return true;
    }
}
