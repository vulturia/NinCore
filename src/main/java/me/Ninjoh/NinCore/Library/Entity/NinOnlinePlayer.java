package me.Ninjoh.NinCore.Library.Entity;

import me.Ninjoh.NinCore.Library.Interfaces.CanReceiveMessage;
import me.Ninjoh.NinCore.Library.Util.MessageUtil;
import me.Ninjoh.NinCore.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class NinOnlinePlayer extends NinOfflinePlayer implements CanReceiveMessage
{
    private JavaPlugin plugin = Main.plugin;


    /**
     * Constructor
     *
     * @param playerUUID The player's UUID
     */
    public NinOnlinePlayer(UUID playerUUID)
    {
        super(playerUUID);
    }


    /**
     * Get the bukkit player.
     *
     * @return bukkit Player.
     */
    public Player getPlayer()
    {
        return plugin.getServer().getPlayer(uuid);
    }


    /**
     * Send a error message to the player.
     * Error format: &cError: &4%error%.
     *
     * NOTE: Don't use this for sending permission errors
     *
     * @param error The error string to send.
     */
    public void sendError(String error)
    {
        // Make first character of error string uppercase.
        String finalErrStr = error.substring(0, 1).toUpperCase() + error.substring(1);

        getPlayer().sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + finalErrStr + ".");
    }


    /**
     * Send command help to the player.
     *
     */
    public void sendCommandHelp(Command cmd)
    {
        MessageUtil.sendCommandHelp((CommandSender) getPlayer(), cmd);
    }


    /**
     * Send sub command help to the player.
     *
     * @param cmd The command parent of the sub command.
     * @param subCmd The sub command to send help for.
     */
    public void sendSubCommandHelp(Command cmd, SubCommand subCmd)
    {
        MessageUtil.sendCommandHelp((CommandSender) getPlayer(), cmd, subCmd);
    }


    /**
     * Send plugin info to the player.
     *
     */
    public void sendPluginInfo(JavaPlugin plugin)
    {
        MessageUtil.sendPluginInfo((CommandSender) getPlayer(), plugin);
    }
}
