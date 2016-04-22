package tk.martijn_heil.nincore.command.handlers;


import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import tk.martijn_heil.nincore.api.NinCore;
import tk.martijn_heil.nincore.api.command.NinSubCommand;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;
import tk.martijn_heil.nincore.api.exceptions.validationexceptions.NotEnoughArgumentsException;
import tk.martijn_heil.nincore.api.util.MessageUtil;

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
            NinCore.get().getEntityManager().getNinCommandSender(na.getTarget()).sendCommandHelp(this.subCommand);
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
