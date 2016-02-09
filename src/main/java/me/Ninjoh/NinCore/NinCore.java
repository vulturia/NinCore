package me.Ninjoh.NinCore;


import me.Ninjoh.NinCore.Library.Util.MinecraftLocale;
import org.jetbrains.annotations.NotNull;

public class NinCore
{
    private static MinecraftLocale[] minecraftLocales = MinecraftLocale.values();
    @NotNull private static MinecraftLocale defaultMinecraftLocale = MinecraftLocale.BRITISH_ENGLISH;
    private static boolean useLocalization = true;

    public static MinecraftLocale[] getMinecraftLocales()
    {
        return minecraftLocales;
    }

    @NotNull
    public static MinecraftLocale getDefaultMinecraftLocale()
    {
        return defaultMinecraftLocale;
    }

    public static void setDefaultMinecraftLocale(MinecraftLocale minecraftLocale)
    {
        defaultMinecraftLocale = minecraftLocale;
    }


    public static boolean useLocalization()
    {
        return useLocalization;
    }


    public static void setUseLocalization(boolean value)
    {
        useLocalization = value;
    }
}
