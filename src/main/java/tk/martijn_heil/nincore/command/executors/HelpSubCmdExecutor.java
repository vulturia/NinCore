package tk.martijn_heil.nincore.command.executors;


import tk.martijn_heil.nincore.entity.NcCommandSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import tk.martijn_heil.nincore.api.command.NinSubCommand;
import tk.martijn_heil.nincore.api.command.executors.NinSubCommandExecutor;

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
