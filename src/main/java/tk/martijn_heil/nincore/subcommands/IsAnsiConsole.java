package tk.martijn_heil.nincore.subcommands;

import tk.martijn_heil.nincore.NcCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import tk.martijn_heil.nincore.api.command.executors.NinSubCommandExecutor;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;


public class IsAnsiConsole extends NinSubCommandExecutor
{

    @Override
    public void execute(CommandSender sender, String[] args) throws ValidationException, TechnicalException
    {
        if(NcCore.getInstance().isConsoleAnsiSupported())
        {
            sender.sendMessage(ChatColor.GREEN + "The current console is ANSI supported.");
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "The current console is not ANSI supported.");
        }
    }
}
