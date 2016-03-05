package me.ninjoh.nincore.command.handlers;


import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.executors.SubCommandExecutor;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.util.MessageUtil;
import org.bukkit.command.CommandSender;

public class NCNinCommandHelpHandler implements SubCommandExecutor
{
    private NinSubCommand SubCommand;

    @NotNull
    @Override
    public SubCommandExecutor init(NinSubCommand NCSubCommand)
    {
        this.SubCommand = NCSubCommand;
        return this;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args)
    {
        if(args.length == 1) // Sub command supplied.
        {
            NinSubCommand subCmd = SubCommand.getParentCommand().getSubCommandByAlias(args[0]);

            if(subCmd == null)
            {
                MessageUtil.sendCommandHelp(sender, SubCommand.getParentCommand());
            }
            else
            {
                MessageUtil.sendCommandHelp(sender, SubCommand.getParentCommand(), subCmd);
            }
        }
        else
        {
            MessageUtil.sendCommandHelp(sender, SubCommand.getParentCommand());
        }
    }
}
