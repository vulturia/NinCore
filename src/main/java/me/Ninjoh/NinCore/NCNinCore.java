package me.ninjoh.nincore;


import me.ninjoh.nincore.api.*;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.builders.NinArgument;
import me.ninjoh.nincore.api.command.executors.NinCommandExecutor;
import me.ninjoh.nincore.api.command.executors.SubCommandExecutor;
import me.ninjoh.nincore.api.entity.NinPlayer;
import me.ninjoh.nincore.player.NCNinPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NCNinCore extends NCNinCorePlugin implements NinCoreImplementation
{
    private MinecraftLocale DEFAULT_MINECRAFT_LOCALE = MinecraftLocale.BRITISH_ENGLISH;
    private boolean USE_LOCALIZATION = true;


    private NinServer server;


    @Override
    public void onEnableInner()
    {
        // Add all currently online players to the online NinPlayers list of the NinServer.
        List<NCNinPlayer> ninPlayers = new ArrayList<>();

        for (Player p : getServer().getOnlinePlayers())
        {
            ninPlayers.add(new NCNinPlayer(p));
        }

        this.server = new NCNinServer(ninPlayers);



        NinCore.setImplementation(this);
    }


    @Override
    public void onDisableInner()
    {

    }




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
    public void registerNinCommand(NinCommand ninCommand)
    {

    }

    @Override
    public void registerNinSubCommand(NinSubCommand ninSubCommand)
    {

    }

    @Override
    public NinCommand constructCommand(@NotNull String s, @Nullable List<NinSubCommand> list, @Nullable List<NinArgument> list1, @NotNull NinCommandExecutor ninCommandExecutor)
    {
        return null;
    }

    @Override
    public NinSubCommand constructSubCommand(@NotNull String s, @Nullable List<String> list, @Nullable String[] strings, @Nullable String s1, @Nullable List<NinArgument> list1, @NotNull SubCommandExecutor subCommandExecutor, @NotNull NinCommand ninCommand)
    {
        return null;
    }

    @Override
    public NinArgument constructArgument()
    {
        return null;
    }

    @Override
    public void getNinCommandSender(CommandSender commandSender)
    {

    }

    @Override
    public NinConsoleCommandSender getNinConsoleCommandSender()
    {
        return null;
    }

    @Override
    public NinPlayer getNinPlayer(Player player)
    {
        return null;
    }

    @Override
    public NinOfflinePlayer getNinOfflinePlayer(OfflinePlayer offlinePlayer)
    {
        return null;
    }

    @Override
    public MinecraftLocale getDefaultMinecraftLocale()
    {
        return this.DEFAULT_MINECRAFT_LOCALE;
    }

    @Override
    public void setDefaultMinecraftLocale(MinecraftLocale minecraftLocale)
    {
        this.DEFAULT_MINECRAFT_LOCALE = minecraftLocale;
    }

    @Override
    public void setUseLocalization(boolean b)
    {
        this.USE_LOCALIZATION = b;
    }

    @Override
    public boolean useLocalization()
    {
        return this.USE_LOCALIZATION;
    }
}
