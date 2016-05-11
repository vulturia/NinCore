package tk.martijn_heil.nincore.entity;


import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.martijn_heil.nincore.api.Core;
import tk.martijn_heil.nincore.api.CoreModule;
import tk.martijn_heil.nincore.api.EntityManager;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;

import java.util.List;

public class NcEntityManager extends CoreModule implements EntityManager, Listener
{
    public NcEntityManager(Core core)
    {
        super(core);
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
        throw new UnsupportedOperationException();
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
        return new NcOnlinePlayer(player);
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
}
