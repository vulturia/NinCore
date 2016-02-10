package me.Ninjoh.NinCore;


import me.Ninjoh.NinCore.Library.Util.MinecraftLocale;
import org.jetbrains.annotations.NotNull;

public class NinCore
{
    private static MinecraftLocale[] minecraftLocales = MinecraftLocale.values();
    @NotNull private static MinecraftLocale defaultMinecraftLocale = MinecraftLocale.BRITISH_ENGLISH;
    private static boolean useLocalization = true;


    /**
     * Get all {@link me.Ninjoh.NinCore.Library.Util.MinecraftLocale}'s.
     *
     * @return All {@link me.Ninjoh.NinCore.Library.Util.MinecraftLocale}'s
     */
    public static MinecraftLocale[] getMinecraftLocales()
    {
        return minecraftLocales;
    }


    /**
     * Get the default {@link me.Ninjoh.NinCore.Library.Util.MinecraftLocale}.
     *
     * @return the default {@link me.Ninjoh.NinCore.Library.Util.MinecraftLocale}.
     */
    @NotNull
    public static MinecraftLocale getDefaultMinecraftLocale()
    {
        return defaultMinecraftLocale;
    }


    /**
     * Set the default {@link me.Ninjoh.NinCore.Library.Util.MinecraftLocale}.
     *
     * @param minecraftLocale The default {@link me.Ninjoh.NinCore.Library.Util.MinecraftLocale}.
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
