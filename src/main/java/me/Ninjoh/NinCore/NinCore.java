package me.Ninjoh.NinCore;


import org.jetbrains.annotations.NotNull;

public class NinCore
{
    private static final MinecraftLocale[] minecraftLocales = MinecraftLocale.values();
    @NotNull private static MinecraftLocale defaultMinecraftLocale = MinecraftLocale.BRITISH_ENGLISH;
    private static boolean useLocalization = true;


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
