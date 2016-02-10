package me.Ninjoh.NinCore.Library.Util;


import me.Ninjoh.NinCore.Library.Entity.Command;
import me.Ninjoh.NinCore.Library.Entity.NinOnlinePlayer;
import me.Ninjoh.NinCore.Library.Entity.SubCommand;
import net.md_5.bungee.api.chat.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtil
{
    /**
     * Send a error message to the sender.
     * Error format: &cError: &4%error%.
     *
     * @param sender CommandSender to send the error.
     * @param error  The error string to send.
     */
    public static void sendError(@NotNull CommandSender sender, @NotNull String error)
    {
        // Get target's locale.
        Locale locale = null;
        if (sender instanceof Player)
        {
            locale = NinOnlinePlayer.fromUUID(((Player) sender).getUniqueId()).getMinecraftLocale().toLocale();
        }

        if (locale == null)
        {
            locale = LocaleUtils.getDefaultMinecraftLocale().toLocale();
        }


        ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);

        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);

        // Format message.
        Object[] messageArguments = {error};
        formatter.applyPattern(messages.getString("sendError"));
        final String finalErr = formatter.format(messageArguments);

        // Send message.
        sender.sendMessage(finalErr);
    }


    /**
     * Send plugin info to a player.
     *
     * @param sender The CommandSender of the command.
     * @param plugin The plugin instance to use.
     */
    public static void sendPluginInfo(@NotNull CommandSender sender, @NotNull JavaPlugin plugin)
    {
        Locale locale = null;
        if (sender instanceof Player)
        {
            locale = NinOnlinePlayer.fromUUID(((Player) sender).getUniqueId()).getMinecraftLocale().toLocale();
        }

        if (locale == null)
        {
            locale = LocaleUtils.getDefaultMinecraftLocale().toLocale();
        }


        ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);

        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);

        String authors = null;
        String description = null;
        String website = null;


        // Plugin authors
        if (plugin.getDescription().getAuthors() != null)
        {
            final String authorsString = StringUtils.join(plugin.getDescription().getAuthors(), ", ");
            Object[] messageArguments1 = {authorsString};

            if (plugin.getDescription().getAuthors().size() > 1)
            {
                formatter.applyPattern(messages.getString("pluginInfo.authors.multipleAuthors"));
            }
            else
            {
                formatter.applyPattern(messages.getString("pluginInfo.authors.oneAuthor"));
            }

            authors = formatter.format(messageArguments1);
        }


        // Plugin description
        if (plugin.getDescription().getDescription() != null)
        {
            Object[] messageArguments2 = {plugin.getDescription().getDescription()};
            formatter.applyPattern(messages.getString("pluginInfo.description"));
            description = formatter.format(messageArguments2);
        }

        // Plugin website
        if (plugin.getDescription().getWebsite() != null)
        {
            Object[] messageArguments3 = {plugin.getDescription().getWebsite()};
            formatter.applyPattern(messages.getString("pluginInfo.website"));
            website = formatter.format(messageArguments3);
        }

        // Plugin version
        Object[] messageArguments2 = {plugin.getDescription().getVersion()};
        formatter.applyPattern(messages.getString("pluginInfo.version"));
        final String version = formatter.format(messageArguments2);

        // Old format;
        //sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6" + plugin.getDescription().getName() + " §8] -= [ §b§l+ §8]=-");

        sender.sendMessage("");
        sender.sendMessage("§8-§b=§8-§b=§8<[ §6" + plugin.getDescription().getName() + " §8]>§b=§8-§b=§8-");
        sender.sendMessage("");


        sender.sendMessage(" " + version); // Plugin version

        if (plugin.getDescription().getAuthors() != null)
        {
            sender.sendMessage(" " + authors);
        } // Plugin authors

        if (plugin.getDescription().getDescription() != null)
        {
            sender.sendMessage("\n " + description);
        } // Plugin description

        if (plugin.getDescription().getWebsite() != null)
        {
            sender.sendMessage("\n " + website);
        } // Plugin website


        sender.sendMessage("");
        sender.sendMessage("§8-§b=§8-§b=§8<[ §6" + plugin.getDescription().getName() + " §8]>§b=§8-§b=§8-");
        sender.sendMessage("");
    }


    /**
     * Send command help to a commandSender.
     *
     * @param sender The commandSender to send the command help to.
     * @param cmd    The command to send help about.
     */
    public static void sendCommandHelp(@NotNull CommandSender sender, @NotNull Command cmd)
    {
        Locale locale = null;
        if (sender instanceof Player)
        {
            locale = NinOnlinePlayer.fromUUID(((Player) sender).getUniqueId()).getMinecraftLocale().toLocale();
        }

        if (locale == null)
        {
            locale = LocaleUtils.getDefaultMinecraftLocale().toLocale();
        }


        ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);

        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);

        Object[] messageArguments = {cmd.getName().toLowerCase()};
        formatter.applyPattern(messages.getString("commandHelp.commandHelpFor"));
        final String commandHelpFor = formatter.format(messageArguments);


        sender.sendMessage("");
        sender.sendMessage("§8-§b=§8-§b=§8<[ §6" + commandHelpFor + " §8]>§b=§8-§b=§8-");
        sender.sendMessage("");

        final String finalCmdAliases = StringUtils.join(cmd.getAliasesWithMainCmd(), ",");

        if (cmd.hasSubCommands())
        {
            for (SubCommand subCmd : cmd.getSubCommands())
            {
                final String finalSubCmdAliases = StringUtils.join(subCmd.getAliasesWithMainSubCmd(), ",");
                final String description = subCmd.getDescription(locale);
                final String usage = subCmd.getUsage();


                if(sender instanceof Player) // Use JSON messages for players.
                {
                    Player p = (Player) sender;

                    final Object[] messageArguments2 = {cmd.getDescription()};
                    formatter.applyPattern(messages.getString("commandHelp.description"));
                    final String finalDescription = formatter.format(messageArguments2);


                    final Object[] messageArguments1 =
                            {
                                    "/" + finalCmdAliases + " " + ((subCmd.requiresPermission() &&
                                            !sender.hasPermission(subCmd.getRequiredPermission()))
                                            ? "§c" + finalSubCmdAliases : finalSubCmdAliases) + "§a " +
                                            ((subCmd.getUsage() == null) ? "" : subCmd.getUsage())
                            };

                    formatter.applyPattern(messages.getString("commandHelp.syntax"));
                    final String syntax = formatter.format(messageArguments1);


                    TextComponent message = new TextComponent(TextComponent.fromLegacyText("§e" + finalCmdAliases +
                            ((subCmd.requiresPermission() && !sender.hasPermission(subCmd.getRequiredPermission()))
                            ? " §c" + finalSubCmdAliases : " " + finalSubCmdAliases) +
                            ((description == null) ? "" : " §f- §7" + description)));

                    BaseComponent[] baseComponents = new ComponentBuilder(syntax + "\n\n" + finalDescription ).create();

                    message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, baseComponents) );
                    message.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/" +
                            cmd.getName().toLowerCase() + " " + subCmd.getName() ) );

                    // Send message.
                    p.spigot().sendMessage(message);
                }
                else
                {
                    sender.sendMessage(" §e/" + finalCmdAliases +
                            ((subCmd.requiresPermission() && !sender.hasPermission(subCmd.getRequiredPermission()))
                                    ? " §c" + finalSubCmdAliases : " " + finalSubCmdAliases) +
                            ((usage == null) ? "" : " §a" + usage) +
                            ((description == null) ? "" : " §f- §7" + description));
                }
            }
        }
        else
        {
            String cmdUsage = cmd.getUsage();

            Object[] messageArguments1 =
                    {
                            ((cmd.requiresPermission() && !sender.hasPermission(cmd.getRequiredPermission())) ? "§c" : "§d") +
                                    "/" + finalCmdAliases + ((cmdUsage == null) ? "" : " §a " + cmdUsage)
                    };

            formatter.applyPattern(messages.getString("commandHelp.syntax"));
            final String syntax = formatter.format(messageArguments1);


            // Send command syntax.
            sender.sendMessage(syntax);

            // Send command description if it has one.
            if (cmd.hasDescription())
            {
                Object[] messageArguments2 = {cmd.getDescription()};
                formatter.applyPattern(messages.getString("commandHelp.description"));
                final String description = formatter.format(messageArguments2);


                sender.sendMessage("");
                sender.sendMessage(description);
            }
        }

        sender.sendMessage("");
        sender.sendMessage("§8-§b=§8-§b=§8<[ §6" + commandHelpFor + " §8]>§b=§8-§b=§8-");
        sender.sendMessage("");
    }


    /**
     * Send command help to a commandSender.
     *
     * @param sender The commandSender to send the command help to.
     * @param cmd    The parent command of the sub command.
     * @param subCmd The sub command to send help about.
     */
    public static void sendCommandHelp(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull SubCommand subCmd)
    {
        Locale locale = null;
        if (sender instanceof Player)
        {
            locale = NinOnlinePlayer.fromUUID(((Player) sender).getUniqueId()).getMinecraftLocale().toLocale();
        }

        if (locale == null)
        {
            locale = LocaleUtils.getDefaultMinecraftLocale().toLocale();
        }


        ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);


        Object[] messageArguments = {cmd.getName().toLowerCase() + " " + subCmd.getName()};
        formatter.applyPattern(messages.getString("commandHelp.commandHelpFor"));
        final String commandHelpFor = formatter.format(messageArguments);

        final String finalSubCmdAliases = StringUtils.join(subCmd.getAliasesWithMainSubCmd(), ",");
        final String finalCmdAliases = StringUtils.join(cmd.getAliasesWithMainCmd(), ",");

        Object[] messageArguments2 =
                {
                        "/" + finalCmdAliases + " " + ((subCmd.requiresPermission() &&
                                !sender.hasPermission(subCmd.getRequiredPermission()))
                                ? "§c" + finalSubCmdAliases : finalSubCmdAliases) + "§a " +
                                ((subCmd.getUsage() == null) ? "" : subCmd.getUsage())
                };

        formatter.applyPattern(messages.getString("commandHelp.syntax"));
        final String syntax = formatter.format(messageArguments2);


        sender.sendMessage("");
        sender.sendMessage("§8-§b=§8-§b=§8<[ §6" + commandHelpFor + " §8]>§b=§8-§b=§8-");
        sender.sendMessage("");


        // Send sub command syntax message.
        sender.sendMessage(syntax);


        // Send description if the sub command has one.
        if (subCmd.hasDescription())
        {
            Object[] messageArguments1 = {cmd.getDescription()};
            formatter.applyPattern(messages.getString("commandHelp.description"));
            final String description = formatter.format(messageArguments1);


            sender.sendMessage("");
            sender.sendMessage(description);
        }

        sender.sendMessage("");
        sender.sendMessage("§8-§b=§8-§b=§8<[ §6" + commandHelpFor + " §8]>§b=§8-§b=§8-");
        sender.sendMessage("");
    }
}
