package me.Ninjoh.NinCore.Library.Exceptions;

import me.Ninjoh.NinCore.Library.Util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TooManyArgumentsException extends Exception
{
    private final String msg = "too many arguments supplied";

    public TooManyArgumentsException(@NotNull CommandSender commandSender)
    {
        MessageUtil.sendError(commandSender, msg);
    }
}
