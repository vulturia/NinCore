package me.Ninjoh.NinCore.command;


import org.bukkit.ChatColor;

public enum ArgumentColor
{
    OPTIONAL(ChatColor.GRAY),
    REQUIRED(ChatColor.GREEN);


    private ChatColor color;

    ArgumentColor(ChatColor color)
    {
        this.color = color;
    }

    @Override
    public String toString()
    {
        return this.color.toString();
    }
}
