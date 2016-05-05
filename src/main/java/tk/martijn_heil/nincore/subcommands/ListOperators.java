package tk.martijn_heil.nincore.subcommands;


import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import tk.martijn_heil.nincore.api.command.executors.NinSubCommandExecutor;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class ListOperators extends NinSubCommandExecutor
{

    @Override
    public void execute(CommandSender sender, String[] args) throws ValidationException, TechnicalException
    {
        List<String> list = new ArrayList<>();
        Bukkit.getOperators().forEach(operator -> list.add(operator.getName()));
        sender.sendMessage(ChatColor.GOLD + StringUtils.join(list, ", "));
    }
}
