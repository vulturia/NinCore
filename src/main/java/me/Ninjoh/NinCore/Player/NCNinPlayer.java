package me.ninjoh.nincore.player;

import me.ninjoh.nincore.NCMinecraftLocale;
import me.ninjoh.nincore.api.player.NinPlayer;
import me.ninjoh.nincore.command.NCCommand;
import me.ninjoh.nincore.command.NCSubCommand;
import me.ninjoh.nincore.interfaces.CanReceiveMessage;
import me.ninjoh.nincore.util.NCLocaleUtils;
import me.ninjoh.nincore.util.NCMessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.UUID;

public class NCNinPlayer extends NCNinOfflinePlayer implements CanReceiveMessage, NinPlayer
{
    private Player player;
    private boolean isFlyingUsingElytra;

    /**
     * Constructor
     *
     * @param p The player
     */
    public NCNinPlayer(Player p)
    {
        super(Bukkit.getOfflinePlayer(p.getUniqueId()));
        player = p;
    }



    @Nullable
    public static NCNinPlayer fromUUID(UUID uuid)
    {
        Player p = Bukkit.getServer().getPlayer(uuid);

        if(p == null) return null;


        return new NCNinPlayer(p);
    }


    public static NCNinPlayer fromPlayer(Player p)
    {
        return new NCNinPlayer(p);
    }


    public static NCNinPlayer fromOfflinePlayer(OfflinePlayer p)
    {
        return NCNinPlayer.fromUUID(p.getUniqueId());
    }


    /**
     * Get the bukkit player.
     *
     * @return bukkit player.
     */
    public Player getPlayer()
    {
        return Bukkit.getServer().getPlayer(getUuid());
    }


    /**
     * Get a player's client lang.
     *
     * @return The player's lang.
     */
    @NotNull
    public NCMinecraftLocale getMinecraftLocale()
    {
        // Get the player's locale.
        NCMinecraftLocale locale = NCMinecraftLocale.fromLanguageTag(getPlayer().spigot().getLocale());
        if(locale == null) {locale = NCLocaleUtils.getDefaultMinecraftLocale();}

        return locale;
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
        NCMessageUtil.sendError(this.getPlayer(), error);
    }


    /**
     * Send command help to the player.
     *
     */
    public void sendCommandHelp(@NotNull NCCommand cmd)
    {
        NCMessageUtil.sendCommandHelp((CommandSender) getPlayer(), cmd);
    }


    /**
     * Send sub command help to the player.
     *
     * @param cmd The command parent of the sub command.
     * @param subCmd The sub command to send help for.
     */
    public void sendSubCommandHelp(@NotNull NCCommand cmd, @NotNull NCSubCommand subCmd)
    {
        NCMessageUtil.sendCommandHelp((CommandSender) getPlayer(), cmd, subCmd);
    }


    /**
     * Send plugin info to the player.
     *
     */
    public void sendPluginInfo(@NotNull JavaPlugin plugin)
    {
        NCMessageUtil.sendPluginInfo((CommandSender) getPlayer(), plugin);
    }


    public void setIsFlyingUsingElytra(boolean value)
    {
        this.isFlyingUsingElytra = value;
    }


    public boolean isFlyingUsingElytra()
    {
        return isFlyingUsingElytra;
    }
}
