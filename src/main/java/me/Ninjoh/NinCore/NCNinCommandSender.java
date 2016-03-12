package me.ninjoh.nincore;


import me.ninjoh.nincore.api.MinecraftLocale;
import me.ninjoh.nincore.api.NinCommandSender;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class NCNinCommandSender implements NinCommandSender
{
    private CommandSender commandSender;


    public NCNinCommandSender(CommandSender commandSender)
    {
        this.commandSender = commandSender;
    }


    public CommandSender getCommandSender()
    {
        return this.commandSender;
    }


    @Override
    public MinecraftLocale getMinecraftLocale()
    {
        return null;
    }


    @Override
    public void sendError(@NotNull String error)
    {
        MessageUtil.sendError(this.getCommandSender(), error);
    }


    @Override
    public void sendCommandHelp(NinCommand ninCommand)
    {
        MessageUtil.sendCommandHelp(this.getCommandSender(), ninCommand);
    }


    @Override
    public void sendCommandHelp(NinSubCommand ninSubCommand)
    {
        MessageUtil.sendCommandHelp(this.getCommandSender(), ninSubCommand);
    }


    @Override
    public void sendPluginInfo(@NotNull JavaPlugin plugin)
    {
        MessageUtil.sendPluginInfo(this.getCommandSender(), plugin);
    }
}