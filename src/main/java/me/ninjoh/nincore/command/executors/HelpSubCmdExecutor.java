package me.ninjoh.nincore.command.executors;


import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.command.executors.NinSubCommandExecutor;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.entity.NcCommandSender;
import org.bukkit.command.CommandSender;

public class HelpSubCmdExecutor extends NinSubCommandExecutor
{
    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args)
    {
        NcCommandSender nsender = NcCommandSender.fromCommandSender(sender);

        if (args.length == 1) // Sub command supplied.
        {
            NinSubCommand subCmd = this.getNinSubCommand().getParentCommand().getSubCommandByAlias(args[0]);

            if (subCmd == null)
            {
                nsender.sendCommandHelp(this.getNinSubCommand().getParentCommand());
            }
            else
            {
                nsender.sendCommandHelp(subCmd);
            }
        }
        else
        {
            nsender.sendCommandHelp(this.getNinSubCommand().getParentCommand());
        }
    }
}
