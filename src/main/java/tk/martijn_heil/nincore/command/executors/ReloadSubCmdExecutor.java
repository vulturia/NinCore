package tk.martijn_heil.nincore.command.executors;

import tk.martijn_heil.nincore.command.NcCommand;
import tk.martijn_heil.nincore.entity.NcCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import tk.martijn_heil.nincore.api.command.executors.NinSubCommandExecutor;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;
import tk.martijn_heil.nincore.api.messaging.MessageColor;
import tk.martijn_heil.nincore.api.util.PluginUtil;


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


        try
        {
            PluginUtil.reload(plugin);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
