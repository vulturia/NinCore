package me.Ninjoh.NinCore;

import me.Ninjoh.NinCore.Library.MainUUIDAPIHandler;
import me.Ninjoh.NinCore.Library.Util.MinecraftLocale;
import me.Ninjoh.NinCore.Library.Util.UUIDUtils;
import net.mcapi.uuid.UUIDAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public class Main extends JavaPlugin
{
    public static JavaPlugin plugin;
    public static MinecraftLocale[] minecraftLocales;
    public static MinecraftLocale defaultMinecraftLocale;
    public static boolean useLocalization;

    @Override
    public void onEnable() // Fired when the server enables the plugin
    {
        UUIDAPI.setHandler(new MainUUIDAPIHandler());
        UUIDUtils.setHandler(new MainUUIDAPIHandler());

        Locale.setDefault(Locale.UK);
        minecraftLocales = MinecraftLocale.values();
        defaultMinecraftLocale = MinecraftLocale.BRITISH_ENGLISH;

        useLocalization = true;

        plugin = this;
    }


    @Override
    public void onDisable() //Fired when the server stops and disables all plugins
    {

    }
}