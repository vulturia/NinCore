package me.ninjoh.nincore.player;

import me.ninjoh.nincore.api.MinecraftLocale;
import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.Nullable;
import me.ninjoh.nincore.api.entity.NinPlayer;
import me.ninjoh.nincore.api.util.MessageUtil;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class NCNinPlayer extends NCNinOfflinePlayer implements NinPlayer
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
    public MinecraftLocale getMinecraftLocale()
    {
        // Get the player's locale.
        MinecraftLocale locale = MinecraftLocale.fromLanguageTag(getPlayer().spigot().getLocale());
        if(locale == null) {locale = NinCore.getImplementation().getDefaultMinecraftLocale();}

        return locale;
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
        MessageUtil.sendError(this.getPlayer(), error);
    }


    /**
     * Send command help to the player.
     *
     */
    public void sendCommandHelp(@NotNull NinCommand cmd)
    {
        MessageUtil.sendCommandHelp((CommandSender) getPlayer(), cmd);
    }


    /**
     * Send sub command help to the player.
     *
     * @param cmd The command parent of the sub command.
     * @param subCmd The sub command to send help for.
     */
    public void sendSubCommandHelp(NinCommand cmd, NinSubCommand subCmd)
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


    public boolean isFlyingUsingElytra()
    {
        EntityPlayer ep = ((CraftPlayer) getPlayer()).getHandle();

        return ep.cB();
    }
}
