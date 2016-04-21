package me.ninjoh.nincore;


import lombok.Getter;
import me.ninjoh.nincore.api.*;
import me.ninjoh.nincore.api.command.CommandImplementation;
import me.ninjoh.nincore.api.logging.LogColor;
import me.ninjoh.nincore.command.NcCommandImplementation;
import me.ninjoh.nincore.entity.NcEntityManager;
import me.ninjoh.nincore.localization.NcLocalizationManager;
import me.ninjoh.nincore.modules.ArmorEquipEventModule;
import me.ninjoh.nincore.modules.NinCoreCmdModule;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public class NcCore extends Core implements NinCoreImplementation
{
    @Getter         private static NcCore instance;

    @Getter         private boolean isConsoleAnsiSupported = false;

    @Getter         private NinCoreConfig ninCoreConfig;
    @Getter         private NcEntityManager entityManager;
    @Getter         private LocalizationManager localizationManager;
    @Getter         private CommandImplementation commandImplementation;


    //private static ProtocolManager protocolManager;


    // The order in which things are set up is very important in onLoadInner and onEnableInner!!
    @Override
    public void onLoadInner()
    {
        NinCore.setImplementation(this); // NinPluginLogger is dependant on the NinCore implementation.
        this.getNinLogger().info(this.getDescription().getName() + " v" + this.getDescription().getVersion() +
                " by " + StringUtils.join(this.getDescription().getAuthors(), ", "));

        instance = this;


        this.saveDefaultConfig();
        this.ninCoreConfig = new NinCoreConfig(this.getConfig());

        localizationManager = new NcLocalizationManager(); // Localization manager is dependant on the nincore configuration.



        try
        {
            ConsoleReader consoleReader = (ConsoleReader) Bukkit.getServer().getClass().getMethod("getReader").invoke(Bukkit.getServer());

            isConsoleAnsiSupported = consoleReader.getTerminal().isAnsiSupported();
        }
        catch (ClassCastException | NullPointerException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            this.getNinLogger().warning(String.format("Could not use colored logging because a %s occurred whilst" +
                    " trying to check if the terminal is ANSI supported;", e.getClass().getName()));
            this.getNinLogger().fine(ExceptionUtils.getFullStackTrace(e));
        }
    }


    // The order in which things are set up is very important in onLoadInner and onEnableInner!!
    @Override
    public void onEnableInner()
    {
        if (this.getNinCoreConfig().isColoredLoggingEnabled())
        {
            this.getNinLogger().info("Colored logging is " + LogColor.HIGHLIGHT + "enabled" + LogColor.RESET + ".");
        }
        else
        {
            this.getNinLogger().info("Colored logging is " + LogColor.HIGHLIGHT + "disabled" + LogColor.RESET + ".");
        }

        this.getNinLogger().info("Registering modules..");
        NcCommandImplementation ncCommandImplementation = new NcCommandImplementation(this);
        this.commandImplementation = ncCommandImplementation;

        this.getModuleManager().addModule(ncCommandImplementation);
        this.getModuleManager().addModule(new NcEntityManager(this));
        this.getModuleManager().addModule(new NinCoreCmdModule(this)); // Cmd module is dependant on the command implementation.
        this.getModuleManager().addModule(new ArmorEquipEventModule(this));
    }


    @Override
    public void onDisableInner()
    {
        //this.getDataManager().saveDataFile();
    }


//    public static ProtocolManager getProtocolLib()
//    {
//        return instance.protocolManager;
//    }


    @Override
    public JavaPlugin getImplementingPlugin()
    {
        return this;
    }


    @Override
    public boolean useColoredLogging()
    {
        return this.getNinCoreConfig().isColoredLoggingEnabled();
    }


    /**
     * Dispatch a command from console.
     *
     * @param command The command string to send.
     */
    @Override
    public void dispatchCommand(String command)
    {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }


    /**
     * Get an entity by it's entity ID.
     *
     * @param id The entity ID to search the related entity for.
     * @return The entity, if no entity was found, null will be returned.
     */
    @Override
    public Entity getEntityById(int id)
    {
        Entity e = null;

        for (World w : Bukkit.getWorlds())
        {
            for (Entity entity : w.getEntities())
            {
                if (entity.getEntityId() == id) // Found the entity!
                {
                    e = entity;
                    break; // Stop searching any further
                }
            }

            // We found the entity!
            if (e != null)
            {
                break; // Stop searching any further
            }
        }

        return e; // Will be null if we didn't find any entity with thgit coat entity ID.
    }


    @Override
    public World getDefaultWorld()
    {
        return Bukkit.getWorlds().get(0);
    }
}
