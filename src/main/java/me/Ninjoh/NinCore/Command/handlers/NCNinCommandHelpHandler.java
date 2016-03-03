package me.ninjoh.nincore.command.handlers;


import me.ninjoh.nincore.NinCoreOld;
import me.ninjoh.nincore.command.NCSubCommand;
import me.ninjoh.nincore.interfaces.SubCommandExecutor;
import me.ninjoh.nincore.util.NCMessageUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class NCNinCommandHelpHandler implements SubCommandExecutor
{
    private NCSubCommand NCSubCommand;

    @NotNull
    @Override
    public SubCommandExecutor init(NCSubCommand NCSubCommand)
    {
        this.NCSubCommand = NCSubCommand;
        return this;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args)
    {
        NinCoreOld.getPlugin().getLogger().info("inside help");
        if(args.length == 1) // Sub command supplied.
        {
            NCSubCommand subCmd = NCSubCommand.getParentNCCommand().getSubCommandByAlias(args[0]);

            if(subCmd == null)
            {
                NCMessageUtil.sendCommandHelp(sender, NCSubCommand.getParentNCCommand());
            }
            else
            {
                NCMessageUtil.sendCommandHelp(sender, NCSubCommand.getParentNCCommand(), subCmd);
            }
        }
        else
        {
            NCMessageUtil.sendCommandHelp(sender, NCSubCommand.getParentNCCommand());
        }
    }
}
