package tk.martijn_heil.nincore.command;


import com.google.common.base.Preconditions;
import tk.martijn_heil.nincore.command.handlers.NcSubCommandHandler;
import tk.martijn_heil.nincore.api.command.NinCommand;
import tk.martijn_heil.nincore.api.command.NinSubCommand;
import tk.martijn_heil.nincore.api.command.executors.NinSubCommandExecutor;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.localization.LocalizedString;

import java.util.List;

public class NcSubCommand extends CommandBase implements NinSubCommand
{
    private NinSubCommandExecutor executor; // Required
    private NcSubCommandHandler handler;
    private NinCommand parentCommand;


    public NcSubCommand(String name, boolean useStaticDescription, String staticDescription, LocalizedString localizedDescription, String requiredPermission, String usage, List<String> aliases, NinSubCommandExecutor executor, NinCommand parentCommand) throws TechnicalException
    {
        super(name, useStaticDescription, staticDescription, localizedDescription, requiredPermission, usage, aliases);

        this.executor = executor.init(this);
        this.handler = new NcSubCommandHandler(this);
        this.parentCommand = parentCommand;

        parentCommand.addSubCommand(this);
    }


    public static NcSubCommand construct(String name, boolean useStaticDescription, String staticDescription, LocalizedString localizedDescription, String requiredPermission, String usage, List<String> aliases, NinSubCommandExecutor executor, NinCommand parentCommand)
    {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(parentCommand);

        return new NcSubCommand(name, useStaticDescription, staticDescription, localizedDescription, requiredPermission, usage, aliases, executor, parentCommand);
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


    public NcSubCommandHandler getHandler()
    {
        return this.handler;
    }


    @Override
    public NinCommand getParentCommand()
    {
        return this.parentCommand;
    }
}