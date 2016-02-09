package me.Ninjoh.NinCore.Library.Exceptions;

import me.Ninjoh.NinCore.Library.Util.LocaleUtils;
import me.Ninjoh.NinCore.Library.Util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class InvalidCommandSenderException extends Exception
{
    private String msg;

    public InvalidCommandSenderException(@NotNull CommandSender commandSender)
    {
        // Sender is never a player.
        Locale locale = LocaleUtils.getDefaultMinecraftLocale().toLocale();
        ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);
        msg = messages.getString("error.InvalidCommandSender");

        MessageUtil.sendError(commandSender, msg);
    }

    public InvalidCommandSenderException()
    {

    }
}
