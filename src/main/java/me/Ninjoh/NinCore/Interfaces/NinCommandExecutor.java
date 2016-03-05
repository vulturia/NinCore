package me.ninjoh.nincore.interfaces;


import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.command.NCCommand;
import org.bukkit.command.CommandSender;

public interface NinCommandExecutor
{
    @NotNull
    NinCommandExecutor init(NCCommand NCCommand);
    void execute(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args);
}
