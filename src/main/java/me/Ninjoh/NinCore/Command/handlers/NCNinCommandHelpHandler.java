package me.ninjoh.nincore.command.handlers;


import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.executors.NinSubCommandExecutor;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.util.MessageUtil;
import org.bukkit.command.CommandSender;

public class NCNinCommandHelpHandler extends NinSubCommandExecutor
{
    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args)
    {
        if (args.length == 1) // Sub command supplied.
        {
            NinSubCommand subCmd = this.getNinSubCommand().getParentCommand().getSubCommandByAlias(args[0]);

            if (subCmd == null)
            {
                MessageUtil.sendCommandHelp(sender, this.getNinSubCommand().getParentCommand());
            }
            else
            {
                MessageUtil.sendCommandHelp(sender, subCmd);
            }
        }
        else
        {
            MessageUtil.sendCommandHelp(sender, this.getNinSubCommand().getParentCommand());
        }
    }
}
