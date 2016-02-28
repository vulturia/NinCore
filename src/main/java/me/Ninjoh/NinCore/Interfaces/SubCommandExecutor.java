package me.Ninjoh.NinCore.interfaces;


import me.Ninjoh.NinCore.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface SubCommandExecutor
{
    SubCommand subCommand = null;
    @NotNull
    SubCommandExecutor init(SubCommand subCommand);
    void execute(@NotNull CommandSender sender, @NotNull String[] args);
}
