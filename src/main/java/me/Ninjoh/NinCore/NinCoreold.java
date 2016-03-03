package me.ninjoh.nincore;


import me.ninjoh.nincore.player.NCNinPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NinCoreOld
{
    protected static final NCMinecraftLocale[] NC_MINECRAFT_LOCALEs = NCMinecraftLocale.values();
    @NotNull protected static NCMinecraftLocale defaultNCMinecraftLocale = NCMinecraftLocale.BRITISH_ENGLISH;
    protected static boolean useLocalization = true;
    protected static JavaPlugin plugin;
    protected static String version;


    protected static NCNinServer NCNinServer;


    static
    {
        List<NCNinPlayer> NCNinPlayers = new ArrayList<>();

        if((Bukkit.getServer().getOnlinePlayers() != null) && (!Bukkit.getServer().getOnlinePlayers().isEmpty()))
        {
            for (Player p : Bukkit.getOnlinePlayers())
            {
                NCNinPlayers.add(new NCNinPlayer(p.getUniqueId()));
            }
        }

        NCNinServer = new NCNinServer(NCNinPlayers);
    }



    public static JavaPlugin getPlugin()
    {
        return NinCoreOld.plugin;
    }


    /**
     * Get all {@link NCMinecraftLocale}'s.
     *
     * @deprecated Use
     * @return All {@link NCMinecraftLocale}'s
     */
    @Deprecated
    public static NCMinecraftLocale[] getNC_MINECRAFT_LOCALEs()
    {
        return NC_MINECRAFT_LOCALEs;
    }


    /**
     * Get the default {@link NCMinecraftLocale}.
     *
     * @return the default {@link NCMinecraftLocale}.
     */
    @NotNull
    public static NCMinecraftLocale getDefaultNCMinecraftLocale()
    {
        return defaultNCMinecraftLocale;
    }


    /**
     * Set the default {@link NCMinecraftLocale}.
     *
     * @param NCMinecraftLocale The default {@link NCMinecraftLocale}.
     */
    public static void setDefaultNCMinecraftLocale(@NotNull NCMinecraftLocale NCMinecraftLocale)
    {
        defaultNCMinecraftLocale = NCMinecraftLocale;
    }


    /**
     * Check if using localization is enabled.
     *
     * @return True/False, is localization enabled?
     */
    public static boolean useLocalization()
    {
        return useLocalization;
    }

    /**
     * Set if localization should be used.
     *
     * @param value True/False, Should localization be used?
     */
    public static void setUseLocalization(boolean value)
    {
        useLocalization = value;
    }


    public static String getVersion()
    {
        return NinCoreOld.version;
    }


    public static NCNinServer getNCNinServer()
    {
        return NCNinServer;
    }
}
