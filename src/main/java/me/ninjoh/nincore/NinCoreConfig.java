package me.ninjoh.nincore;


import lombok.Getter;
import lombok.Setter;
import me.ninjoh.nincore.api.MinecraftLocale;
import org.bukkit.configuration.file.FileConfiguration;

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
