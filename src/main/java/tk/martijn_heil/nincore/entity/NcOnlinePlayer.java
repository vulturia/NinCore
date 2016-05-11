package tk.martijn_heil.nincore.entity;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.martijn_heil.nincore.NcCore;
import tk.martijn_heil.nincore.api.NinCore;
import tk.martijn_heil.nincore.api.command.NinCommand;
import tk.martijn_heil.nincore.api.command.NinSubCommand;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;
import tk.martijn_heil.nincore.api.localization.MinecraftLocale;
import tk.martijn_heil.nincore.api.messaging.MessageColor;
import tk.martijn_heil.nincore.api.util.MessageUtil;
import tk.martijn_heil.nincore.api.util.TextUtils;
import tk.martijn_heil.nincore.api.util.TranslationUtils;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

public class NcOnlinePlayer extends NcOfflinePlayer implements NinOnlinePlayer
{
    private Player player;


    /**
     * Constructor
     *
     * @param p The player
     */
    public NcOnlinePlayer(@NotNull Player p)
    {
        super(p);
        this.player = p;
        //this.ninCommandSender = NinCommandSender.fromCommandSender(p);
    }


    /**
     * Get the bukkit player.
     *
     * @return bukkit player.
     */
    @Override
    public Player toPlayer()
    {
        return this.player;
    }


    @Nullable
    public static NcOnlinePlayer fromUUID(@NotNull UUID uuid)
    {
        Preconditions.checkNotNull(uuid);

        Player p = Bukkit.getServer().getPlayer(uuid);

        if (p == null) return null;


        return new NcOnlinePlayer(p);
    }


    public static NcOnlinePlayer fromPlayer(@NotNull Player p)
    {
        Preconditions.checkNotNull(p);
        return NcCore.getInstance().getEntityManager().getNinOnlinePlayer(p);
    }


    @NotNull
    @Override
    public CommandSender toCommandSender()
    {
        return this.player;
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
        Locale locale = NinOnlinePlayer.fromPlayer(player).getLocale();

        String msg = TranslationUtils.transWithArgs(ResourceBundle.getBundle("me.ninjoh.nincore.res.messages", locale), new Object[]{error}, "sendError");
        this.player.sendMessage(prefix + msg);
    }


    @Override
    public void sendMessage(@NotNull MessageColor messageColor, @NotNull String message, @NotNull Plugin plugin)
    {
        Preconditions.checkNotNull(messageColor);
        Preconditions.checkNotNull(message);
        Preconditions.checkNotNull(plugin);

        String result = ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + plugin.getName() + ChatColor.GOLD + "] " + ChatColor.RESET +
                messageColor + TextUtils.appendChatColorsToChatColorReset(message, messageColor.toChatColor());
        this.player.sendMessage(result);
    }


    @Override
    public void sendMessage(@NotNull MessageColor messageColor, @NotNull String message, @NotNull String prefix)
    {
        Preconditions.checkNotNull(messageColor);
        Preconditions.checkNotNull(message);
        Preconditions.checkNotNull(prefix);

        String result = ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + prefix + ChatColor.GOLD + "] " + ChatColor.RESET +
                messageColor + TextUtils.appendChatColorsToChatColorReset(message, messageColor.toChatColor());
        this.player.sendMessage(result);
    }


    @Override
    public void sendMessage(@NotNull String s)
    {
        this.player.sendMessage(s);
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


    @Override
    public MinecraftLocale getMinecraftLocale()
    {
        try
        {
            MinecraftLocale locale;
            try
            {
                locale = MinecraftLocale.fromLanguageTag(player.spigot().getLocale());
                if (locale == null)
                {
                    locale = NinCore.get().getLocalizationManager().getDefaultMinecraftLocale();
                }
            }
            catch (Exception e)
            {
                return MinecraftLocale.fromLanguageTag(((CraftPlayer) player).getHandle().locale);
            }


            return locale;
        }
        catch (Exception e)
        {
            NcCore.getInstance().getNinLogger().warning("Could not get player's locale, returning default MinecraftLocale.");
            NcCore.getInstance().getNinLogger().fine(ExceptionUtils.getFullStackTrace(e));
            return NcCore.getInstance().getLocalizationManager().getDefaultMinecraftLocale();
        }
    }
}
