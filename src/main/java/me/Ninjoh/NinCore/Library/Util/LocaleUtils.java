package me.Ninjoh.NinCore.Library.Util;


import me.Ninjoh.NinCore.NinCore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class LocaleUtils
{
    public static void setDefaultLocale(@NotNull Locale locale)
    {
        Locale.setDefault(locale);
    }

    /**
     * Get all available {@link me.Ninjoh.NinCore.Library.Util.MinecraftLocale}'s
     *
     * @return All available {@link me.Ninjoh.NinCore.Library.Util.MinecraftLocale}'s
     */
    @NotNull
    public static MinecraftLocale[] getMinecraftLocales()
    {
        return MinecraftLocale.values();
    }


    /**
     *
     * @param languageTag The language tag to query for. Warning:
     *                    It's in this format, e.g; en_US so not en-US
     * @return The {@link me.Ninjoh.NinCore.Library.Util.MinecraftLocale} for this language tag.
     */
    @Nullable
    public static MinecraftLocale getMinecraftLocale(String languageTag)
    {
        MinecraftLocale locale = null;

        for (MinecraftLocale minecraftLocale : NinCore.getMinecraftLocales())
        {
            if(minecraftLocale.getCode().equals(languageTag))
            {
                locale = minecraftLocale;
            }
        }

        return locale;
    }


    /**
     * Set the default {@link me.Ninjoh.NinCore.Library.Util.MinecraftLocale}.
     *
     * @param languageTag The language tag to query for. Warning:
     *                    It's in this format, e.g; en_US so not en-US
     */
    public static void setDefaultMinecraftLocale(String languageTag)
    {
        for (MinecraftLocale minecraftLocale : NinCore.getMinecraftLocales())
        {
            if(minecraftLocale.getCode().equals(languageTag))
            {
                NinCore.setDefaultMinecraftLocale(minecraftLocale);
            }
        }
    }


    /**
     * Get the default {@link me.Ninjoh.NinCore.Library.Util.MinecraftLocale}.
     *
     * @return The default MinecraftLocale.
     */
    @NotNull
    public static MinecraftLocale getDefaultMinecraftLocale()
    {
        return NinCore.getDefaultMinecraftLocale();
    }

    /**
     * Set if localization should be used.
     *
     * @param value True/False, enable localization?
     */
    public static void setUseLocalization(boolean value)
    {
        NinCore.setUseLocalization(value);
    }


    /**
     * Check if localization is used.
     *
     * @return True if localization is used.
     */
    public static boolean useLocalization()
    {
        return NinCore.useLocalization();
    }
}
