package me.ninjoh.nincore.entity;

import com.google.common.base.Preconditions;
import lombok.experimental.Delegate;
import me.ninjoh.nincore.NcCore;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.Nullable;
import me.ninjoh.nincore.api.entity.NinCommandSender;
import me.ninjoh.nincore.api.entity.NinOnlinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NcOnlinePlayer extends NcOfflinePlayer implements NinOnlinePlayer
{
    private Player player;

    @Delegate
    private NinCommandSender ninCommandSender;


    /**
     * Constructor
     *
     * @param p The player
     */
    public NcOnlinePlayer(@NotNull Player p)
    {
        super(Bukkit.getOfflinePlayer(p.getUniqueId()));
        this.player = p;
        this.ninCommandSender = NinCommandSender.fromCommandSender(p);
    }


    /**
     * Get the bukkit player.
     *
     * @return bukkit player.
     */
    @Override
    public Player toPlayer()
    {
        return this.player;
    }


    @Nullable
    public static NcOnlinePlayer fromUUID(@NotNull UUID uuid)
    {
        Preconditions.checkNotNull(uuid);

        Player p = Bukkit.getServer().getPlayer(uuid);

        if (p == null) return null;


        return new NcOnlinePlayer(p);
    }


    public static NcOnlinePlayer fromPlayer(@NotNull Player p)
    {
        return NcCore.getInstance().getEntityManager().getNinOnlinePlayer(p);
    }
}
