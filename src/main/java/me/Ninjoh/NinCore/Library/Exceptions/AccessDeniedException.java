package me.Ninjoh.NinCore.Library.Exceptions;

import me.Ninjoh.NinCore.Library.Util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AccessDeniedException extends Exception
{
    private final String msg = "you do not have access to use this command";


    public AccessDeniedException(@NotNull CommandSender commandSender)
    {
        MessageUtil.sendError(commandSender, msg);
    }


    public AccessDeniedException()
    {

    }
}
