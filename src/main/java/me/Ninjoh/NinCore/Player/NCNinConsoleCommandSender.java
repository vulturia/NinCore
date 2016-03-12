package me.ninjoh.nincore.player;


import me.ninjoh.nincore.api.CanReceiveChatMessage;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class NCNinConsoleCommandSender implements CanReceiveChatMessage
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
     */
    @Override
    public void sendPluginInfo(@NotNull JavaPlugin plugin)
    {
        MessageUtil.sendPluginInfo(getConsoleCommandSender(), plugin);
    }


    /**
     * Send error.
     *
     * @param error error string to send.
     */
    @Override
    public void sendError(@NotNull String error)
    {
        MessageUtil.sendError(getConsoleCommandSender(), error);
    }


    /**
     * Send command help to the player
     *
     * @param cmd The command to send help for
     */
    @Override
    public void sendCommandHelp(@NotNull NinCommand cmd)
    {
        MessageUtil.sendCommandHelp(getConsoleCommandSender(), cmd);
    }


    /**
     * Send sub command help to the player.
     *
     * @param ninSubCommand The sub command to send help for.
     */
    @Override
    public void sendCommandHelp(NinSubCommand ninSubCommand)
    {
        MessageUtil.sendCommandHelp(getConsoleCommandSender(), ninSubCommand);
    }
}
