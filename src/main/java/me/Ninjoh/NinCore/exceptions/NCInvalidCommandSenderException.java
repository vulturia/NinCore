package me.ninjoh.nincore.exceptions;

import me.ninjoh.nincore.util.NCLocaleUtils;
import me.ninjoh.nincore.util.NCMessageUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class NCInvalidCommandSenderException extends Exception
{
    private String msg;

    public NCInvalidCommandSenderException(@NotNull CommandSender commandSender)
    {
        // Sender is never a player.
        Locale locale = NCLocaleUtils.getDefaultMinecraftLocale().toLocale();
        ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);
        msg = messages.getString("error.InvalidCommandSender");

        NCMessageUtil.sendError(commandSender, msg);
    }

    public NCInvalidCommandSenderException()
    {

    }
}
