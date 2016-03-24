package me.ninjoh.nincore;


import me.ninjoh.nincore.api.*;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.executors.NinCommandExecutor;
import me.ninjoh.nincore.api.command.executors.SubCommandExecutor;
import me.ninjoh.nincore.api.entity.NinPlayer;
import me.ninjoh.nincore.api.exceptions.technicalexceptions.SubCommandAliasAlreadyRegisteredException;
import me.ninjoh.nincore.api.exceptions.technicalexceptions.SubCommandAlreadyExistsException;
import me.ninjoh.nincore.api.logging.LogColor;
import me.ninjoh.nincore.api.util.PluginUtil;
import me.ninjoh.nincore.command.NCCommand;
import me.ninjoh.nincore.command.NCSubCommand;
import me.ninjoh.nincore.command.handlers.NCNinCommandHandler;
import me.ninjoh.nincore.listeners.ArmorListener;
import me.ninjoh.nincore.listeners.PlayerListener;
import me.ninjoh.nincore.player.NCNinOfflinePlayer;
import me.ninjoh.nincore.player.NCNinPlayer;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class NCNinCore extends NinCorePlugin implements NinCoreImplementation
{
    private MinecraftLocale defaultMinecraftLocale = MinecraftLocale.BRITISH_ENGLISH;
    private boolean useLocalization;
    private boolean useColoredLogging;


    private NinServer server;
    private static NCNinCore NCNinCore;

    //private static ProtocolManager protocolManager;


    @Override
    public void onEnableInner()
    {
        NCNinCore = this;
        this.getNinLogger().info(this.getDescription().getName() + " v" + this.getDescription().getVersion() +
                " by " + StringUtils.join(this.getDescription().getAuthors(), ", "));
        NinCore.setImplementation(this);
        this.saveDefaultConfig();


        useColoredLogging = this.getConfig().getBoolean("logging.enableColoredLogging");
        if(useColoredLogging)
        {
            this.getNinLogger().info("Colored logging is enabled.");
        }
        else
        {
            this.getNinLogger().info("Colored logging is disabled.");
        }

        useLocalization = this.getConfig().getBoolean("localization.enabled");
        if(useLocalization)
        {
            this.getNinLogger().info("Localization is enabled.");
        }
        else
        {
            this.getNinLogger().info("Localization is enabled.");
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

        this.server = new NCNinServer(ninPlayers);



        //protocolManager = ProtocolLibrary.getProtocolManager();

        this.getNinLogger().info("Default MinecraftLocale set to: " + LogColor.HIGHLIGHT + defaultMinecraftLocale.name() + " (" +
                defaultMinecraftLocale.getLanguageTag() + ", " +
                defaultMinecraftLocale.getDisplayName(MinecraftLocale.BRITISH_ENGLISH) + ")");


        // Register listeners
        this.getNinLogger().info("Registering event listeners..");
        getServer().getPluginManager().registerEvents(new ArmorListener(blocked), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArmorListener(blocked), this);
    }


    @Override
    public void onDisableInner()
    {

    }


//    public static ProtocolManager getProtocolLib()
//    {
//        return NCNinCore.protocolManager;
//    }


    public static NCNinCore getInstance()
    {
        return NCNinCore;
    }


    @Override
    public NinServer getNinServer()
    {
        return this.server;
    }


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
    public NinCommand constructCommand(String name, boolean useStaticDescription, String descriptionKey, String descriptionBundleBaseName, String requiredPermission, NinCommandExecutor executor, List<NinSubCommand> subCommands, JavaPlugin plugin)
    {
        return NCCommand.construct(name, useStaticDescription, descriptionKey, descriptionBundleBaseName, requiredPermission, executor, subCommands, plugin);
    }


    @Override
    public NinSubCommand constructSubCommand(String name, boolean useStaticDescription, String staticDescription, String descriptionKey, String descriptionBundleBaseName, String requiredPermission, String usage, List<String> aliases, SubCommandExecutor executor, NinCommand parentCommand)
    {
        return NCSubCommand.construct(name, useStaticDescription, staticDescription, descriptionKey, descriptionBundleBaseName, requiredPermission, usage, aliases, executor, parentCommand);
    }


    @Override
    public NinCommandSender getNinCommandSender(CommandSender commandSender)
    {
        return new NCNinCommandSender(commandSender);
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
    public MinecraftLocale getDefaultMinecraftLocale()
    {
        return this.defaultMinecraftLocale;
    }


    @Override
    public void setDefaultMinecraftLocale(MinecraftLocale minecraftLocale)
    {
        this.getNinLogger().info("Default MinecraftLocale changed to " + defaultMinecraftLocale.name() + " (" +
                defaultMinecraftLocale.getLanguageTag() + ", " +
                defaultMinecraftLocale.getDisplayName(MinecraftLocale.BRITISH_ENGLISH) + ")");
        this.defaultMinecraftLocale = minecraftLocale;
    }


    @Override
    public void setUseLocalization(boolean value)
    {
        this.useLocalization = value;
    }


    @Override
    public boolean useLocalization()
    {
        return this.useLocalization;
    }


    @Override
    public JavaPlugin getImplementingPlugin()
    {
        return this;
    }


    @Override
    public boolean useColoredLogging()
    {
        return this.useColoredLogging;
    }
}
