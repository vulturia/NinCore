package me.ninjoh.nincore.exceptions;

import me.ninjoh.nincore.player.NinOnlinePlayer;
import me.ninjoh.nincore.util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class AccessDeniedException extends Exception
{
    private String msg;


    public AccessDeniedException(@NotNull CommandSender commandSender)
    {
        if(commandSender instanceof Player)
        {
            Locale locale = NinOnlinePlayer.fromUUID(((Player) commandSender).getUniqueId()).getMinecraftLocale().toLocale();
            ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);
            msg = messages.getString("error.AccessDenied");
        }

        MessageUtil.sendError(commandSender, msg);
    }


    public AccessDeniedException()
    {

    }
}
