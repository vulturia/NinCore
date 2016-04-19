package me.ninjoh.nincore.listeners;

import me.ninjoh.nincore.NcCore;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.entity.NcOnlinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(@NotNull PlayerJoinEvent e)
    {
        NcCore.getInstance().getEntityManager().addNinOnlinePlayer(new NcOnlinePlayer(e.getPlayer()));


        NcCore.getInstance().getLogger().fine("Added player (" + e.getPlayer().getName() +
                ", " + e.getPlayer().getUniqueId() + ") to the online NinPlayer cache");
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(@NotNull PlayerQuitEvent e)
    {
        NcCore.getInstance().getEntityManager().removeNinOnlinePlayer(NcOnlinePlayer.fromPlayer(e.getPlayer()));

        NcCore.getInstance().getLogger().fine("Removed player (" + e.getPlayer().getName() +
                ", " + e.getPlayer().getUniqueId() + ") from the online NinPlayer cache");
    }
}