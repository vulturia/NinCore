package me.ninjoh.nincore.command.executors;


import me.ninjoh.nincore.api.command.executors.NinSubCommandExecutor;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.entity.NcCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

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
