package me.ninjoh.nincore.exceptions;


import me.ninjoh.nincore.NinCoreOld;
import me.ninjoh.nincore.player.NCNinPlayer;
import me.ninjoh.nincore.util.NCMessageUtil;
import me.ninjoh.nincore.util.NCTranslationUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class NCIllegalArgumentDataTypeException extends Exception
{

    public NCIllegalArgumentDataTypeException(@NotNull CommandSender commandSender, @NotNull String type, String actualValue)
    {
        Locale locale;
        String msg = null;

        if(commandSender instanceof Player)
        {
            locale = NCNinPlayer.fromUUID(((Player) commandSender).getUniqueId()).getMinecraftLocale().toLocale();
        }
        else
        {
            locale = NinCoreOld.getDefaultNCMinecraftLocale().toLocale();
        }

        switch(type)
        {
            case("BOOLEAN"):
                Object[] args = {actualValue};
                msg = NCTranslationUtils.transWithArgs("lang.messages", locale, args, "illegalDataType.BOOLEAN");
                break;

            case("INTEGER"):
                Object[] args2 = {actualValue};
                msg = NCTranslationUtils.transWithArgs("lang.messages", locale, args2, "illegalDataType.INTEGER");
                break;
        }


        if(msg != null)
        {
            NCMessageUtil.sendError(commandSender, msg);
        }
    }


    public NCIllegalArgumentDataTypeException()
    {

    }

}
