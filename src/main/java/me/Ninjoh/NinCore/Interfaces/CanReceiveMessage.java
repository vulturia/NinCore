package me.ninjoh.nincore.interfaces;


import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.command.NCCommand;
import me.ninjoh.nincore.command.NCSubCommand;
import org.bukkit.plugin.java.JavaPlugin;

public interface CanReceiveMessage
{
    void sendError(@NotNull String error);

    void sendCommandHelp(@NotNull NCCommand cmd);

    void sendSubCommandHelp(@NotNull NCCommand cmd, @NotNull NCSubCommand subCmd);

    void sendPluginInfo(@NotNull JavaPlugin plugin);
}
