package me.Ninjoh.NinCore.Library.Exceptions;

import me.Ninjoh.NinCore.Library.Entity.NinOnlinePlayer;
import me.Ninjoh.NinCore.Library.Util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class TooManyArgumentsException extends Exception
{
    private String msg;

    public TooManyArgumentsException(@NotNull CommandSender commandSender)
    {
        if(commandSender instanceof Player)
        {
            Locale locale = NinOnlinePlayer.fromUUID(((Player) commandSender).getUniqueId()).getMinecraftLocale().toLocale();
            ResourceBundle messages = ResourceBundle.getBundle("locale.messages", locale);
            msg = messages.getString("error.PlayerNotFound");
        }

        MessageUtil.sendError(commandSender, msg);
    }
}
