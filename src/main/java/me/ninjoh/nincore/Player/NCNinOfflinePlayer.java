package me.ninjoh.nincore.player;

import me.ninjoh.nincore.api.NinOfflinePlayer;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;


public class NCNinOfflinePlayer implements NinOfflinePlayer
{
    protected OfflinePlayer offlinePlayer;


    /**
     * Get the player's UUID.
     *
     * @return the player's UUID.
     */
    public UUID getUuid()
    {
        return offlinePlayer.getUniqueId();
    }


    public NCNinOfflinePlayer(OfflinePlayer offlinePlayer)
    {
        this.offlinePlayer = offlinePlayer;
    }


    @Nullable
    public static NCNinOfflinePlayer fromUUID(UUID uuid)
    {
        OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(uuid);

        if (offlinePlayer == null)
        {
            return null;
        }

        return new NCNinOfflinePlayer(offlinePlayer);
    }


    public static NCNinOfflinePlayer fromOfflinePlayer(OfflinePlayer offlinePlayer)
    {
        return new NCNinOfflinePlayer(offlinePlayer);
    }


    public OfflinePlayer toOfflinePlayer()
    {
        return offlinePlayer;
    }
}
