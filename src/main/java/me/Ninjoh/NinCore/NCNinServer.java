package me.ninjoh.nincore;


import me.ninjoh.nincore.api.NinServer;
import me.ninjoh.nincore.api.entity.NinPlayer;
import me.ninjoh.nincore.player.NCNinPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class NCNinServer implements NinServer
{
    private List<NinPlayer> ninPlayers;




    public NCNinServer(List<NCNinPlayer> NCNinPlayers)
    {
        NCNinPlayers.add(new NCNinPlayer(Bukkit.getPlayer("test")));
    }


    public Collection<NinPlayer> getNCNinPlayers()
    {
        return ninPlayers;
    }


    public void addNinOnlinePlayer(NCNinPlayer p)
    {
        ninPlayers.add(p);
    }


    public void removeNinOnlinePlayer(NCNinPlayer p)
    {
        ninPlayers.remove(p);
    }


    @Override
    public List<NinPlayer> getOnlineNinPlayers()
    {
        return ninPlayers;
    }


    @Override
    public NinPlayer getNinPlayer(Player player)
    {
        // NinPlayers are always online.
        if(!player.isOnline()) return null;


        for (NinPlayer p : ninPlayers)
        {
            if(p.getPlayer().equals(player))
            {
                return p;
            }
        }

        return null; // We should never reach this
    }

    public Server getServer()
    {
        return Bukkit.getServer();
    }
}
