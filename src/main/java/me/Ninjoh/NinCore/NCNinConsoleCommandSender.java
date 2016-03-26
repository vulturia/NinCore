package me.ninjoh.nincore;


import me.ninjoh.nincore.api.NinConsoleCommandSender;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class NCNinConsoleCommandSender implements NinConsoleCommandSender
{

    @Override
    public ConsoleCommandSender getConsoleCommandSender()
    {
        return Bukkit.getConsoleSender();
    }


    @Override
    public void sendError(String s)
    {
        MessageUtil.sendError(this.getConsoleCommandSender(), s);
    }


    @Override
    public void sendCommandHelp(NinCommand ninCommand)
    {
        MessageUtil.sendCommandHelp(this.getConsoleCommandSender(), ninCommand);
    }


    @Override
    public void sendCommandHelp(NinSubCommand ninSubCommand)
    {
        MessageUtil.sendCommandHelp(this.getConsoleCommandSender(), ninSubCommand);
    }


    @Override
    public void sendPluginInfo(JavaPlugin javaPlugin)
    {
        MessageUtil.sendPluginInfo(this.getConsoleCommandSender(), javaPlugin);
    }
}
