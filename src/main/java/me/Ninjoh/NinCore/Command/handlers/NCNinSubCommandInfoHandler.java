package me.ninjoh.nincore.command.handlers;


import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.executors.SubCommandExecutor;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class NCNinSubCommandInfoHandler implements SubCommandExecutor
{
    private NinSubCommand subCommand;
    private JavaPlugin plugin;


    public NCNinSubCommandInfoHandler(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }


    @NotNull
    @Override
    public SubCommandExecutor init(NinSubCommand NCSubCommand)
    {
        this.subCommand = NCSubCommand;
        return this;
    }


    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args)
    {
        NinCore.get().getNinCommandSender(sender).sendPluginInfo(plugin);
    }
}