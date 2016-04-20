package me.ninjoh.nincore;


import lombok.Getter;
import me.ninjoh.nincore.api.LocalizationManager;
import me.ninjoh.nincore.api.NinCore;
import me.ninjoh.nincore.api.NinCoreImplementation;
import me.ninjoh.nincore.api.NinCorePlugin;
import me.ninjoh.nincore.api.command.CommandImplementation;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.builders.CommandBuilder;
import me.ninjoh.nincore.api.command.builders.SubCommandBuilder;
import me.ninjoh.nincore.api.logging.LogColor;
import me.ninjoh.nincore.command.NcCommandImplementation;
import me.ninjoh.nincore.entity.NcEntityManager;
import me.ninjoh.nincore.entity.NcOnlinePlayer;
import me.ninjoh.nincore.listeners.ArmorListener;
import me.ninjoh.nincore.localization.NcLocalizationManager;
import me.ninjoh.nincore.subcommands.GetJavaVersion;
import me.ninjoh.nincore.subcommands.IsAnsiConsole;
import me.ninjoh.nincore.subcommands.ListOperators;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class NcCore extends NinCorePlugin implements NinCoreImplementation
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
        entityManager = new NcEntityManager();

        this.saveDefaultConfig();
        this.ninCoreConfig = new NinCoreConfig(this.getConfig());

        localizationManager = new NcLocalizationManager(); // Localization manager is dependant on the nincore configuration.
        commandImplementation = new NcCommandImplementation();



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


        this.getNinLogger().info("Registering blocked materials for ArmorEquipEvent..");
        List<String> blocked = new ArrayList<>();
        blocked.add(Material.FURNACE.toString());
        blocked.add(Material.CHEST.toString());
        blocked.add(Material.BEACON.toString());
        blocked.add(Material.DISPENSER.toString());
        blocked.add(Material.DROPPER.toString());
        blocked.add(Material.HOPPER.toString());
        blocked.add(Material.WORKBENCH.toString());
        blocked.add(Material.ENCHANTMENT_TABLE.toString());
        blocked.add(Material.ENDER_CHEST.toString());
        blocked.add(Material.ANVIL.toString());
        blocked.add(Material.BED_BLOCK.toString());
        blocked.add(Material.FENCE_GATE.toString());
        blocked.add(Material.SPRUCE_FENCE_GATE.toString());
        blocked.add(Material.BIRCH_FENCE_GATE.toString());
        blocked.add(Material.ACACIA_FENCE_GATE.toString());
        blocked.add(Material.JUNGLE_FENCE_GATE.toString());
        blocked.add(Material.DARK_OAK_FENCE_GATE.toString());
        blocked.add(Material.IRON_DOOR_BLOCK.toString());
        blocked.add(Material.WOODEN_DOOR.toString());
        blocked.add(Material.SPRUCE_DOOR.toString());
        blocked.add(Material.BIRCH_DOOR.toString());
        blocked.add(Material.JUNGLE_DOOR.toString());
        blocked.add(Material.ACACIA_DOOR.toString());
        blocked.add(Material.DARK_OAK_DOOR.toString());
        blocked.add(Material.WOOD_BUTTON.toString());
        blocked.add(Material.STONE_BUTTON.toString());
        blocked.add(Material.TRAP_DOOR.toString());
        blocked.add(Material.IRON_TRAPDOOR.toString());
        blocked.add(Material.DIODE_BLOCK_OFF.toString());
        blocked.add(Material.DIODE_BLOCK_ON.toString());
        blocked.add(Material.REDSTONE_COMPARATOR_OFF.toString());
        blocked.add(Material.REDSTONE_COMPARATOR_ON.toString());
        blocked.add(Material.FENCE.toString());
        blocked.add(Material.SPRUCE_FENCE.toString());
        blocked.add(Material.BIRCH_FENCE.toString());
        blocked.add(Material.JUNGLE_FENCE.toString());
        blocked.add(Material.DARK_OAK_FENCE.toString());
        blocked.add(Material.ACACIA_FENCE.toString());
        blocked.add(Material.NETHER_FENCE.toString());
        blocked.add(Material.BREWING_STAND.toString());
        blocked.add(Material.CAULDRON.toString());
        blocked.add(Material.SIGN_POST.toString());
        blocked.add(Material.WALL_SIGN.toString());
        blocked.add(Material.SIGN.toString());


        // Add all currently online players to the online NinPlayers list of the NinServer.

        this.getNinLogger().info("Adding all online players to the online player cache..");
        for (Player p : getServer().getOnlinePlayers())
        {
            this.entityManager.addNinOnlinePlayer(new NcOnlinePlayer(p));
            this.getNinLogger().fine("Added a NinPlayer to the online player cache, (" + p.getName() + ", " + p.getUniqueId() + ")");
        }


        //protocolManager = ProtocolLibrary.getProtocolManager();




        // Register listeners
        this.getNinLogger().info("Registering event listeners..");
        Bukkit.getPluginManager().registerEvents(new ArmorListener(blocked), this);


        // Register NinCore command it's sub commands.
        this.getNinLogger().info("Registering NinCore command..");

        CommandBuilder ncB = new CommandBuilder(this);
        ncB.setName("nincore");
        ncB.setUseStaticDescription(true);
        NinCommand nc = ncB.construct();

        this.getNinLogger().info("Registering subcommands..");

        nc.addDefaultInfoSubCmd();
        nc.addDefaultHelpSubCmd();

        SubCommandBuilder nc_getjavaversionB = new SubCommandBuilder();
        nc_getjavaversionB.setParentCommand(nc);
        nc_getjavaversionB.setName("getJavaVersion");
        nc_getjavaversionB.setRequiredPermission("nincore.getjavaversion");
        nc_getjavaversionB.addAlias("gjv");
        nc_getjavaversionB.setUseStaticDescription(true);
        nc_getjavaversionB.setStaticDescription("Get the current Java runtime version.");
        nc_getjavaversionB.setExecutor(new GetJavaVersion());
        nc_getjavaversionB.construct();

        SubCommandBuilder nc_isAnsiConsole = new SubCommandBuilder();
        nc_isAnsiConsole.setParentCommand(nc);
        nc_isAnsiConsole.setName("isAnsiConsole");
        nc_isAnsiConsole.setRequiredPermission("nincore.isansiconsole");
        nc_isAnsiConsole.addAlias("iac");
        nc_isAnsiConsole.setUseStaticDescription(true);
        nc_isAnsiConsole.setStaticDescription("Check if the current console is ANSI supported.");
        nc_isAnsiConsole.setExecutor(new IsAnsiConsole());
        nc_isAnsiConsole.construct();

        SubCommandBuilder nc_listOperators = new SubCommandBuilder();
        nc_listOperators.setParentCommand(nc);
        nc_listOperators.setName("listOperators");
        nc_listOperators.setRequiredPermission("nincore.listoperators");
        nc_listOperators.addAlias("lo");
        nc_listOperators.setUseStaticDescription(true);
        nc_listOperators.setStaticDescription("List all server operators.");
        nc_listOperators.setExecutor(new ListOperators());
        nc_listOperators.construct();
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
