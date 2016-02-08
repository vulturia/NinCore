package me.Ninjoh.NinCore.Library.Exceptions;

import me.Ninjoh.NinCore.Library.Util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PlayerNotFoundException extends Exception
{
    private final String msg = "player not found";


    public PlayerNotFoundException(@NotNull CommandSender commandSender)
    {
        MessageUtil.sendError(commandSender, msg);
    }


    public PlayerNotFoundException()
    {

    }
}