package me.ninjoh.nincore.watchers;


import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.entity.NinPlayer;
import me.ninjoh.nincore.events.PlayerEndElytraFlightEvent;
import me.ninjoh.nincore.events.PlayerStartElytraFlightEvent;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;

public class PlayerGlideStateWatcher implements Runnable
{
    @Override
    public void run()
    {
        for (NinPlayer np : NinCore.getImplementation().getNinServer().getOnlineNinPlayers())
        {
            EntityPlayer ep = ((CraftPlayer) np.getPlayer()).getHandle();

            // EntityPlayer#cB() is the player glide state (elytra flight). true if gliding.
            if(np.isFlyingUsingElytra() != ep.cB()) // Player's glide state has changed.
            {
                if(!ep.cB()) // Player has stopped elytra flight.
                {
                    Bukkit.getPluginManager().callEvent(new PlayerStartElytraFlightEvent(np.getPlayer()));
                }
                else // Player has started elytra flight.
                {
                    Bukkit.getPluginManager().callEvent(new PlayerEndElytraFlightEvent(np.getPlayer()));
                }
            }
        }
    }
}
