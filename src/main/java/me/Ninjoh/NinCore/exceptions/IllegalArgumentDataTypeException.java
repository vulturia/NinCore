package me.Ninjoh.NinCore.exceptions;


import me.Ninjoh.NinCore.NinCore;
import me.Ninjoh.NinCore.player.NinOnlinePlayer;
import me.Ninjoh.NinCore.util.MessageUtil;
import me.Ninjoh.NinCore.util.TranslationUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class IllegalArgumentDataTypeException extends Exception
{

    public IllegalArgumentDataTypeException(@NotNull CommandSender commandSender, String type, String actualValue)
    {
        Locale locale;
        String msg = null;

        if(commandSender instanceof Player)
        {
            locale = NinOnlinePlayer.fromUUID(((Player) commandSender).getUniqueId()).getMinecraftLocale().toLocale();
        }
        else
        {
            locale = NinCore.getDefaultMinecraftLocale().toLocale();
        }

        switch(type)
        {
            case("BOOLEAN"):
                Object[] args = {actualValue};
                msg = TranslationUtils.transWithArgs("lang.messages", locale, args, "illegalDataType.BOOLEAN");
                break;

            case("INTEGER"):
                Object[] args2 = {actualValue};
                msg = TranslationUtils.transWithArgs("lang.messages", locale, args2, "illegalDataType.INTEGER");
                break;
        }


        if(msg != null)
        {
            MessageUtil.sendError(commandSender, msg);
        }
    }


    public IllegalArgumentDataTypeException()
    {

    }

}
