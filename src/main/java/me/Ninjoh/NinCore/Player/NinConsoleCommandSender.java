package me.ninjoh.nincore.player;


import me.ninjoh.nincore.command.Command;
import me.ninjoh.nincore.command.SubCommand;
import me.ninjoh.nincore.interfaces.CanReceiveMessage;
import me.ninjoh.nincore.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class NinConsoleCommandSender implements CanReceiveMessage
{


    /**
     * Get the ConsoleCommandSender
     *
     * @return The ConsoleCommandSender
     */
    public ConsoleCommandSender getConsoleCommandSender()
    {
        return Bukkit.getServer().getConsoleSender();
    }


    /**
     * Send plugin info to the player.
     *
     */
    public void sendPluginInfo(@NotNull JavaPlugin plugin)
    {
        MessageUtil.sendPluginInfo((CommandSender) getConsoleCommandSender(), plugin);
    }

    /**
     * Send error.
     *
     * @param error error string to send.
     */
    public void sendError(@NotNull String error)
    {
        MessageUtil.sendError((CommandSender) getConsoleCommandSender(), error);
    }

    /**
     * Send command help to the player.
     *
     */
    public void sendCommandHelp(@NotNull Command cmd)
    {
        MessageUtil.sendCommandHelp((CommandSender) getConsoleCommandSender(), cmd);
    }


    /**
     * Send sub command help to the player.
     *
     * @param cmd The command parent of the sub command.
     * @param subCmd The sub command to send help for.
     */
    public void sendSubCommandHelp(@NotNull Command cmd, @NotNull SubCommand subCmd)
    {
        MessageUtil.sendCommandHelp((CommandSender) getConsoleCommandSender(), cmd, subCmd);
    }
}
