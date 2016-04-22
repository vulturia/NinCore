package tk.martijn_heil.nincore.entity;


import tk.martijn_heil.nincore.NcCore;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.martijn_heil.nincore.api.Core;
import tk.martijn_heil.nincore.api.CoreModule;
import tk.martijn_heil.nincore.api.EntityManager;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;

import java.util.ArrayList;
import java.util.List;

public class NcEntityManager extends CoreModule implements EntityManager, Listener
{
    private List<NinOnlinePlayer> ninOnlinePlayers = new ArrayList<>();


    public NcEntityManager(Core core)
    {
        super(core);

        this.getLogger().fine("Adding all currently online players to the NinOnlinePlayers cache..");
        Bukkit.getOnlinePlayers().forEach((player) -> ninOnlinePlayers.add(new NcOnlinePlayer(player)));
    }


    /**
     * Returns a copy of the NinOnlinePlayers cache.
     *
     * @return A copy of the NinOnlinePlayers cache.
     */
    @Override
    @NotNull
    public List<NinOnlinePlayer> getNinOnlinePlayers()
    {
        return new ArrayList<>(ninOnlinePlayers);
    }


    /**
     * Get a player from the NinOnlinePlayers cache.
     *
     * @param player The related player.
     * @return The NinOnlinePlayer if one is found, else it will return null.
     */
    @Nullable
    @Override
    public NcOnlinePlayer getNinOnlinePlayer(@NotNull Player player)
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
    @NotNull
    public NcCommandSender getNinCommandSender(@NotNull CommandSender commandSender)
    {
        return new NcCommandSender(commandSender);
    }


    @Override
    @NotNull
    public NcConsoleCommandSender getNinConsoleCommandSender()
    {
        return new NcConsoleCommandSender();
    }



    @Override
    public NcOfflinePlayer getNinOfflinePlayer(@NotNull OfflinePlayer offlinePlayer)
    {
        return NcOfflinePlayer.fromOfflinePlayer(offlinePlayer);
    }


    private void addNinOnlinePlayer(NcOnlinePlayer p)
    {
        ninOnlinePlayers.add(p);
    }


    private void removeNinOnlinePlayer(NcOnlinePlayer p)
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
