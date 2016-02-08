package me.Ninjoh.NinCore.Library.Util;


import me.Ninjoh.NinCore.Library.Entity.Tick;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class DataManager
{
    private JavaPlugin plugin;
    private FileConfiguration data;
    private FileConfiguration lang;

    private File dataF;
    private File langF;



    public DataManager(@NotNull JavaPlugin plugin)
    {
        this.plugin = plugin;
        dataF = new File(plugin.getDataFolder(), "data.yml");
        langF = new File(plugin.getDataFolder(), "lang.yml");
    }


    /**
     * Create the data file
     */
    public void createDataFile()
    {
        FileConfiguration dataFile = YamlConfiguration.loadConfiguration(dataF);

        try
        {
            plugin.getLogger().info("Attempting to create data file..");

            dataFile.save(dataF);

            plugin.getLogger().info("Successfully created data file");
        }
        catch (IOException e)
        {
            plugin.getLogger().severe("An unknown exception generated while creating data file:");
            e.printStackTrace();
        }
    }


    /**
     * Check if data file exists
     *
     * @return Does the data file exist? true/false
     */
    public boolean dataFileExists()
    {
        return dataF.exists();
    }


    /**
     * Save the data file.
     */
    public void saveDataFile()
    {
        try
        {
            plugin.getLogger().info("Attempting to save data file..");

            data.save(dataF);

            plugin.getLogger().info("Successfully saved data file");
        }
        catch(IOException e)
        {
            plugin.getLogger().severe("An unknown exception generated while saving data file:");
            e.printStackTrace();
        }
    }


    /**
     * Schedule automatic saving of the data file.
     *
     * @param interval The interval between saving.
     */
    public void scheduleAutomaticDataFileSave(long interval)
    {
        // Schedule automatic saving of data file.
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                saveDataFile();
            }
        }, interval, interval);

        plugin.getLogger().info("Scheduled data saving interval is set to " + interval + " ticks(s).");
    }


    /**
     * Schedule automatic saving of the data file.
     *
     * @param interval The interval between saving.
     */
    public void scheduleAutomaticDataFileSave(@NotNull Tick interval)
    {
        // Schedule automatic saving of data file.
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                saveDataFile();
            }
        }, interval.toLong(), interval.toLong());

        plugin.getLogger().info("Scheduled data saving interval is set to " + interval + " tick(s).");
    }


    /**
     * Load the data file
     *
     * @return FileConfiguration of the data file
     */
    public FileConfiguration loadDataFile()
    {
        data = YamlConfiguration.loadConfiguration(dataF);
        return data;
    }


    /**
     * Create the data file
     */
    public void createLangFile()
    {
        FileConfiguration langFile = YamlConfiguration.loadConfiguration(langF);
        lang = langFile;


        try
        {
            plugin.getLogger().info("Attempting to create language file..");

            langFile.save(langF);

            plugin.getLogger().info("Successfully created language file.");
        }
        catch(IOException e)
        {
            plugin.getLogger().severe("An unknown exception generated while creating language file:");
            e.printStackTrace();
        }
    }

    /**
     * Check if data file exists
     *
     * @return Does the data file exist? true/false
     */
    public boolean langFileExists()
    {
        return langF.exists();
    }

    public void saveLangFile()
    {
        try
        {
            plugin.getLogger().info("Attempting to save language file..");

            lang.save(langF);

            plugin.getLogger().info("Successfully saved language file.");
        }
        catch(IOException e)
        {
            plugin.getLogger().severe("An unknown exception generated while creating language file:");
            e.printStackTrace();
        }
    }

    /**
     * Load the data file
     *
     * @return Yamlconfiguration of the data file
     */
    public FileConfiguration loadLangFile()
    {
        lang = YamlConfiguration.loadConfiguration(langF);
        return lang;
    }
}
