package me.Ninjoh.NinCore.Library.Util;


import me.Ninjoh.NinCore.Library.Entity.Command;
import me.Ninjoh.NinCore.Library.Entity.NinOnlinePlayer;
import me.Ninjoh.NinCore.Library.Entity.SubCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtil
{
    /**
     * Send a error message to the sender.
     * Error format: &cError: &4%error%.
     *
     * @param sender CommandSender to send the error.
     * @param error The error string to send.
     */
    public static void sendError(@NotNull CommandSender sender, @NotNull String error)
    {
        // Make first character of error string uppercase.
        String finalErrStr = error.substring(0, 1).toUpperCase() + error.substring(1);

        sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + finalErrStr + ".");
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
        if(sender instanceof Player)
        {
            locale = NinOnlinePlayer.fromUUID(((Player) sender).getUniqueId()).getMinecraftLocale().toLocale();
        }

        if(locale == null)
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
        if(plugin.getDescription().getAuthors() != null)
        {
            final String authorsString = StringUtils.join(plugin.getDescription().getAuthors(), ", ");
            Object[] messageArguments1 = {authorsString};

            if(plugin.getDescription().getAuthors().size() > 1)
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
        if(plugin.getDescription().getDescription() != null)
        {
            Object[] messageArguments2 = {plugin.getDescription().getDescription()};
            formatter.applyPattern(messages.getString("pluginInfo.description"));
            description = formatter.format(messageArguments2);
        }

        // Plugin website
        if(plugin.getDescription().getWebsite() != null)
        {
            Object[] messageArguments3 = {plugin.getDescription().getWebsite()};
            formatter.applyPattern(messages.getString("pluginInfo.website"));
            website = formatter.format(messageArguments3);
        }

        // Plugin version
        Object[] messageArguments2 = {plugin.getDescription().getVersion()};
        formatter.applyPattern(messages.getString("pluginInfo.version"));
        final String version = formatter.format(messageArguments2);


        sender.sendMessage("");
        sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6" + plugin.getDescription().getName() + " §8] -= [ §b§l+ §8]=-");
        sender.sendMessage("");


        sender.sendMessage(" " + version); // Plugin version

        if(plugin.getDescription().getAuthors() != null) {sender.sendMessage(" " + authors);} // Plugin authors

        if(plugin.getDescription().getDescription() != null) {sender.sendMessage(" " + description);} // Plugin description

        if(plugin.getDescription().getWebsite() != null) {sender.sendMessage(" " + website);} // Plugin website


        sender.sendMessage("");
        sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6" + plugin.getDescription().getName() + " §8] -= [ §b§l+ §8]=-");
        sender.sendMessage("");
    }


    /**
     * Send command help to a commandSender.
     *
     * @param sender The commandSender to send the command help to.
     * @param cmd The command to send help about.
     */
    public static void sendCommandHelp(@NotNull CommandSender sender, @NotNull Command cmd)
    {
        Locale locale = null;
        if(sender instanceof Player)
        {
            locale = NinOnlinePlayer.fromUUID(((Player) sender).getUniqueId()).getMinecraftLocale().toLocale();
        }

        if(locale == null)
        {
            locale = LocaleUtils.getDefaultMinecraftLocale().toLocale();
        }


        ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);


        Object[] messageArguments = {cmd.getName().toLowerCase()};

        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);

        formatter.applyPattern(messages.getString("commandHelp.CommandHelpFor"));
        final String commandHelpFor = formatter.format(messageArguments);





        sender.sendMessage("");
        sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6" + commandHelpFor + "§8] -= [ §b§l+ §8]=-");
        sender.sendMessage("");

        final String finalCmdAliases = StringUtils.join(cmd.getAliasesWithMainCmd(), ",");

        if(cmd.hasSubCommands())
        {
            for (SubCommand subCmd : cmd.getSubCommands())
            {
                final String finalSubCmdAliases = StringUtils.join(subCmd.getAliasesWithMainSubCmd(), ",");


                String description = subCmd.getDescription(locale);

                if(description == null)
                {
                    description = "";
                }


                if(subCmd.getUsage() == null)
                {
                    if(subCmd.requiresPermission() && !sender.hasPermission(subCmd.getRequiredPermission()))
                    {
                        sender.sendMessage(" §e/" + finalCmdAliases + " " + ChatColor.RED +
                                finalSubCmdAliases + " §f- §7" + description);
                    }
                    else
                    {
                        sender.sendMessage(" §e/" + finalCmdAliases + " " + finalSubCmdAliases +
                                " §f- §7" + description);
                    }
                }
                else
                {
                    if(subCmd.requiresPermission() && !sender.hasPermission(subCmd.getRequiredPermission()))
                    {
                        sender.sendMessage(" §e/" + finalCmdAliases + " " + ChatColor.RED +
                                finalSubCmdAliases + " §a" + subCmd.getUsage() +
                                " §f- §7" + description);
                    }
                    else
                    {
                        sender.sendMessage(" §e/" + finalCmdAliases + " " + finalSubCmdAliases + " §a" +
                                subCmd.getUsage() + " §f- §7" + description);
                    }
                }
            }
        }
        else
        {
            Object[] messageArguments1 = {cmd.getName().toLowerCase()};
            formatter.applyPattern(messages.getString("commandHelp.Syntax"));
            final String syntax = formatter.format(messageArguments1);

            Object[] messageArguments2 = {cmd.getName().toLowerCase()};
            formatter.applyPattern(messages.getString("commandHelp.Description"));
            final String description = formatter.format(messageArguments2);



            if(cmd.hasUsage())
            {
                String cmdUsage = cmd.getUsage();
                if(cmdUsage != null)
                {
                    cmdUsage =  cmdUsage.replaceAll("/<command>", "");
                }
                else
                {
                    cmdUsage = "";
                }

                if(cmd.requiresPermission() && !sender.hasPermission(cmd.getRequiredPermission()))
                {
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + syntax + ": " + ChatColor.RED + "/" +
                            finalCmdAliases + ChatColor.GREEN + "" + cmdUsage);
                }
                else
                {
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + syntax + ": " + ChatColor.YELLOW + "/" +
                            finalCmdAliases + ChatColor.GREEN + "" + cmdUsage);
                }
            }
            else
            {
                sender.sendMessage(ChatColor.LIGHT_PURPLE + syntax + ": " + ChatColor.YELLOW + "/" + finalCmdAliases);
            }

            if(cmd.hasDescription())
            {
                sender.sendMessage("");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + description + ": " + ChatColor.GRAY + cmd.getDescription());
            }
        }

        sender.sendMessage("");
        sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6" + commandHelpFor + "§8] -= [ §b§l+ §8]=-");
        sender.sendMessage("");
    }


    /**
     * Send command help to a commandSender.
     *
     * @param sender The commandSender to send the command help to.
     * @param cmd The parent command of the sub command.
     * @param subCmd The sub command to send help about.
     */
    public static void sendCommandHelp(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull SubCommand subCmd)
    {
        Locale locale = null;
        if(sender instanceof Player)
        {
            locale = NinOnlinePlayer.fromUUID(((Player) sender).getUniqueId()).getMinecraftLocale().toLocale();
        }

        if(locale == null)
        {
            locale = LocaleUtils.getDefaultMinecraftLocale().toLocale();
        }


        ResourceBundle messages = ResourceBundle.getBundle("lang.messages", locale);
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);


        Object[] messageArguments = {cmd.getName().toLowerCase() + " " + subCmd.getName()};
        formatter.applyPattern(messages.getString("commandHelp.CommandHelpFor"));
        final String commandHelpFor = formatter.format(messageArguments);


        final String syntax = messages.getString("commandHelp.Syntax");
        final String description1 = messages.getString("commandHelp.Description");





        sender.sendMessage("");
        sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6" + commandHelpFor + "§8] -= [ §b§l+ §8]=-");
        sender.sendMessage("");

        final String finalSubCmdAliases = StringUtils.join(subCmd.getAliasesWithMainSubCmd(), ",");
        final String finalCmdAliases = StringUtils.join(cmd.getAliasesWithMainCmd(), ",");


        if(subCmd.requiresPermission() && !sender.hasPermission(subCmd.getRequiredPermission()))
        {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + syntax + ": " + ChatColor.YELLOW + "/" +
                    finalCmdAliases + " " + ChatColor.RED + finalSubCmdAliases + " " + ChatColor.GREEN + "" +
                    ((subCmd.getUsage() == null) ? "" : subCmd.getUsage()));
        }
        else
        {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + syntax + ": " + ChatColor.YELLOW + "/" +
                    finalCmdAliases + " " + finalSubCmdAliases + " " + ChatColor.GREEN + "" +
                    ((subCmd.getUsage() == null) ? "" : subCmd.getUsage()));
        }


        if(subCmd.hasDescription())
        {
            String description = subCmd.getDescription(locale);
            if(description == null)
            {
                description = "";
            }

            sender.sendMessage("");
            sender.sendMessage(ChatColor.LIGHT_PURPLE + description1 + ": " + ChatColor.GRAY + description);
        }

        sender.sendMessage("");
        sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6" + commandHelpFor + "§8] -= [ §b§l+ §8]=-");
        sender.sendMessage("");
    }
}
