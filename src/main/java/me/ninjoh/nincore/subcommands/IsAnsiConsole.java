package me.ninjoh.nincore.subcommands;

import me.ninjoh.nincore.NcCore;
import me.ninjoh.nincore.api.command.executors.NinSubCommandExecutor;
import me.ninjoh.nincore.api.exceptions.TechnicalException;
import me.ninjoh.nincore.api.exceptions.ValidationException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class IsAnsiConsole extends NinSubCommandExecutor
{

    @Override
    public void execute(CommandSender sender, String[] args) throws ValidationException, TechnicalException
    {
        if(NcCore.getInstance().consoleIsAnsiSupported())
        {
            sender.sendMessage(ChatColor.GREEN + "The current console is ANSI supported.");
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "The current console is not ANSI supported.");
        }
    }
}
