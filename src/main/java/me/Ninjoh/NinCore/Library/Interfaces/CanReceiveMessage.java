package me.Ninjoh.NinCore.Library.Interfaces;


import me.Ninjoh.NinCore.Library.Entity.Command;
import me.Ninjoh.NinCore.Library.Entity.SubCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public interface CanReceiveMessage
{
    void sendError(@NotNull String error);

    void sendCommandHelp(@NotNull Command cmd);

    void sendSubCommandHelp(@NotNull Command cmd, @NotNull SubCommand subCmd);

    void sendPluginInfo(@NotNull JavaPlugin plugin);
}
