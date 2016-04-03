package me.ninjoh.nincore.command;


import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.executors.NinSubCommandExecutor;
import me.ninjoh.nincore.command.handlers.NCNinSubCommandHandler;

import java.util.List;

public class NCSubCommand extends CommandBase implements NinSubCommand
{
    private NinSubCommandExecutor executor; // Required
    private NCNinSubCommandHandler handler;
    private NinCommand parentCommand;


    public NCSubCommand(String name, boolean useStaticDescription, String staticDescription, String descriptionKey, String descriptionBundleBaseName, String requiredPermission, String usage, List<String> aliases, NinSubCommandExecutor executor, NinCommand parentCommand, ClassLoader loader)
    {
        super(name, useStaticDescription, staticDescription, descriptionKey, descriptionBundleBaseName, requiredPermission, usage, aliases, loader);

        this.executor = executor.init(this);
        this.handler = new NCNinSubCommandHandler(this);
        this.parentCommand = parentCommand;
    }


    public static NCSubCommand construct(String name, boolean useStaticDescription, String staticDescription, String descriptionKey, String descriptionBundleBaseName, String requiredPermission, String usage, List<String> aliases, NinSubCommandExecutor executor, NinCommand parentCommand, ClassLoader loader)
    {
        if(loader == null) loader = ((NCCommand) parentCommand).getPlugin().getClass().getClassLoader();

        return new NCSubCommand(name, useStaticDescription, staticDescription, descriptionKey, descriptionBundleBaseName, requiredPermission, usage, aliases, executor, parentCommand, loader);
    }


    /**
     * Get the executor for this sub command.
     *
     * @return The executor for this sub command.
     */
    @Override
    public NinSubCommandExecutor getExecutor()
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