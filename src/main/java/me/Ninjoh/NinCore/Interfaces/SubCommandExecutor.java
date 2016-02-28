package me.Ninjoh.NinCore.interfaces;


import me.Ninjoh.NinCore.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SubCommandExecutor
{
    @Nullable
    SubCommand subCommand = null;
    @NotNull
    SubCommandExecutor init(SubCommand subCommand);
    void execute(@NotNull CommandSender sender, @NotNull String[] args);
}
