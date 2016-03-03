package me.ninjoh.nincore.command.handlers;


import me.ninjoh.nincore.command.NCCommand;
import me.ninjoh.nincore.command.NCSubCommand;
import me.ninjoh.nincore.exceptions.NCAccessDeniedException;
import me.ninjoh.nincore.util.NCMessageUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NCNinCommandHandler implements CommandExecutor
{
    private NCCommand NCCommand;

    public NCNinCommandHandler(NCCommand NCCommand)
    {
        this.NCCommand = NCCommand;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, String label, @NotNull String[] args)
    {
        if(cmd.getName().equalsIgnoreCase(NCCommand.getName()))
        {
            // Check if the sender has the required permission for this NCCommand.
            if(this.NCCommand.requiresPermission() && !sender.hasPermission(this.NCCommand.getRequiredPermission()))
            {
                try
                {
                    throw new NCAccessDeniedException(sender);
                }
                catch(NCAccessDeniedException e)
                {
                    //
                }
                return true;
            }

            if(this.NCCommand.hasSubCommands())
            {
                if(args.length < 1)
                {
                    // Sub NCCommand not specified, send NCCommand help.
                    NCMessageUtil.sendCommandHelp(sender, this.NCCommand);
                    return true;
                }


                NCSubCommand subCmd = this.NCCommand.getSubCommandByAlias(args[0].toLowerCase());

                if(subCmd != null)
                {
                    if(subCmd.requiresPermission() && !sender.hasPermission(subCmd.getRequiredPermission()))
                    {
                        try
                        {
                            throw new NCAccessDeniedException(sender);
                        }
                        catch(NCAccessDeniedException e)
                        {
                            //
                        }

                        return true;
                    }

                    // The first argument is the sub NCCommand, so remove that one
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

                    // Generate new args without the first argument, wich would be the sub NCCommand.
                    String[] newArgs = list.toArray(new String[list.size()]);



                    // Handle sub NCCommand.
                    new NCNinSubCommandHandler(subCmd).handle(sender, newArgs, cmd, subCmd);
                    //subCmd.getExecutor().Handle(sender, newArgs, this, subCmd);
                }
                else
                {
                    NCMessageUtil.sendCommandHelp(sender, this.NCCommand);
                }
            }
            else
            {
                this.NCCommand.getExecutor().execute(sender, cmd, label, args);
            }
        }
        return true;
    }
}
