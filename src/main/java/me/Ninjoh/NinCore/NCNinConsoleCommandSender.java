package me.ninjoh.nincore;


import me.ninjoh.nincore.api.NinConsoleCommandSender;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class NCNinConsoleCommandSender extends NCNinCommandSender implements NinConsoleCommandSender
{

    public NCNinConsoleCommandSender()
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
