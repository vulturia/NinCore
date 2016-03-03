package me.ninjoh.nincore;


import me.ninjoh.nincore.command.NCCommand;
import me.ninjoh.nincore.command.NCSubCommand;
import me.ninjoh.nincore.interfaces.CanReceiveMessage;
import me.ninjoh.nincore.util.NCMessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class NinCommandSender implements CanReceiveMessage
{
    private CommandSender commandSender;


    public NinCommandSender(CommandSender commandSender)
    {
        this.commandSender = commandSender;
    }


    public CommandSender getCommandSender()
    {
        return this.commandSender;
    }


    public void sendError(@NotNull String error)
    {
        NCMessageUtil.sendError(this.getCommandSender(), error);
    }


    public void sendCommandHelp(@NotNull NCCommand cmd)
    {
        NCMessageUtil.sendCommandHelp(this.getCommandSender(), cmd);
    }


    public void sendSubCommandHelp(@NotNull NCCommand cmd, @NotNull NCSubCommand subCmd)
    {
        NCMessageUtil.sendCommandHelp(this.getCommandSender(), cmd, subCmd);
    }


    public void sendPluginInfo(@NotNull JavaPlugin plugin)
    {
        NCMessageUtil.sendPluginInfo(this.getCommandSender(), plugin);
    }
}
