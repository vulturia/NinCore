package me.ninjoh.nincore.command;


import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.executors.SubCommandExecutor;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.Nullable;
import me.ninjoh.nincore.api.util.TranslationUtils;
import me.ninjoh.nincore.command.handlers.NCNinSubCommandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class NCSubCommand implements NinSubCommand
{
    private String Name; // Required && Always lowercase.
    private List<String> aliases; // Optional
    private String descriptionKey; // Optional
    private String descriptionBundleBaseName; // Optional
    private String requiredPermission; // Optional.
    private SubCommandExecutor executor; // Required
    private NCNinSubCommandHandler handler;
    private NinCommand parentCommand;
    private String usage;



    public NCSubCommand(@NotNull String name, @Nullable List<String> aliases, @Nullable String descriptionKey,
                        @Nullable String descriptionBundleBaseName, @Nullable String permission, @Nullable String usage,
                        @NotNull SubCommandExecutor executor, @NotNull NinCommand parentCommand)
    {
        Name = name.toLowerCase(); // NCSubCommand names are always lower case.
        this.descriptionKey = descriptionKey;
        this.descriptionBundleBaseName = descriptionBundleBaseName;
        requiredPermission = permission;
        this.executor = executor.init(this);
        this.parentCommand = parentCommand;
        this.handler = new NCNinSubCommandHandler(this);
        this.usage = usage;

        // Make all alias entries lowercase.
        if(aliases != null)
        {
            for(int i=0; i < aliases.size(); i++) {
                aliases.set(i, aliases.get(i).toLowerCase());
            }

            this.aliases = aliases;
        }
        else
        {
            this.aliases = new ArrayList<>();
        }
    }


    /**
     * Get the name of this sub command.
     *
     * @return The name of this sub command.
     */
    @Override
    public String getName()
    {
        return Name;
    }


    /**
     * Get all aliases of this sub command.
     *
     * @return All aliases except the sub command name.
     */
    @Override
    public List<String> getAliases()
    {
        return new ArrayList<>(aliases);
    }


    /**
     * Get all aliases of this sub command.
     * Including the name which is always the main
     * alias as well.
     *
     * @return All aliases including the sub command name.
     */
    @Override
    public List<String> getAliasesWithMainSubCmd()
    {
        List<String> copyOfAliases = new ArrayList<>(aliases);
        copyOfAliases.add(0, Name);

        return copyOfAliases;
    }


    /**
     * Does this sub command have any aliases?
     *
     * @return True/False, Does this sub command have any aliases?
     */
    @Override
    public boolean hasAliases()
    {
        return !aliases.isEmpty();
    }


    /**
     * Does this sub command require a CommandSender
     * to have a certain permission?
     *
     * @return True/False, Does this sub command require a permission?
     */
    @Override
    public boolean requiresPermission()
    {
        return requiredPermission != null;
    }


    /**
     * Get the required permission for this sub command.
     *
     * @return The required permission for this sub command.
     */
    @Override
    public String getRequiredPermission()
    {
        return requiredPermission;
    }


    /**
     * Get this sub command's syntax.
     * ( Only the last part of the syntax, e.g;
     * "<player=you> <world>" )
     *
     * @return The usage string.
     */
    @Override
    public String getUsage()
    {
        return this.usage;
    }


    @Override
    public void setUsage(String value)
    {
        this.usage = value;
    }


    /**
     * Get this sub command's description.
     *
     * @return This sub command's description.
     */
    @Override
    public String getDescription()
    {
        if(this.hasDescription())
        {
            return TranslationUtils.getStaticMsg(ResourceBundle.getBundle(this.descriptionBundleBaseName), descriptionKey);
        }
        else
        {
            return null;
        }
    }


    @Override
    public String getDescription(Locale inLocale)
    {
        return TranslationUtils.getStaticMsg(ResourceBundle.getBundle(this.descriptionBundleBaseName, inLocale), this.descriptionKey);
    }


    /**
     * Check if this sub command has a description.
     *
     * @return True/False, does this sub command have a description?
     */
    @Override
    public boolean hasDescription()
    {
        return ((descriptionKey != null) && (descriptionBundleBaseName != null));
    }


    /**
     * Get the executor for this sub command.
     *
     * @return The executor for this sub command.
     */
    @Override
    public SubCommandExecutor getExecutor()
    {
        return executor;
    }


    public NCNinSubCommandHandler getHandler()
    {
        return this.handler;
    }


    @Override
    public NinCommand getParentCommand()
    {
        return this.parentCommand;
    }
}