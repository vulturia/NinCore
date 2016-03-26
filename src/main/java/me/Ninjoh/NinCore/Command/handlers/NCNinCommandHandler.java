package me.ninjoh.nincore.command.handlers;


import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.exceptions.TechnicalException;
import me.ninjoh.nincore.api.exceptions.ValidationException;
import me.ninjoh.nincore.api.exceptions.validationexceptions.AccessDeniedException;
import me.ninjoh.nincore.api.util.MessageUtil;
import me.ninjoh.nincore.command.NCCommand;
import me.ninjoh.nincore.command.NCSubCommand;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class NCNinCommandHandler implements CommandExecutor
{
    private NCCommand NCCommand;


    public NCNinCommandHandler(NCCommand NCCommand)
    {
        this.NCCommand = NCCommand;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, String label, @NotNull String[] args)
    {
        try
        {
            if (cmd.getName().equalsIgnoreCase(NCCommand.getName()))
            {
                // Check if the sender has the required permission for this NCCommand.
                if (this.NCCommand.requiresPermission() && !sender.hasPermission(this.NCCommand.getRequiredPermission()))
                {
                    throw new AccessDeniedException(sender);
                }

                if (this.NCCommand.hasSubCommands())
                {
                    if (args.length < 1)
                    {
                        // Sub NCCommand not specified, send NCCommand help.
                        MessageUtil.sendCommandHelp(sender, this.NCCommand);
                        return true;
                    }


                    NinSubCommand subCmd = this.NCCommand.getSubCommandByAlias(args[0].toLowerCase());

                    if (subCmd != null)
                    {
                        if (subCmd.requiresPermission() && !sender.hasPermission(subCmd.getRequiredPermission()))
                        {

                            throw new AccessDeniedException(sender);
                        }

                        // The first argument is the sub NCCommand, so remove that one
                        List<String> list = new ArrayList<>();

                        int count = 0;

                        for (String arg : args)
                        {
                            if (count > 0)
                            {
                                list.add(arg);
                            }

                            count++;
                        }

                        // Generate new args without the first argument, wich would be the sub NCCommand.
                        String[] newArgs = list.toArray(new String[list.size()]);


                        // Handle sub NCCommand.
                        ((NCSubCommand) subCmd).getHandler().handle(sender, newArgs);
                    }
                    else
                    {
                        MessageUtil.sendCommandHelp(sender, this.NCCommand);
                    }
                }
                else
                {
                    this.NCCommand.getExecutor().execute(sender, args);
                }
            }
        }
        catch (ValidationException ve)
        {
            MessageUtil.sendError(ve.getTarget(), ve.getPlayerMessage());

            if (ve.getLogMessage() != null) // Log the log message if it isn't null, with a very fine log level.
            {
                NinCore.getImplementingPlugin().getLogger().finer(ve.getLogMessage());
                NinCore.getImplementingPlugin().getLogger().finest(ExceptionUtils.getFullStackTrace(ve));
            }
        }
        catch (TechnicalException te)
        {
            // Log the exception to console.
            if ((te.getLogLevel() == null) && (te.getMessage() != null))
            {
                NinCore.getImplementingPlugin().getLogger().warning(te.getMessage());
                te.printStackTrace();
            }
            else if ((te.getLogLevel() != null) && (te.getMessage() != null))
            {
                NinCore.getImplementingPlugin().getLogger().log(te.getLogLevel(), te.getMessage());
            }
            else
            {
                NinCore.getImplementation().getImplementingPlugin().getLogger().warning("An unknown " +
                        te.getClass().getName() + " occured; \n" + ExceptionUtils.getFullStackTrace(te));
            }
        }

        return true; // CommandExecutor must return true to prevent bukkit just sending back command usage to the player.
    }
}
