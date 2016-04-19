package me.ninjoh.nincore.command.handlers;


import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.exceptions.TechnicalException;
import me.ninjoh.nincore.api.exceptions.ValidationException;
import me.ninjoh.nincore.api.exceptions.validationexceptions.NotEnoughArgumentsException;
import me.ninjoh.nincore.api.util.MessageUtil;
import org.bukkit.command.CommandSender;

public class NcSubCommandHandler
{
    private NinSubCommand subCommand;


    public NcSubCommandHandler(NinSubCommand subCommand)
    {
        this.subCommand = subCommand;
    }


    public void handle(@NotNull CommandSender sender, String[] args)
    {
        try
        {
            // Just execute it for now.
            this.subCommand.getExecutor().execute(sender, args);
        }
        catch (NotEnoughArgumentsException na)
        {
            NinCore.get().getNinCommandSender(na.getTarget()).sendCommandHelp(this.subCommand);
        }
        catch (ValidationException ve)
        {
            MessageUtil.sendError(ve.getTarget(), ve.getPlayerMessage());
        }
        catch (TechnicalException te)
        {
            if (te.getLogLevel() == null)
            {
                NinCore.getImplementingPlugin().getLogger().warning(te.getMessage());
            }
            else
            {
                NinCore.getImplementingPlugin().getLogger().log(te.getLogLevel(), te.getMessage());
            }
        }
    }
}
