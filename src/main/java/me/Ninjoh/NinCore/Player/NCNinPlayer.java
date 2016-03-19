package me.ninjoh.nincore.player;

import lombok.experimental.Delegate;
import me.ninjoh.nincore.api.NinCommandSender;
import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.Nullable;
import me.ninjoh.nincore.api.entity.NinPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NCNinPlayer extends NCNinOfflinePlayer implements NinPlayer
{
    private Player player;

    @Delegate
    private NinCommandSender ninCommandSender;


    /**
     * Constructor
     *
     * @param p The player
     */
    public NCNinPlayer(Player p)
    {
        super(Bukkit.getOfflinePlayer(p.getUniqueId()));
        this.player = p;
        this.ninCommandSender = NinCore.get().getNinCommandSender(p);
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
    public static NCNinPlayer fromUUID(UUID uuid)
    {
        Player p = Bukkit.getServer().getPlayer(uuid);

        if (p == null) return null;


        return new NCNinPlayer(p);
    }


    public static NCNinPlayer fromPlayer(Player p)
    {
        return new NCNinPlayer(p);
    }
}
