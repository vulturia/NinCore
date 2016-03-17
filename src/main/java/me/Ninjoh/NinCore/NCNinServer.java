package me.ninjoh.nincore;


import me.ninjoh.nincore.api.NinServer;
import me.ninjoh.nincore.api.entity.NinPlayer;
import me.ninjoh.nincore.player.NCNinPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class NCNinServer implements NinServer
{
    private List<NinPlayer> ninPlayers;




    public NCNinServer(List<NinPlayer> ninPlayers)
    {
        this.ninPlayers = ninPlayers;
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

    @Override
    public Server getServer()
    {
        return Bukkit.getServer();
    }


    /**
     * Dispatch a command from console.
     *
     * @param command The command string to send.
     */
    @Override
    public void dispatchCommand(String command)
    {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }


    /**
     * Get an entity by it's entity ID.
     *
     * @param id The entity ID to search the related entity for.
     * @return The entity, if no entity was found, null will be returned.
     */
    @Override
    public Entity getEntityById(int id)
    {
        Entity e = null;

        for (World w : Bukkit.getWorlds())
        {
            for (Entity entity : w.getEntities())
            {
                if(entity.getEntityId() == id) // Found the entity!
                {
                    e = entity;
                    break; // Stop searching any further
                }
            }

            // We found the entity!
            if(e != null)
            {
                break; // Stop searching any further
            }
        }

        return e; // Will be null if we didn't find any entity with that entity ID.
    }


    @Override
    public World getDefaultWorld()
    {
        return Bukkit.getWorlds().get(0);
    }
}
