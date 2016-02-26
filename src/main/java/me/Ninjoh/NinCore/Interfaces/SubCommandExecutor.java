package me.Ninjoh.NinCore.interfaces;


import me.Ninjoh.NinCore.command.Command;
import me.Ninjoh.NinCore.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface SubCommandExecutor
{
    void Handle(@NotNull CommandSender sender, @NotNull String[] args,@NotNull  Command command, @NotNull SubCommand subCommand);
}
