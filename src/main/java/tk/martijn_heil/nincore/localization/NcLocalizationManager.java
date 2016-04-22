package tk.martijn_heil.nincore.localization;


import lombok.Getter;
import lombok.Setter;
import tk.martijn_heil.nincore.NcCore;
import org.jetbrains.annotations.NotNull;
import tk.martijn_heil.nincore.api.localization.LocalizationManager;
import tk.martijn_heil.nincore.api.localization.MinecraftLocale;
import tk.martijn_heil.nincore.api.logging.LogColor;

public class NcLocalizationManager implements LocalizationManager
{
    @Getter @Setter private boolean localizationEnabled;
    @Getter         private MinecraftLocale defaultMinecraftLocale;

    public NcLocalizationManager()
    {
        this.defaultMinecraftLocale = NcCore.getInstance().getNinCoreConfig().getDefaultMinecraftLocale();

        NcCore.getInstance().getNinLogger().info("Default MinecraftLocale set to: " + LogColor.HIGHLIGHT +
                defaultMinecraftLocale.name() + " (" + defaultMinecraftLocale.toLanguageTag() + ", " +
                defaultMinecraftLocale.getDisplayName(MinecraftLocale.BRITISH_ENGLISH) + ")");

        localizationEnabled = NcCore.getInstance().getNinCoreConfig().isLocalizationEnabled();
        if (localizationEnabled)
        {
            NcCore.getInstance().getNinLogger().info("Localization is " + LogColor.HIGHLIGHT + "enabled" + LogColor.RESET + ".");
        }
        else
        {
            NcCore.getInstance().getNinLogger().info("Localization is " + LogColor.HIGHLIGHT + "enabled.");
        }
    }

    @Override
    public void setDefaultMinecraftLocale(@NotNull MinecraftLocale minecraftLocale)
    {
        if(!NcCore.getInstance().getNinCoreConfig().isDefaultMinecraftLocaleUpdatable())
        {
            NcCore.getInstance().getNinLogger().warning("Could not update the default MinecraftLocale due to updating the default" +
                    " MinecraftLocale being disabled in the configuration file.");
        }
        else
        {
            NcCore.getInstance().getNinLogger().info("Default MinecraftLocale changed to " + defaultMinecraftLocale.name() + " (" +
                    defaultMinecraftLocale.toLanguageTag() + ", " +
                    defaultMinecraftLocale.getDisplayName(MinecraftLocale.BRITISH_ENGLISH) + ")");
            this.defaultMinecraftLocale = minecraftLocale;
        }
    }
}
