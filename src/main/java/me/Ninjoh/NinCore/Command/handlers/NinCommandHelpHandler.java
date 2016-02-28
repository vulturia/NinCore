package me.ninjoh.nincore.command.handlers;


import me.ninjoh.nincore.command.SubCommand;
import me.ninjoh.nincore.interfaces.SubCommandExecutor;
import me.ninjoh.nincore.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class NinCommandHelpHandler implements SubCommandExecutor
{
    private SubCommand subCommand;

    @NotNull
    @Override
    public SubCommandExecutor init(SubCommand subCommand)
    {
        this.subCommand = subCommand;
        return this;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args)
    {
        Bukkit.getLogger().info("inside help");
        if(args.length == 1) // Sub command supplied.
        {
            SubCommand subCmd = subCommand.getParentCommand().getSubCommand(args[0]);

            if(subCmd == null)
            {
                MessageUtil.sendCommandHelp(sender, subCommand.getParentCommand());
            }
            else
            {
                MessageUtil.sendCommandHelp(sender, subCommand.getParentCommand(), subCmd);
            }
        }
        else
        {
            MessageUtil.sendCommandHelp(sender, subCommand.getParentCommand());
        }
    }
}
