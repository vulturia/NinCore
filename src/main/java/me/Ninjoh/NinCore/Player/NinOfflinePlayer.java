package me.Ninjoh.NinCore.player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;


public class NinOfflinePlayer
{
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


    public NinOfflinePlayer(UUID uuid)
    {
        this.uuid = uuid;
    }


    @NotNull
    public static NinOfflinePlayer fromUUID(UUID uuid)
    {
        return new NinOfflinePlayer(uuid);
    }


    public OfflinePlayer getOfflinePlayer()
    {
        return Bukkit.getServer().getOfflinePlayer(uuid);
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
