package me.ninjoh.nincore.listeners;

import me.ninjoh.nincore.NinCoreOld;
import me.ninjoh.nincore.player.NCNinPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;


public class PlayerListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(@NotNull PlayerJoinEvent e)
    {
        NinCoreOld.getNCNinServer().addNinOnlinePlayer(NCNinPlayer.fromPlayer(e.getPlayer()));
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(@NotNull PlayerQuitEvent e)
    {
        NinCoreOld.getNCNinServer().removeNinOnlinePlayer(NCNinPlayer.fromPlayer(e.getPlayer()));
    }
}