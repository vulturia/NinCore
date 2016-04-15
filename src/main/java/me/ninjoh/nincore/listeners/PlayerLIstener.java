package me.ninjoh.nincore.listeners;

import me.ninjoh.nincore.NCNinServer;
import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.player.NCNinPlayer;
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
        if (NinCore.getImplementation().getNinServer() instanceof NCNinServer)
        {
            NinCore.getImplementingPlugin().getLogger().fine("Added player (" + e.getPlayer().getName() +
                    ", " + e.getPlayer().getUniqueId() + ") to the online NinPlayer cache");

            ((NCNinServer) NinCore.getImplementation().getNinServer()).
                    addNinOnlinePlayer(NCNinPlayer.fromPlayer(e.getPlayer()));
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(@NotNull PlayerQuitEvent e)
    {
        if (NinCore.getImplementation().getNinServer() instanceof NCNinServer)
        {
            NinCore.getImplementingPlugin().getLogger().fine("Removed player (" + e.getPlayer().getName() +
                    ", " + e.getPlayer().getUniqueId() + ") from the online NinPlayer cache");

            ((NCNinServer) NinCore.getImplementation().getNinServer()).
                    removeNinOnlinePlayer(NCNinPlayer.fromPlayer(e.getPlayer()));
        }
    }
}