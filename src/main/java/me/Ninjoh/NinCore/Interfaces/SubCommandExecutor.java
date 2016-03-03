package me.ninjoh.nincore.interfaces;


import me.ninjoh.nincore.command.NCSubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SubCommandExecutor
{
    @Nullable
    NCSubCommand NC_SUB_COMMAND = null;
    @NotNull
    SubCommandExecutor init(NCSubCommand NCSubCommand);
    void execute(@NotNull CommandSender sender, @NotNull String[] args);
}
