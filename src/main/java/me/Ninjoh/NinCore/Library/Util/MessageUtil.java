package me.Ninjoh.NinCore.Library.Util;


import me.Ninjoh.NinCore.Library.Entity.Command;
import me.Ninjoh.NinCore.Library.Entity.SubCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MessageUtil
{
    /**
     * Send a error message to the sender.
     * Error format: &cError: &4%error%.
     *
     * @param sender CommandSender to send the error.
     * @param error The error string to send.
     */
    public static void sendError(CommandSender sender, String error)
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
    public static void sendPluginInfo(CommandSender sender, JavaPlugin plugin)
    {
        String version = plugin.getDescription().getVersion();
        List authors = plugin.getDescription().getAuthors();

        sender.sendMessage("");
        sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6" + plugin.getDescription().getName() + " §8] -= [ §b§l+ §8]=-");
        sender.sendMessage("");


        sender.sendMessage(" §eVersion: §7" + version);

        if(plugin.getDescription().getAuthors() != null)
        {
            sender.sendMessage(" §e" + ((plugin.getDescription().getAuthors().size() > 1) ? "Authors" : "Author") + ": §7" +
                    StringUtils.join(authors, ", "));
        }


        if(plugin.getDescription().getDescription() != null)
        {
            sender.sendMessage(" §eVersion: §7" + plugin.getDescription().getDescription());
        }


        if(plugin.getDescription().getWebsite() != null)
        {
            sender.sendMessage(" §eWebsite: §7" + plugin.getDescription().getWebsite());
        }


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
    public static void sendCommandHelp(CommandSender sender, Command cmd)
    {
        sender.sendMessage("");
        sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6Help For Command: '" + cmd.getName().toLowerCase() + "' §8] -= [ §b§l+ §8]=-");
        sender.sendMessage("");

        final String finalCmdAliases = StringUtils.join(cmd.getAliasesWithMainCmd(), ",");

        if(cmd.hasSubCommands())
        {
            for (SubCommand subCmd : cmd.getSubCommands())
            {
                final String finalSubCmdAliases = StringUtils.join(subCmd.getAliasesWithMainSubCmd(), ",");

                if(subCmd.getUsage() == null)
                {
                    if(subCmd.requiresPermission() && !sender.hasPermission(subCmd.getRequiredPermission()))
                    {
                        sender.sendMessage(" §e/" + finalCmdAliases + " " + ChatColor.RED +
                                finalSubCmdAliases + " §f- §7" + subCmd.getDescription());
                    }
                    else
                    {
                        sender.sendMessage(" §e/" + finalCmdAliases + " " + finalSubCmdAliases +
                                " §f- §7" + subCmd.getDescription());
                    }
                }
                else
                {
                    if(subCmd.requiresPermission() && !sender.hasPermission(subCmd.getRequiredPermission()))
                    {
                        sender.sendMessage(" §e/" + finalCmdAliases + " " + ChatColor.RED +
                                finalSubCmdAliases + " §a" + subCmd.getUsage() +
                                " §f- §7" + subCmd.getDescription());
                    }
                    else
                    {
                        sender.sendMessage(" §e/" + finalCmdAliases + " " + finalSubCmdAliases + " §a" +
                                subCmd.getUsage() + " §f- §7" + subCmd.getDescription());
                    }
                }
            }
        }
        else
        {
            if(cmd.hasUsage())
            {
                String cmdUsage = cmd.getUsage();
                final String finalCmdUsage =  cmdUsage.replaceAll("/<command>", "");

                if(cmd.requiresPermission() && !sender.hasPermission(cmd.getRequiredPermission()))
                {
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "Syntax: " + ChatColor.RED + "/" +
                            finalCmdAliases + ChatColor.GREEN + "" + finalCmdUsage);
                }
                else
                {
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "Syntax: " + ChatColor.YELLOW + "/" +
                            finalCmdAliases + ChatColor.GREEN + "" + finalCmdUsage);
                }
            }
            else
            {
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Syntax: " + ChatColor.YELLOW + "/" + finalCmdAliases);
            }

            if(cmd.hasDescription())
            {
                sender.sendMessage("");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Description: " + ChatColor.GRAY + cmd.getDescription());
            }
        }

        sender.sendMessage("");
        sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6Help For Command: '" + cmd.getName().toLowerCase() + "' §8] -= [ §b§l+ §8]=-");
        sender.sendMessage("");
    }


    /**
     * Send command help to a commandSender.
     *
     * @param sender The commandSender to send the command help to.
     * @param cmd The parent command of the sub command.
     * @param subCmd The sub command to send help about.
     */
    public static void sendCommandHelp(CommandSender sender, Command cmd, SubCommand subCmd)
    {
        sender.sendMessage("");
        sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6Help For Command: '" + cmd.getName().toLowerCase() + " " + subCmd.getName() + "' §8] -= [ §b§l+ §8]=-");
        sender.sendMessage("");

        final String finalSubCmdAliases = StringUtils.join(subCmd.getAliasesWithMainSubCmd(), ",");
        final String finalCmdAliases = StringUtils.join(cmd.getAliasesWithMainCmd(), ",");


        if(subCmd.requiresPermission() && !sender.hasPermission(subCmd.getRequiredPermission()))
        {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Syntax: " + ChatColor.YELLOW + "/" +
                    finalCmdAliases + " " + ChatColor.RED + finalSubCmdAliases + " " + ChatColor.GREEN + "" +
                    ((subCmd.getUsage() == null) ? "" : subCmd.getUsage()));
        }
        else
        {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Syntax: " + ChatColor.YELLOW + "/" +
                    finalCmdAliases + " " + finalSubCmdAliases + " " + ChatColor.GREEN + "" +
                    ((subCmd.getUsage() == null) ? "" : subCmd.getUsage()));
        }


        if(subCmd.hasDescription())
        {
            sender.sendMessage("");
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Description: " + ChatColor.GRAY + subCmd.getDescription());
        }

        sender.sendMessage("");
        sender.sendMessage("§8-=[ §b§l+ §8]=- §8[ §6Help For Command: '" + cmd.getName().toLowerCase() + " " + subCmd.getName() + "' §8] -= [ §b§l+ §8]=-");
        sender.sendMessage("");
    }
}
