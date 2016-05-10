package tk.martijn_heil.nincore.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.martijn_heil.nincore.api.entity.NinOfflinePlayer;

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


    public NcOfflinePlayer(@NotNull OfflinePlayer offlinePlayer)
    {
        Preconditions.checkNotNull(offlinePlayer, "offlinePlayer can not  be null.");
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


    @Contract("_ -> !null")
    public static NcOfflinePlayer fromOfflinePlayer(OfflinePlayer offlinePlayer)
    {
        return new NcOfflinePlayer(offlinePlayer);
    }


    @NotNull
    public OfflinePlayer toOfflinePlayer()
    {
        return offlinePlayer;
    }
}
