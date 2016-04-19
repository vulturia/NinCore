package me.ninjoh.nincore.command.handlers;


import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.exceptions.TechnicalException;
import me.ninjoh.nincore.api.exceptions.ValidationException;
import me.ninjoh.nincore.api.exceptions.validationexceptions.AccessDeniedException;
import me.ninjoh.nincore.api.exceptions.validationexceptions.NotEnoughArgumentsException;
import me.ninjoh.nincore.api.util.MessageUtil;
import me.ninjoh.nincore.command.NcCommand;
import me.ninjoh.nincore.command.NcSubCommand;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class NcCommandHandler implements CommandExecutor
{
    private NcCommand NcCommand;


    public NcCommandHandler(NcCommand NcCommand)
    {
        this.NcCommand = NcCommand;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, String label, @NotNull String[] args)
    {
        try
        {
            if (cmd.getName().equalsIgnoreCase(NcCommand.getName()))
            {
                // Check if the sender has the required permission for this NcCommand.
                if (this.NcCommand.requiresPermission() && !sender.hasPermission(this.NcCommand.getRequiredPermission()))
                {
                    throw new AccessDeniedException(sender);
                }

                if (this.NcCommand.hasSubCommands())
                {
                    if (args.length < 1)
                    {
                        // Sub NcCommand not specified, send NcCommand help.
                        MessageUtil.sendCommandHelp(sender, this.NcCommand);
                        return true;
                    }


                    NinSubCommand subCmd = this.NcCommand.getSubCommandByAlias(args[0].toLowerCase());

                    if (subCmd != null)
                    {
                        if (subCmd.requiresPermission() && !sender.hasPermission(subCmd.getRequiredPermission()))
                        {

                            throw new AccessDeniedException(sender);
                        }

                        // The first argument is the sub NcCommand, so remove that one
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

                        // Generate new args without the first argument, wich would be the sub NcCommand.
                        String[] newArgs = list.toArray(new String[list.size()]);


                        // Handle sub NcCommand.
                        ((NcSubCommand) subCmd).getHandler().handle(sender, newArgs);
                    }
                    else
                    {
                        MessageUtil.sendCommandHelp(sender, this.NcCommand);
                    }
                }
                else
                {
                    this.NcCommand.getExecutor().execute(sender, args);
                }
            }
        }
        catch (NotEnoughArgumentsException na)
        {
            NinCore.get().getEntityManager().getNinCommandSender(na.getTarget()).sendCommandHelp(this.NcCommand);
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
