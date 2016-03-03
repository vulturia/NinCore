package me.ninjoh.nincore.exceptions;

import me.ninjoh.nincore.player.NCNinPlayer;
import me.ninjoh.nincore.util.NCMessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class NCAccessDeniedException extends Exception
{
    private String msg;


    public NCAccessDeniedException(@NotNull CommandSender commandSender)
    {
        if(commandSender instanceof Player)
        {
            Locale locale = NCNinPlayer.fromUUID(((Player) commandSender).getUniqueId()).getMinecraftLocale().toLocale();
            ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);
            msg = messages.getString("error.AccessDenied");
        }

        NCMessageUtil.sendError(commandSender, msg);
    }


    public NCAccessDeniedException()
    {

    }
}
