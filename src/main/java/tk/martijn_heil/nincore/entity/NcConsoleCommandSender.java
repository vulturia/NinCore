package tk.martijn_heil.nincore.entity;


import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import tk.martijn_heil.nincore.api.entity.NinConsoleCommandSender;

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
