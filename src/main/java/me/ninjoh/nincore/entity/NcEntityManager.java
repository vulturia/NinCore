package me.ninjoh.nincore.entity;


import me.ninjoh.nincore.NcCore;
import me.ninjoh.nincore.api.EntityManager;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.entity.NinOnlinePlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class NcEntityManager implements EntityManager, Listener
{
    private List<NinOnlinePlayer> ninOnlinePlayers;


    @Override
    public List<NinOnlinePlayer> getNinOnlinePlayers()
    {
        return new ArrayList<>(ninOnlinePlayers);
    }


    @Override
    public NcOnlinePlayer getNinOnlinePlayer(Player player)
    {
        // NinPlayers are always online.
        if (!player.isOnline()) return null;


        for (NinOnlinePlayer p : ninOnlinePlayers)
        {
            if (p.toPlayer().equals(player))
            {
                return (NcOnlinePlayer) p;
            }
        }

        return null; // We should never reach this
    }


    @Override
    public NcCommandSender getNinCommandSender(CommandSender commandSender)
    {
        return new NcCommandSender(commandSender);
    }


    @Override
    public NcConsoleCommandSender getNinConsoleCommandSender()
    {
        return new NcConsoleCommandSender();
    }



    @Override
    public NcOfflinePlayer getNinOfflinePlayer(OfflinePlayer offlinePlayer)
    {
        return NcOfflinePlayer.fromOfflinePlayer(offlinePlayer);
    }


    public void addNinOnlinePlayer(NcOnlinePlayer p)
    {
        ninOnlinePlayers.add(p);
    }


    public void removeNinOnlinePlayer(NcOnlinePlayer p)
    {
        ninOnlinePlayers.remove(p);
    }


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
