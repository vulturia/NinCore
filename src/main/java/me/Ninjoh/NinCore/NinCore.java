package me.ninjoh.nincore;


import me.ninjoh.nincore.listeners.SecurityListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.jetbrains.annotations.NotNull;

public class NinCore
{
    private static final MinecraftLocale[] minecraftLocales = MinecraftLocale.values();
    @NotNull private static MinecraftLocale defaultMinecraftLocale = MinecraftLocale.BRITISH_ENGLISH;
    private static boolean useLocalization = true;
    private static JavaPlugin plugin;


    public static void init(JavaPlugin plugin)
    {
        AnsiConsole.systemInstall();
        Bukkit.getLogger().info(Ansi.ansi().fg(Ansi.Color.RED).a("nincore init called!").reset().toString());
        NinCore.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(new SecurityListener(), plugin);
    }


    public static JavaPlugin getPlugin()
    {
        return NinCore.plugin;
    }


    /**
     * Get all {@link MinecraftLocale}'s.
     *
     * @return All {@link MinecraftLocale}'s
     */
    public static MinecraftLocale[] getMinecraftLocales()
    {
        return minecraftLocales;
    }


    /**
     * Get the default {@link MinecraftLocale}.
     *
     * @return the default {@link MinecraftLocale}.
     */
    @NotNull
    public static MinecraftLocale getDefaultMinecraftLocale()
    {
        return defaultMinecraftLocale;
    }


    /**
     * Set the default {@link MinecraftLocale}.
     *
     * @param minecraftLocale The default {@link MinecraftLocale}.
     */
    public static void setDefaultMinecraftLocale(@NotNull MinecraftLocale minecraftLocale)
    {
        defaultMinecraftLocale = minecraftLocale;
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
}
