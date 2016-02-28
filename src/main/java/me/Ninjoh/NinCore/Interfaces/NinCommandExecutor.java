package me.ninjoh.nincore.interfaces;


import me.ninjoh.nincore.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface NinCommandExecutor
{
    @NotNull
    NinCommandExecutor init(Command command);
    void execute(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args);
}
