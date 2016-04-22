package tk.martijn_heil.nincore.subcommands;


import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import tk.martijn_heil.nincore.api.command.executors.NinSubCommandExecutor;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;

public class GetJavaVersion extends NinSubCommandExecutor
{

    @Override
    public void execute(CommandSender commandSender, String[] args) throws ValidationException, TechnicalException
    {
        commandSender.sendMessage(ChatColor.GOLD + System.getProperty("java.runtime.version"));
    }
}
