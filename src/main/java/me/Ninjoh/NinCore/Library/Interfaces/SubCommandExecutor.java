package me.Ninjoh.NinCore.Library.Interfaces;


import me.Ninjoh.NinCore.Library.Entity.Command;
import me.Ninjoh.NinCore.Library.Entity.SubCommand;
import org.bukkit.command.CommandSender;

public interface SubCommandExecutor
{
    void Handle(CommandSender sender, String[] args, Command command, SubCommand subCommand);
}
