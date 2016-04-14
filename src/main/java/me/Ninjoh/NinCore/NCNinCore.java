package me.ninjoh.nincore;


import lombok.Getter;
import lombok.Setter;
import me.ninjoh.nincore.api.*;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.builders.CommandBuilder;
import me.ninjoh.nincore.api.command.builders.SubCommandBuilder;
import me.ninjoh.nincore.api.command.executors.NinCommandExecutor;
import me.ninjoh.nincore.api.command.executors.NinSubCommandExecutor;
import me.ninjoh.nincore.api.entity.NinPlayer;
import me.ninjoh.nincore.api.exceptions.technicalexceptions.SubCommandAliasAlreadyRegisteredException;
import me.ninjoh.nincore.api.exceptions.technicalexceptions.SubCommandAlreadyExistsException;
import me.ninjoh.nincore.api.localization.LocalizedString;
import me.ninjoh.nincore.api.logging.LogColor;
import me.ninjoh.nincore.command.NCCommand;
import me.ninjoh.nincore.command.NCSubCommand;
import me.ninjoh.nincore.command.handlers.NCNinCommandHandler;
import me.ninjoh.nincore.listeners.ArmorListener;
import me.ninjoh.nincore.listeners.PlayerListener;
import me.ninjoh.nincore.player.NCNinOfflinePlayer;
import me.ninjoh.nincore.player.NCNinPlayer;
import me.ninjoh.nincore.subcommands.GetJavaVersion;
import me.ninjoh.nincore.subcommands.IsAnsiConsole;
import me.ninjoh.nincore.subcommands.ListOperators;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class NCNinCore extends NinCorePlugin implements NinCoreImplementation
{
    @Getter         private MinecraftLocale defaultMinecraftLocale;
    @Getter @Setter private boolean isLocalized;
                    private boolean consoleIsAnsiSupported = false;

    @Getter         private NinServer ninServer;
                    private NinCoreConfig config;

    @Getter         private static NCNinCore instance;


    //private static ProtocolManager protocolManager;


    @Override
    public void onLoadInner()
    {
        NinCore.setImplementation(this);
        instance = this;

        try
        {
            ConsoleReader consoleReader = (ConsoleReader) Bukkit.getServer().getClass().getMethod("getReader").invoke(Bukkit.getServer());

            consoleIsAnsiSupported = consoleReader.getTerminal().isAnsiSupported();
        }
        catch (ClassCastException | NullPointerException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            this.getNinLogger().warning(String.format("Could not use colored logging because a %s occurred whilst" +
                    " trying to check if the terminal is ANSI supported;", e.getClass().getName()));
            this.getNinLogger().fine(ExceptionUtils.getFullStackTrace(e));
        }
    }


    @Override
    public void onEnableInner()
    {
        this.getNinLogger().info(this.getDescription().getName() + " v" + this.getDescription().getVersion() +
                " by " + StringUtils.join(this.getDescription().getAuthors(), ", "));


        this.saveDefaultConfig();
        this.config = new NinCoreConfig(this.getConfig());

//        if(!this.getDataManager().dataFileExists())
//        {
//            this.getDataManager().createDataFile();
//        }
//
//        this.getDataManager().loadDataFile();
//        this.getDataManager().getData()

        this.defaultMinecraftLocale = NinCoreConfig.getDefaultMinecraftLocale();


        if (NinCoreConfig.isColoredLoggingEnabled())
        {
            this.getNinLogger().info("Colored logging is " + LogColor.HIGHLIGHT + "enabled" + LogColor.RESET + ".");
        }
        else
        {
            this.getNinLogger().info("Colored logging is " + LogColor.HIGHLIGHT + "disabled" + LogColor.RESET + ".");
        }

        isLocalized = this.getConfig().getBoolean("localization.enabled");
        if (isLocalized)
        {
            this.getNinLogger().info("Localization is " + LogColor.HIGHLIGHT + "enabled" + LogColor.RESET + ".");
        }
        else
        {
            this.getNinLogger().info("Localization is " + LogColor.HIGHLIGHT + "enabled.");
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
        List<NinPlayer> ninPlayers = new ArrayList<>();


        this.getNinLogger().info("Adding all online players to the online player cache..");
        for (Player p : getServer().getOnlinePlayers())
        {
            ninPlayers.add(new NCNinPlayer(p));
            this.getNinLogger().fine("Added a NinPlayer to the online player cache, (" + p.getName() + ", " + p.getUniqueId() + ")");
        }

        this.ninServer = new NCNinServer(ninPlayers);


        //protocolManager = ProtocolLibrary.getProtocolManager();

        this.getNinLogger().info("Default MinecraftLocale set to: " + LogColor.HIGHLIGHT + defaultMinecraftLocale.name() + " (" +
                defaultMinecraftLocale.toLanguageTag() + ", " +
                defaultMinecraftLocale.getDisplayName(MinecraftLocale.BRITISH_ENGLISH) + ")");


        // Register listeners
        this.getNinLogger().info("Registering event listeners..");
        getServer().getPluginManager().registerEvents(new ArmorListener(blocked), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
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
        this.registerNinSubCommand(nc_getjavaversionB.construct(), this);

        SubCommandBuilder nc_isAnsiConsole = new SubCommandBuilder();
        nc_isAnsiConsole.setParentCommand(nc);
        nc_isAnsiConsole.setName("isAnsiConsole");
        nc_isAnsiConsole.setRequiredPermission("nincore.isansiconsole");
        nc_isAnsiConsole.addAlias("iac");
        nc_isAnsiConsole.setUseStaticDescription(true);
        nc_isAnsiConsole.setStaticDescription("Check if the current console is ANSI supported.");
        nc_isAnsiConsole.setExecutor(new IsAnsiConsole());
        this.registerNinSubCommand(nc_isAnsiConsole.construct(), this);

        SubCommandBuilder nc_listOperators = new SubCommandBuilder();
        nc_listOperators.setParentCommand(nc);
        nc_listOperators.setName("listOperators");
        nc_listOperators.setRequiredPermission("nincore.listoperators");
        nc_listOperators.addAlias("lo");
        nc_listOperators.setUseStaticDescription(true);
        nc_listOperators.setStaticDescription("List all server operators.");
        nc_listOperators.setExecutor(new ListOperators());
        this.registerNinSubCommand(nc_listOperators.construct(), this);
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
    public void registerNinCommand(NinCommand ninCommand, JavaPlugin javaPlugin)
    {
        javaPlugin.getCommand(ninCommand.getName()).setExecutor(new NCNinCommandHandler((NCCommand) ninCommand));
    }


    @Override
    public void registerNinSubCommand(NinSubCommand ninSubCommand, JavaPlugin javaPlugin) throws SubCommandAliasAlreadyRegisteredException, SubCommandAlreadyExistsException
    {
        ninSubCommand.getParentCommand().addSubCommand(ninSubCommand);
    }


    @Override
    public NinCommand constructCommand(String name, boolean useStaticDescription, LocalizedString localizedDescription, String requiredPermission, NinCommandExecutor executor, List<NinSubCommand> subCommands, JavaPlugin plugin)
    {
        return NCCommand.construct(name, useStaticDescription, localizedDescription, requiredPermission, executor, subCommands, plugin);
    }


    @Override
    public NinSubCommand constructSubCommand(String name, boolean useStaticDescription, String staticDescription, LocalizedString localizedDescription, String requiredPermission, String usage, List<String> aliases, NinSubCommandExecutor executor, NinCommand parentCommand)
    {
        return NCSubCommand.construct(name, useStaticDescription, staticDescription, localizedDescription, requiredPermission, usage, aliases, executor, parentCommand);
    }


    @Override
    public NinCommandSender getNinCommandSender(CommandSender commandSender)
    {
        return new NCNinCommandSender(commandSender);
    }


    @Override
    public NinConsoleCommandSender getNinConsoleCommandSender()
    {
        return new NCNinConsoleCommandSender();
    }


    @Override
    public NinPlayer getNinPlayer(Player player)
    {
        return this.getNinServer().getNinPlayer(player);
    }


    @Override
    public NinOfflinePlayer getNinOfflinePlayer(OfflinePlayer offlinePlayer)
    {
        return NCNinOfflinePlayer.fromOfflinePlayer(offlinePlayer);
    }


    @Override
    public void setDefaultMinecraftLocale(MinecraftLocale minecraftLocale)
    {
        if(!NinCoreConfig.isDefaultMinecraftLocaleUpdatable())
        {
            this.getNinLogger().warning("Could not update the default MinecraftLocale due to updating the default" +
                    " MinecraftLocale being disabled in the configuration file.");
        }
        else
        {
            this.getNinLogger().info("Default MinecraftLocale changed to " + defaultMinecraftLocale.name() + " (" +
                    defaultMinecraftLocale.toLanguageTag() + ", " +
                    defaultMinecraftLocale.getDisplayName(MinecraftLocale.BRITISH_ENGLISH) + ")");
            this.defaultMinecraftLocale = minecraftLocale;
        }
    }


    @Override
    public JavaPlugin getImplementingPlugin()
    {
        return this;
    }


    @Override
    public boolean useColoredLogging()
    {
        return NinCoreConfig.isColoredLoggingEnabled();
    }


    @Override
    public boolean consoleIsAnsiSupported()
    {
        return consoleIsAnsiSupported;
    }


    public NinCoreConfig getNinCoreConfig()
    {
        return config;
    }
}
