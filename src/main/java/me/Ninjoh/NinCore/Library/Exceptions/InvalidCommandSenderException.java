package me.Ninjoh.NinCore.Library.Exceptions;

import me.Ninjoh.NinCore.Library.Util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class InvalidCommandSenderException extends Exception
{
    private final String msg = "this command can only be sent by a player";


    public InvalidCommandSenderException(@NotNull CommandSender commandSender)
    {
        MessageUtil.sendError(commandSender, msg);
    }

    public InvalidCommandSenderException()
    {

    }
}
