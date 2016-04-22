package tk.martijn_heil.nincore.command.executors;


import tk.martijn_heil.nincore.entity.NcCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import tk.martijn_heil.nincore.api.command.executors.NinSubCommandExecutor;

public class InfoSubCmdExecutor extends NinSubCommandExecutor
{
    private JavaPlugin plugin;


    public InfoSubCmdExecutor(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }


    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args)
    {
        NcCommandSender.fromCommandSender(sender).sendPluginInfo(plugin);
    }
}
