package me.ninjoh.nincore;


import me.ninjoh.nincore.api.*;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.executors.NinCommandExecutor;
import me.ninjoh.nincore.api.command.executors.SubCommandExecutor;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.Nullable;
import me.ninjoh.nincore.api.entity.NinPlayer;
import me.ninjoh.nincore.api.exceptions.technicalexceptions.SubCommandAliasAlreadyRegisteredException;
import me.ninjoh.nincore.api.exceptions.technicalexceptions.SubCommandAlreadyExistsException;
import me.ninjoh.nincore.command.NCCommand;
import me.ninjoh.nincore.command.NCSubCommand;
import me.ninjoh.nincore.command.handlers.NCNinCommandHandler;
import me.ninjoh.nincore.listeners.ArmorListener;
import me.ninjoh.nincore.listeners.PlayerListener;
import me.ninjoh.nincore.player.NCNinOfflinePlayer;
import me.ninjoh.nincore.player.NCNinPlayer;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

        // Listener for ArmorEquipEvent


        // Add all currently online players to the online NinPlayers list of the NinServer.
        List<NinPlayer> ninPlayers = new ArrayList<>();


        this.getNinLogger().info("Adding all online players to the online player cache..");
        for (Player p : getServer().getOnlinePlayers())
        {
            ninPlayers.add(new NCNinPlayer(p));
            this.getNinLogger().fine("Added a NinPlayer to the list of online players (" + p.getName() + ", " + p.getUniqueId() + ")");
        }

        this.server = new NCNinServer(ninPlayers);



        //protocolManager = ProtocolLibrary.getProtocolManager();

        this.getNinLogger().info("Default MinecraftLocale set to: " + ChatColor.LIGHT_PURPLE + defaultMinecraftLocale.name() + " (" +
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


    public static JavaPlugin getPlugin()
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
    public NinCommand constructCommand(@NotNull String s, String s1, @Nullable String s2, @Nullable List<NinSubCommand> list, @NotNull NinCommandExecutor ninCommandExecutor, JavaPlugin javaPlugin)
    {
        return new NCCommand(s, s1, s2, list, ninCommandExecutor, javaPlugin);
    }


    @Override
    public NinSubCommand constructSubCommand(@NotNull String s, @Nullable List<String> list, @Nullable String s1, @Nullable String s2, @Nullable String s3, @Nullable String s4, @NotNull SubCommandExecutor subCommandExecutor, @NotNull NinCommand ninCommand)
    {
        return new NCSubCommand(s, list, s1, s2, s3, s4, subCommandExecutor, ninCommand);
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
    public void reloadExternalPlugin(JavaPlugin javaPlugin)
    {

    }


    @Override
    public boolean useColoredLogging()
    {
        return this.useColoredLogging;
    }
}
