package me.ninjoh.nincore.command;


import com.google.common.base.Preconditions;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.executors.NinSubCommandExecutor;
import me.ninjoh.nincore.api.localization.LocalizedString;
import me.ninjoh.nincore.command.handlers.NCNinSubCommandHandler;

import java.util.List;

public class NCSubCommand extends CommandBase implements NinSubCommand
{
    private NinSubCommandExecutor executor; // Required
    private NCNinSubCommandHandler handler;
    private NinCommand parentCommand;


    public NCSubCommand(String name, boolean useStaticDescription, String staticDescription, LocalizedString localizedDescription, String requiredPermission, String usage, List<String> aliases, NinSubCommandExecutor executor, NinCommand parentCommand)
    {
        super(name, useStaticDescription, staticDescription, localizedDescription, requiredPermission, usage, aliases);

        this.executor = executor.init(this);
        this.handler = new NCNinSubCommandHandler(this);
        this.parentCommand = parentCommand;
    }


    public static NCSubCommand construct(String name, boolean useStaticDescription, String staticDescription, LocalizedString localizedDescription, String requiredPermission, String usage, List<String> aliases, NinSubCommandExecutor executor, NinCommand parentCommand)
    {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(parentCommand);

        return new NCSubCommand(name, useStaticDescription, staticDescription, localizedDescription, requiredPermission, usage, aliases, executor, parentCommand);
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