package me.Ninjoh.NinCore.Library.Interfaces;


import me.Ninjoh.NinCore.Library.Entity.Command;
import me.Ninjoh.NinCore.Library.Entity.SubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface SubCommandExecutor
{
    void Handle(@NotNull CommandSender sender, @NotNull String[] args,@NotNull  Command command, @NotNull SubCommand subCommand);
}
