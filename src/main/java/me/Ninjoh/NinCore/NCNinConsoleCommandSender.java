package me.ninjoh.nincore;


import com.google.common.base.Preconditions;
import me.ninjoh.nincore.api.NinConsoleCommandSender;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.messaging.MessageColor;
import me.ninjoh.nincore.api.util.MessageUtil;
import me.ninjoh.nincore.api.util.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class NCNinConsoleCommandSender implements NinConsoleCommandSender
{

    @Override
    public ConsoleCommandSender getConsoleCommandSender()
    {
        return Bukkit.getConsoleSender();
    }


    @Override
    public void sendError(String s)
    {
        MessageUtil.sendError(this.getConsoleCommandSender(), s);
    }


    @Override
    public void sendError(@NotNull String s, @NotNull Plugin plugin)
    {

    }


    @Override
    public void sendMessage(MessageColor messageColor, String message, Plugin plugin)
    {
        String result = ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + plugin.getName() + ChatColor.GOLD + "] " + ChatColor.RESET +
                messageColor + TextUtils.appendChatColorsToChatColorReset(message, messageColor.toChatColor());
        getConsoleCommandSender().sendMessage(result);
    }


    @Override
    public void sendMessage(@NotNull MessageColor messageColor, @NotNull String message, @NotNull String prefix)
    {
        Preconditions.checkNotNull(messageColor);
        Preconditions.checkNotNull(message);
        Preconditions.checkNotNull(prefix);

        String result = ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + prefix + ChatColor.GOLD + "] " + ChatColor.RESET +
                messageColor + TextUtils.appendChatColorsToChatColorReset(message, messageColor.toChatColor());
        getConsoleCommandSender().sendMessage(result);
    }


    @Override
    public void sendCommandHelp(NinCommand ninCommand)
    {
        MessageUtil.sendCommandHelp(this.getConsoleCommandSender(), ninCommand);
    }


    @Override
    public void sendCommandHelp(NinSubCommand ninSubCommand)
    {
        MessageUtil.sendCommandHelp(this.getConsoleCommandSender(), ninSubCommand);
    }


    @Override
    public void sendPluginInfo(JavaPlugin javaPlugin)
    {
        MessageUtil.sendPluginInfo(this.getConsoleCommandSender(), javaPlugin);
    }
}
