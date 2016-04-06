package me.ninjoh.nincore.subcommands;


import me.ninjoh.nincore.api.command.executors.NinSubCommandExecutor;
import me.ninjoh.nincore.api.exceptions.TechnicalException;
import me.ninjoh.nincore.api.exceptions.ValidationException;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ListOperators extends NinSubCommandExecutor
{

    @Override
    public void execute(CommandSender sender, String[] args) throws ValidationException, TechnicalException
    {
        sender.sendMessage(ChatColor.GOLD + StringUtils.join(Bukkit.getOperators(), ", "));
    }
}
