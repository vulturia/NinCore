package me.Ninjoh.NinCore.Library.Interfaces;


import me.Ninjoh.NinCore.Library.Entity.Command;
import me.Ninjoh.NinCore.Library.Entity.SubCommand;
import org.bukkit.plugin.java.JavaPlugin;

public interface CanReceiveMessage
{
    void sendError(String error);

    void sendCommandHelp(Command cmd);

    void sendSubCommandHelp(Command cmd, SubCommand subCmd);

    void sendPluginInfo(JavaPlugin plugin);
}
