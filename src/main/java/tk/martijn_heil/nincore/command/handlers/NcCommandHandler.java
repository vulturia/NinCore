package tk.martijn_heil.nincore.command.handlers;


import tk.martijn_heil.nincore.command.NcCommand;
import tk.martijn_heil.nincore.command.NcSubCommand;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import tk.martijn_heil.nincore.api.NinCore;
import tk.martijn_heil.nincore.api.command.NinSubCommand;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;
import tk.martijn_heil.nincore.api.exceptions.validationexceptions.AccessDeniedException;
import tk.martijn_heil.nincore.api.exceptions.validationexceptions.NotEnoughArgumentsException;
import tk.martijn_heil.nincore.api.util.MessageUtil;

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
            na.getTarget().sendCommandHelp(this.NcCommand);
        }
        catch (ValidationException ve)
        {
            ve.getTarget().sendError(ve.getPlayerMessage());

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
