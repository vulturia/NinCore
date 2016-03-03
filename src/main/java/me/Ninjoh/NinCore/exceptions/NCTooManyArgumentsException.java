package me.ninjoh.nincore.exceptions;

import me.ninjoh.nincore.NCMinecraftLocale;
import me.ninjoh.nincore.player.NCNinPlayer;
import me.ninjoh.nincore.util.NCMessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class NCTooManyArgumentsException extends Exception
{
    private String msg;

    public NCTooManyArgumentsException(@NotNull CommandSender commandSender)
    {
        Locale locale;
        if(commandSender instanceof Player)
        {
            locale = NCNinPlayer.fromUUID(((Player) commandSender).getUniqueId()).getMinecraftLocale().toLocale();

        }
        else
        {
            locale = NCMinecraftLocale.getDefault().toLocale();
        }

        ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);
        msg = messages.getString("error.PlayerNotFound");

        NCMessageUtil.sendError(commandSender, msg);
    }
}
