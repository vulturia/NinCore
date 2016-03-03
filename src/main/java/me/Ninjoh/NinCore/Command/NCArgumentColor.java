package me.ninjoh.nincore.command;


import org.bukkit.ChatColor;

public enum NCArgumentColor
{
    OPTIONAL(ChatColor.DARK_GRAY),
    REQUIRED(ChatColor.GREEN);


    private ChatColor color;

    NCArgumentColor(ChatColor color)
    {
        this.color = color;
    }

    @Override
    public String toString()
    {
        return this.color.toString();
    }
}
