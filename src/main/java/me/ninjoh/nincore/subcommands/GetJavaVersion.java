package me.ninjoh.nincore.subcommands;


import me.ninjoh.nincore.api.command.executors.NinSubCommandExecutor;
import me.ninjoh.nincore.api.exceptions.TechnicalException;
import me.ninjoh.nincore.api.exceptions.ValidationException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class GetJavaVersion extends NinSubCommandExecutor
{

    @Override
    public void execute(CommandSender commandSender, String[] args) throws ValidationException, TechnicalException
    {
        commandSender.sendMessage(ChatColor.GOLD + System.getProperty("java.runtime.version"));
    }
}
