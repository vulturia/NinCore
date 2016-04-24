package tk.martijn_heil.nincore;


import lombok.Getter;
import tk.martijn_heil.nincore.command.NcCommandImplementation;
import tk.martijn_heil.nincore.entity.NcEntityManager;
import tk.martijn_heil.nincore.localization.NcLocalizationManager;
import tk.martijn_heil.nincore.modules.ArmorEquipEventModule;
import tk.martijn_heil.nincore.modules.NinCoreCmdModule;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import tk.martijn_heil.nincore.api.Core;
import tk.martijn_heil.nincore.api.NinCore;
import tk.martijn_heil.nincore.api.NinCoreImplementation;
import tk.martijn_heil.nincore.api.command.CommandImplementation;
import tk.martijn_heil.nincore.api.localization.LocalizationManager;
import tk.martijn_heil.nincore.api.logging.LogColor;

import java.lang.reflect.InvocationTargetException;

public class NcCore extends Core implements NinCoreImplementation
{
    @Getter private static NcCore instance;

    @Getter private boolean isConsoleAnsiSupported = false;

    @Getter private NinCoreConfig ninCoreConfig;
    @Getter private NcEntityManager entityManager;
    @Getter private LocalizationManager localizationManager;
    @Getter private CommandImplementation commandImplementation;


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
            this.getNinLogger().info("Coloured logging is " + LogColor.HIGHLIGHT + "enabled" + LogColor.RESET + " in configuration.");
        }
        else
        {
            this.getNinLogger().info("Coloured logging is " + LogColor.HIGHLIGHT + "disabled" + LogColor.RESET + " in configuration.");
        }

        if(this.isConsoleAnsiSupported)
        {
            this.getNinLogger().info("Console is ANSI supported.");
        }
        else
        {
            this.getNinLogger().warning("Console is not ANSI supported, can not use coloured logging.");
        }


        this.getNinLogger().info("Registering modules..");
        NcCommandImplementation ncCommandImplementation = new NcCommandImplementation(this);
        NcEntityManager ncEntityManager = new NcEntityManager(this);
        this.commandImplementation = ncCommandImplementation;
        this.entityManager = ncEntityManager;

        this.getModuleManager().addModule(ncCommandImplementation);
        this.getModuleManager().addModule(ncEntityManager);
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
    @NotNull
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
    @NotNull
    public World getDefaultWorld()
    {
        return Bukkit.getWorlds().get(0);
    }
}
