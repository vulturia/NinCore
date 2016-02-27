package me.Ninjoh.NinCore.command;


import org.bukkit.ChatColor;

public enum ArgumentColor
{
    OPTIONAL(ChatColor.GREEN),
    REQUIRED(ChatColor.YELLOW);


    private ChatColor color;

    ArgumentColor(ChatColor color)
    {
        this.color = color;
    }
}
