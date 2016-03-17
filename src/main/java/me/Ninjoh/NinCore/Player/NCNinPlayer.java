package me.ninjoh.nincore.player;

import me.ninjoh.nincore.api.MinecraftLocale;
import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.Nullable;
import me.ninjoh.nincore.api.entity.NinPlayer;
import me.ninjoh.nincore.api.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.UUID;

public class NCNinPlayer extends NCNinOfflinePlayer implements NinPlayer
{
    private Player player;


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

        if (p == null) return null;


        return new NCNinPlayer(p);
    }


    public static NCNinPlayer fromPlayer(Player p)
    {
        return new NCNinPlayer(p);
    }


    /**
     * Get the bukkit player.
     *
     * @return bukkit player.
     */
    @Override
    public Player getPlayer()
    {
        return this.player;
    }


    /**
     * Get a player's client lang.
     *
     * @return The player's lang.
     */
    @Override
    @NotNull
    public MinecraftLocale getMinecraftLocale()
    {
        // Get the player's locale.
        MinecraftLocale locale = MinecraftLocale.fromLanguageTag(getPlayer().spigot().getLocale());
        if (locale == null)
        {
            locale = NinCore.getImplementation().getDefaultMinecraftLocale();
        }

        return locale;
    }


    @Override
    public Locale getLocale()
    {
        return this.getMinecraftLocale().toLocale();
    }


    /**
     * Send a error message to the player.
     * Error format: (LIGHT RED) Error: (RED) %error%.
     * NOTE: Don't use this for sending permission errors
     *
     * @param error The error string to send.
     */
    @Override
    public void sendError(@NotNull String error)
    {
        MessageUtil.sendError(this.getPlayer(), error);
    }


    /**
     * Send command help to the player.
     */
    @Override
    public void sendCommandHelp(@NotNull NinCommand cmd)
    {
        MessageUtil.sendCommandHelp(this.getPlayer(), cmd);
    }


    @Override
    public void sendCommandHelp(NinSubCommand ninSubCommand)
    {
        MessageUtil.sendCommandHelp(this.getPlayer(), ninSubCommand);
    }


    /**
     * Send plugin info to the player.
     */
    public void sendPluginInfo(@NotNull JavaPlugin plugin)
    {
        MessageUtil.sendPluginInfo(getPlayer(), plugin);
    }
}
