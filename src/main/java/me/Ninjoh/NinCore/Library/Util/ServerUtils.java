package me.Ninjoh.NinCore.Library.Util;


import org.bukkit.Bukkit;

public class ServerUtils
{
    /**
     * Dispatch a command from console.
     *
     * @param command The command string to send.
     */
    public static void dispatchCommand(String command)
    {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
