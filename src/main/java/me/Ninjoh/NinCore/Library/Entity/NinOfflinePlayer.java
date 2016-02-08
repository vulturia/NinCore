package me.Ninjoh.NinCore.Library.Entity;

import me.Ninjoh.NinCore.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;


public class NinOfflinePlayer
{
    private JavaPlugin plugin = Main.plugin;
    public UUID uuid;


    /**
     * Get the player's UUID.
     *
     * @return the player's UUID.
     */
    public UUID getUuid()
    {
        return uuid;
    }


    public NinOfflinePlayer(UUID offlinePlayerUUID)
    {
        uuid = offlinePlayerUUID;
    }


    public OfflinePlayer getOfflinePlayer()
    {
        return plugin.getServer().getOfflinePlayer(uuid);
    }


    @Nullable
    public NinOnlinePlayer getNinOnlinePlayer()
    {
        if(getOfflinePlayer().isOnline())
        {
            return new NinOnlinePlayer(uuid);
        }
        else
        {
            return null;
        }
    }
}
