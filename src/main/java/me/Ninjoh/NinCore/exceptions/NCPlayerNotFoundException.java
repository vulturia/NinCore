package me.ninjoh.nincore.exceptions;

import me.ninjoh.nincore.player.NCNinPlayer;
import me.ninjoh.nincore.util.NCMessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class NCPlayerNotFoundException extends Exception
{
    private String msg;


    public NCPlayerNotFoundException(@NotNull CommandSender commandSender)
    {
        if(commandSender instanceof Player)
        {
            Locale locale = NCNinPlayer.fromUUID(((Player) commandSender).getUniqueId()).getMinecraftLocale().toLocale();
            ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);
            msg = messages.getString("error.PlayerNotFound");
        }

        NCMessageUtil.sendError(commandSender, msg);
    }


    public NCPlayerNotFoundException()
    {

    }
}
