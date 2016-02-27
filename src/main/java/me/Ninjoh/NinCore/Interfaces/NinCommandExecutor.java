package me.Ninjoh.NinCore.interfaces;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface NinCommandExecutor
{
    @NotNull
    NinCommandExecutor init(me.Ninjoh.NinCore.command.Command command);
    boolean execute(CommandSender sender, Command cmd, String label, String[] args);
}
