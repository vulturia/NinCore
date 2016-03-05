package me.ninjoh.nincore;


import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.util.MessageUtil;
import me.ninjoh.nincore.command.NCCommand;
import me.ninjoh.nincore.command.NCSubCommand;
import me.ninjoh.nincore.interfaces.CanReceiveMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

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


    @Override
    public void sendError(@NotNull String error)
    {
        MessageUtil.sendError(this.getCommandSender(), error);
    }


    @Override
    public void sendCommandHelp(@NotNull NCCommand cmd)
    {
        MessageUtil.sendCommandHelp(this.getCommandSender(), cmd);
    }


    @Override
    public void sendSubCommandHelp(@NotNull NCCommand cmd, @NotNull NCSubCommand subCmd)
    {
        MessageUtil.sendCommandHelp(this.getCommandSender(), cmd, subCmd);
    }


    @Override
    public void sendPluginInfo(@NotNull JavaPlugin plugin)
    {
        MessageUtil.sendPluginInfo(this.getCommandSender(), plugin);
    }
}
