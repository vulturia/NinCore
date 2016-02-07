package me.Ninjoh.NinCore.Library.Entity;


import me.Ninjoh.NinCore.Library.Interfaces.CanReceiveMessage;
import me.Ninjoh.NinCore.Library.Util.MessageUtil;
import me.Ninjoh.NinCore.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class NinConsoleCommandSender implements CanReceiveMessage
{
    JavaPlugin plugin = Main.plugin;


    /**
     * Get the ConsoleCommandSender
     *
     * @return The ConsoleCommandSender
     */
    public ConsoleCommandSender getConsoleCommandSender()
    {
        return plugin.getServer().getConsoleSender();
    }


    /**
     * Send plugin info to the player.
     *
     */
    public void sendPluginInfo(JavaPlugin plugin)
    {
        MessageUtil.sendPluginInfo((CommandSender) getConsoleCommandSender(), plugin);
    }

    /**
     * Send error.
     *
     * @param error error string to send.
     */
    public void sendError(String error)
    {
        MessageUtil.sendError((CommandSender) getConsoleCommandSender(), error);
    }

    /**
     * Send command help to the player.
     *
     */
    public void sendCommandHelp(Command cmd)
    {
        MessageUtil.sendCommandHelp((CommandSender) getConsoleCommandSender(), cmd);
    }


    /**
     * Send sub command help to the player.
     *
     * @param cmd The command parent of the sub command.
     * @param subCmd The sub command to send help for.
     */
    public void sendSubCommandHelp(Command cmd, SubCommand subCmd)
    {
        MessageUtil.sendCommandHelp((CommandSender) getConsoleCommandSender(), cmd, subCmd);
    }
}
