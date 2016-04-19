package me.ninjoh.nincore.entity;

import me.ninjoh.nincore.api.common.org.jetbrains.annotations.Nullable;
import me.ninjoh.nincore.api.entity.NinOfflinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;


public class NcOfflinePlayer implements NinOfflinePlayer
{
    private OfflinePlayer offlinePlayer;


    /**
     * Get the player's UUID.
     *
     * @return the player's UUID.
     */
    public UUID getUuid()
    {
        return offlinePlayer.getUniqueId();
    }


    public NcOfflinePlayer(OfflinePlayer offlinePlayer)
    {
        this.offlinePlayer = offlinePlayer;
    }


    @Nullable
    public static NcOfflinePlayer fromUUID(UUID uuid)
    {
        OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(uuid);

        if (offlinePlayer == null)
        {
            return null;
        }

        return new NcOfflinePlayer(offlinePlayer);
    }


    public static NcOfflinePlayer fromOfflinePlayer(OfflinePlayer offlinePlayer)
    {
        return new NcOfflinePlayer(offlinePlayer);
    }


    public OfflinePlayer toOfflinePlayer()
    {
        return offlinePlayer;
    }
}
