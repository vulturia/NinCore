package me.Ninjoh.NinCore.Library.Entity;

import me.Ninjoh.NinCore.Library.Interfaces.CanReceiveMessage;
import me.Ninjoh.NinCore.Library.Util.LocaleUtils;
import me.Ninjoh.NinCore.Library.Util.MessageUtil;
import me.Ninjoh.NinCore.Library.Util.MinecraftLocale;
import me.Ninjoh.NinCore.NinCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class NinOnlinePlayer extends NinOfflinePlayer implements CanReceiveMessage
{

    /**
     * Constructor
     *
     * @param uuid The player's UUID
     */
    public NinOnlinePlayer(UUID uuid)
    {
        super(uuid);
    }


    @NotNull
    public static NinOnlinePlayer fromUUID(UUID uuid)
    {
        return new NinOnlinePlayer(uuid);
    }


    /**
     * Get the bukkit player.
     *
     * @return bukkit Player.
     */
    public Player getPlayer()
    {
        return Bukkit.getServer().getPlayer(uuid);
    }


    /**
     * Get a player's client locale.
     *
     * @return The player's locale.
     */
    @NotNull
    public MinecraftLocale getMinecraftLocale()
    {
        try
        {
            if(!NinCore.useLocalization())
            {
                return LocaleUtils.getDefaultMinecraftLocale();
            }


            Player p = getPlayer();

            if(getMethod("getHandle", p.getClass()) != null)
            {
                Object ep = getMethod("getHandle", p.getClass()).invoke(p, (Object[]) null);

                Field f = ep.getClass().getDeclaredField("locale");
                f.setAccessible(true);
                String language = (String) f.get(ep);

                MinecraftLocale locale = null;

                if(LocaleUtils.getMinecraftLocale(language) != null)
                {
                    locale = LocaleUtils.getMinecraftLocale(language);
                }

                if(locale == null)
                {
                    locale = LocaleUtils.getDefaultMinecraftLocale();
                }


                return locale;
            }
            else
            {
                return LocaleUtils.getDefaultMinecraftLocale();
            }
        }
        catch(@NotNull IllegalAccessException | InvocationTargetException | NoSuchFieldException | NullPointerException e)
        {
            return LocaleUtils.getDefaultMinecraftLocale();
        }
    }


    @Nullable
    private Method getMethod(String name, @NotNull Class<?> clazz)
    {
        for (Method m : clazz.getDeclaredMethods())
        {
            if (m.getName().equals(name))
                return m;
        }
        return null;
    }


    /**
     * Send a error message to the player.
     * Error format: &cError: &4%error%.
     *
     * NOTE: Don't use this for sending permission errors
     *
     * @param error The error string to send.
     */
    public void sendError(@NotNull String error)
    {
        // Make first character of error string uppercase.
        String finalErrStr = error.substring(0, 1).toUpperCase() + error.substring(1);

        getPlayer().sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + finalErrStr + ".");
    }


    /**
     * Send command help to the player.
     *
     */
    public void sendCommandHelp(@NotNull Command cmd)
    {
        MessageUtil.sendCommandHelp((CommandSender) getPlayer(), cmd);
    }


    /**
     * Send sub command help to the player.
     *
     * @param cmd The command parent of the sub command.
     * @param subCmd The sub command to send help for.
     */
    public void sendSubCommandHelp(@NotNull Command cmd, @NotNull SubCommand subCmd)
    {
        MessageUtil.sendCommandHelp((CommandSender) getPlayer(), cmd, subCmd);
    }


    /**
     * Send plugin info to the player.
     *
     */
    public void sendPluginInfo(@NotNull JavaPlugin plugin)
    {
        MessageUtil.sendPluginInfo((CommandSender) getPlayer(), plugin);
    }
}
