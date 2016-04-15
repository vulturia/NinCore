package me.ninjoh.nincore;


import com.google.common.base.Preconditions;
import me.ninjoh.nincore.api.MinecraftLocale;
import me.ninjoh.nincore.api.NinCommandSender;
import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.messaging.MessageColor;
import me.ninjoh.nincore.api.util.MessageUtil;
import me.ninjoh.nincore.api.util.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public class NCNinCommandSender implements NinCommandSender
{
    private CommandSender commandSender;


    public NCNinCommandSender(CommandSender commandSender)
    {
        this.commandSender = commandSender;
    }


    @Override
    public CommandSender toCommandSender()
    {
        return this.commandSender;
    }


    @Override
    public MinecraftLocale getMinecraftLocale()
    {
        if (this.commandSender instanceof Player)
        {
            MinecraftLocale locale = MinecraftLocale.fromLanguageTag(((Player) commandSender).spigot().getLocale());
            if (locale == null)
            {
                locale = NinCore.get().getDefaultMinecraftLocale();
            }

            return locale;
        }
        else
        {
            return MinecraftLocale.getDefault();
        }
    }


    @Override
    public Locale getLocale()
    {
        return this.getMinecraftLocale().toLocale();
    }


    @Override
    public void sendError(@NotNull String error)
    {
        MessageUtil.sendError(this.toCommandSender(), error);
    }


    @Override
    public void sendError(String error, Plugin plugin)
    {
        String result = "[" + plugin.getName() + "] " + error; // TODO
        commandSender.sendMessage(result);
    }


    @Override
    public void sendMessage(MessageColor messageColor, String message, Plugin plugin)
    {
        Preconditions.checkNotNull(messageColor);
        Preconditions.checkNotNull(message);
        Preconditions.checkNotNull(plugin);

        String result = ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + plugin.getName() + ChatColor.GOLD + "] " + ChatColor.RESET +
                messageColor + TextUtils.appendChatColorsToChatColorReset(message, messageColor.toChatColor());
        commandSender.sendMessage(result);
    }


    @Override
    public void sendMessage(@NotNull MessageColor messageColor, @NotNull String message, @NotNull String prefix)
    {
        Preconditions.checkNotNull(messageColor);
        Preconditions.checkNotNull(message);
        Preconditions.checkNotNull(prefix);

        String result = ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + prefix + ChatColor.GOLD + "] " + ChatColor.RESET +
                messageColor + TextUtils.appendChatColorsToChatColorReset(message, messageColor.toChatColor());
        commandSender.sendMessage(result);
    }


    @Override
    public void sendCommandHelp(NinCommand ninCommand)
    {
        MessageUtil.sendCommandHelp(this.toCommandSender(), ninCommand);
    }


    @Override
    public void sendCommandHelp(NinSubCommand ninSubCommand)
    {
        MessageUtil.sendCommandHelp(this.toCommandSender(), ninSubCommand);
    }


    @Override
    public void sendPluginInfo(@NotNull JavaPlugin plugin)
    {
        MessageUtil.sendPluginInfo(this.toCommandSender(), plugin);
    }
}