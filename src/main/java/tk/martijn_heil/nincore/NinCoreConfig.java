package tk.martijn_heil.nincore;


import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import tk.martijn_heil.nincore.api.localization.MinecraftLocale;

public class NinCoreConfig
{
    @Getter         private FileConfiguration config;

    @Getter @Setter private MinecraftLocale defaultMinecraftLocale;
    @Getter @Setter private boolean defaultMinecraftLocaleUpdatable;
    @Getter @Setter private boolean coloredLoggingEnabled;
    @Getter @Setter private boolean localizationEnabled;


    public NinCoreConfig(FileConfiguration config)
    {
        this.config = config;

        defaultMinecraftLocale = MinecraftLocale.valueOf(config.getString("localization.minecraftLocales.defaultMinecraftLocale"));
        defaultMinecraftLocaleUpdatable = config.getBoolean("localization.minecraftLocales.canBeUpdated");
        coloredLoggingEnabled = config.getBoolean("logging.enableColoredLogging");
        localizationEnabled = config.getBoolean("localization.enabled");
    }
}
