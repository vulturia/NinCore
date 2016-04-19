package me.ninjoh.nincore.command.executors;

import me.ninjoh.nincore.api.command.executors.NinSubCommandExecutor;
import me.ninjoh.nincore.api.exceptions.TechnicalException;
import me.ninjoh.nincore.api.exceptions.ValidationException;
import me.ninjoh.nincore.api.messaging.MessageColor;
import me.ninjoh.nincore.api.util.PluginUtil;
import me.ninjoh.nincore.command.NcCommand;
import me.ninjoh.nincore.entity.NcCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;


public class ReloadSubCmdExecutor extends NinSubCommandExecutor
{

    @Override
    public void execute(CommandSender commandSender, String[] args) throws ValidationException, TechnicalException
    {
        NcCommandSender ncCommandSender = NcCommandSender.fromCommandSender(commandSender);
        Plugin plugin = ((NcCommand) (this.getNinSubCommand().getParentCommand())).getPlugin();


        ncCommandSender.sendMessage(MessageColor.BAD, "Please note that using this command is highly discouraged and may" +
                " cause issues when using some plugins.", plugin);

        ncCommandSender.sendMessage(MessageColor.BAD, "Please only use this command in development environments.", plugin);


        PluginUtil.reload(plugin);
    }
}
