package me.ninjoh.nincore.util;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

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


    public static OfflinePlayer[] getAllKnownPlayers()
    {
        return Bukkit.getOfflinePlayers();
    }
}
