package me.Ninjoh.NinCore;

import me.Ninjoh.NinCore.Library.MainUUIDAPIHandler;
import me.Ninjoh.NinCore.Library.Util.UUIDUtils;
import net.mcapi.uuid.UUIDAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    public static JavaPlugin plugin;

    @Override
    public void onEnable() // Fired when the server enables the plugin
    {
        UUIDAPI.setHandler(new MainUUIDAPIHandler());
        UUIDUtils.setHandler(new MainUUIDAPIHandler());

        plugin = this;
    }

    @Override
    public void onDisable() //Fired when the server stops and disables all plugins
    {

    }
}