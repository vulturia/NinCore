package me.ninjoh.nincore.interfaces;


import me.ninjoh.nincore.command.SubCommand;
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
