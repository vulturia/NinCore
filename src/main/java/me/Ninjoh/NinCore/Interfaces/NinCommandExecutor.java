package me.ninjoh.nincore.interfaces;


import me.ninjoh.nincore.command.NCCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface NinCommandExecutor
{
    @NotNull
    NinCommandExecutor init(NCCommand NCCommand);
    void execute(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args);
}
