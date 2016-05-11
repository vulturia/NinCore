package tk.martijn_heil.nincore.entity;


import com.google.common.base.Preconditions;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import tk.martijn_heil.nincore.NcCore;
import tk.martijn_heil.nincore.api.NinCore;
import tk.martijn_heil.nincore.api.command.NinCommand;
import tk.martijn_heil.nincore.api.command.NinSubCommand;
import tk.martijn_heil.nincore.api.entity.NinCommandSender;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;
import tk.martijn_heil.nincore.api.localization.MinecraftLocale;
import tk.martijn_heil.nincore.api.messaging.MessageColor;
import tk.martijn_heil.nincore.api.util.MessageUtil;
import tk.martijn_heil.nincore.api.util.TextUtils;
import tk.martijn_heil.nincore.api.util.TranslationUtils;

import java.util.Locale;
import java.util.ResourceBundle;

public class NcCommandSender implements NinCommandSender
{
    private CommandSender commandSender;


    public NcCommandSender(CommandSender commandSender)
    {
        this.commandSender = commandSender;
    }


    @Override
    @NotNull
    public CommandSender toCommandSender()
    {
        return this.commandSender;
    }


    @Override
    public MinecraftLocale getMinecraftLocale()
    {
        try
        {

            MinecraftLocale locale;
            if (this.commandSender instanceof Player)
            {
                try
                {
                    locale = MinecraftLocale.fromLanguageTag(((Player) commandSender).spigot().getLocale());
                    if (locale == null)
                    {
                        locale = NinCore.get().getLocalizationManager().getDefaultMinecraftLocale();
                    }
                }
                catch (Exception e)
                {
                    return MinecraftLocale.fromLanguageTag(((CraftPlayer) commandSender).getHandle().locale);
                }


                return locale;
            }
            else
            {
                return MinecraftLocale.getDefault();
            }
        }
        catch (Exception e)
        {
            NcCore.getInstance().getNinLogger().warning("Could not get player's locale, returning default MinecraftLocale.");
            NcCore.getInstance().getNinLogger().fine(ExceptionUtils.getFullStackTrace(e));
            return NcCore.getInstance().getLocalizationManager().getDefaultMinecraftLocale();
        }
    }


    @Override
    public void sendError(@NotNull String error)
    {
        MessageUtil.sendError(this.toCommandSender(), error);
    }


    @Override
    public void sendError(@NotNull String error,@NotNull Plugin plugin)
    {
        //String result = "[" + plugin.getName() + "] " + error; // TODO
        String prefix = ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + plugin.getName() + ChatColor.GOLD + "] " + ChatColor.RESET;


        // Get target's locale.
        Locale locale;
        if (commandSender instanceof Player)
        {
            locale = NinOnlinePlayer.fromPlayer((Player) commandSender).getLocale();
        }
        else
        {
            locale = NinCore.get().getLocalizationManager().getDefaultMinecraftLocale().toLocale();
        }

        String msg = TranslationUtils.transWithArgs(ResourceBundle.getBundle("me.ninjoh.nincore.res.messages", locale), new Object[]{error}, "sendError");
        this.commandSender.sendMessage(prefix + msg);
    }


    @Override
    public void sendMessage(@NotNull MessageColor messageColor, @NotNull String message, @NotNull Plugin plugin)
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
    public void sendMessage(@NotNull String s)
    {
        this.commandSender.sendMessage(s);
    }


    @Override
    public void sendCommandHelp(@NotNull NinCommand ninCommand)
    {
        MessageUtil.sendCommandHelp(this.toCommandSender(), ninCommand);
    }


    @Override
    public void sendCommandHelp(@NotNull NinSubCommand ninSubCommand)
    {
        MessageUtil.sendCommandHelp(this.toCommandSender(), ninSubCommand);
    }


    @Override
    public void sendPluginInfo(@NotNull JavaPlugin plugin)
    {
        MessageUtil.sendPluginInfo(this.toCommandSender(), plugin);
    }


    public static NcCommandSender fromCommandSender(CommandSender sender)
    {
        return new NcCommandSender(sender);
    }
}