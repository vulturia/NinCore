package me.ninjoh.nincore.command;


import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.executors.SubCommandExecutor;
import me.ninjoh.nincore.command.handlers.NCNinSubCommandHandler;

import java.util.List;

public class NCSubCommand extends CommandBase implements NinSubCommand
{
    private SubCommandExecutor executor; // Required
    private NCNinSubCommandHandler handler;
    private NinCommand parentCommand;


    public NCSubCommand(String name, boolean useStaticDescription, String staticDescription, String descriptionKey, String descriptionBundleBaseName, String requiredPermission, String usage, List<String> aliases, SubCommandExecutor executor, NinCommand parentCommand)
    {
        super(name, useStaticDescription, staticDescription, descriptionKey, descriptionBundleBaseName, requiredPermission, usage, aliases, ((NCCommand) parentCommand).getPlugin().getClass().getClassLoader());

        this.executor = executor.init(this);
        this.handler = new NCNinSubCommandHandler(this);
        this.parentCommand = parentCommand;
    }


    public static NCSubCommand construct(String name, boolean useStaticDescription, String staticDescription, String descriptionKey, String descriptionBundleBaseName, String requiredPermission, String usage, List<String> aliases, SubCommandExecutor executor, NinCommand parentCommand)
    {
        return new NCSubCommand(name, useStaticDescription, staticDescription, descriptionKey, descriptionBundleBaseName, requiredPermission, usage, aliases, executor, parentCommand);
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