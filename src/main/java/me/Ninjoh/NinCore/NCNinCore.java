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
import me.ninjoh.nincore.listeners.PlayerListener;
import me.ninjoh.nincore.player.NCNinOfflinePlayer;
import me.ninjoh.nincore.player.NCNinPlayer;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
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
    private boolean useLocalization = true;


    private NinServer server;

    //private static ProtocolManager protocolManager;


    @Override
    public void onEnableInner()
    {
        // Add all currently online players to the online NinPlayers list of the NinServer.
        List<NinPlayer> ninPlayers = new ArrayList<>();

        for (Player p : getServer().getOnlinePlayers())
        {
            ninPlayers.add(new NCNinPlayer(p));
            this.getLogger().fine("Added a NinPlayer to the list of online players (" + p.getName() + ", " + p.getUniqueId() + ")");
        }

        this.getLogger().info(this.getDescription().getName() + " v" + this.getDescription().getVersion() +
                " by " + StringUtils.join(this.getDescription().getAuthors(), ", "));

        this.server = new NCNinServer(ninPlayers);

        NinCore.setImplementation(this);
        //protocolManager = ProtocolLibrary.getProtocolManager();

        this.getLogger().info("Default MinecraftLocale set to " + defaultMinecraftLocale.name() + " (" +
                defaultMinecraftLocale.getLanguageTag() + ", " +
                defaultMinecraftLocale.getDisplayName(MinecraftLocale.BRITISH_ENGLISH) + ")");


        // Register listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }


    @Override
    public void onDisableInner()
    {

    }


//    public static ProtocolManager getProtocolLib()
//    {
//        return NCNinCore.protocolManager;
//    }


    public static Plugin getPlugin()
    {
        return Bukkit.getPluginManager().getPlugin("NCNinCore");
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
        this.getLogger().info("Default MinecraftLocale changed to " + defaultMinecraftLocale.name() + " (" +
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
}
