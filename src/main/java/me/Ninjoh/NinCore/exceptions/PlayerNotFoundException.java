package me.Ninjoh.NinCore.exceptions;

import me.Ninjoh.NinCore.player.NinOnlinePlayer;
import me.Ninjoh.NinCore.util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class PlayerNotFoundException extends Exception
{
    private String msg;


    public PlayerNotFoundException(@NotNull CommandSender commandSender)
    {
        if(commandSender instanceof Player)
        {
            Locale locale = NinOnlinePlayer.fromUUID(((Player) commandSender).getUniqueId()).getMinecraftLocale().toLocale();
            ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);
            msg = messages.getString("error.PlayerNotFound");
        }

        MessageUtil.sendError(commandSender, msg);
    }


    public PlayerNotFoundException()
    {

    }
}
