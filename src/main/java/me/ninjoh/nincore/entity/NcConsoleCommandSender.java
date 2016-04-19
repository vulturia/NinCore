package me.ninjoh.nincore.entity;


import me.ninjoh.nincore.api.entity.NinConsoleCommandSender;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class NcConsoleCommandSender extends NcCommandSender implements NinConsoleCommandSender
{

    public NcConsoleCommandSender()
    {
        super(Bukkit.getConsoleSender());
    }


    @Override
    @Deprecated
    public ConsoleCommandSender getConsoleCommandSender()
    {
        return this.toConsoleCommandSender();
    }


    @Override
    public ConsoleCommandSender toConsoleCommandSender()
    {
        return Bukkit.getConsoleSender();
    }
}
