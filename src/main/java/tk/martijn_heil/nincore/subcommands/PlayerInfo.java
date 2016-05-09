package tk.martijn_heil.nincore.subcommands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.martijn_heil.nincore.api.command.executors.NinSubCommandExecutor;
import tk.martijn_heil.nincore.api.entity.NinOnlinePlayer;
import tk.martijn_heil.nincore.api.exceptions.TechnicalException;
import tk.martijn_heil.nincore.api.exceptions.ValidationException;
import tk.martijn_heil.nincore.api.exceptions.validationexceptions.NotEnoughArgumentsException;
import tk.martijn_heil.nincore.api.exceptions.validationexceptions.PlayerNotFoundException;
import tk.martijn_heil.nincore.api.exceptions.validationexceptions.TooManyArgumentsException;

public class PlayerInfo extends NinSubCommandExecutor
{

    @Override
    public void execute(CommandSender sender, String[] args) throws ValidationException, TechnicalException
    {
        if(args.length > 1) throw new TooManyArgumentsException(sender);

        if(!(sender instanceof Player) && args.length < 1) throw new NotEnoughArgumentsException(sender);


        Player p;
        if(args.length == 1)
        {
            p = Bukkit.getPlayer(args[0]);
        }
        else
        {
            p = (Player) sender;
        }

        if(p == null) throw new PlayerNotFoundException(sender);
        NinOnlinePlayer np = NinOnlinePlayer.fromPlayer(p);

        sender.sendMessage(ChatColor.GOLD + "Debug information for player: " + p.getName());
        sender.sendMessage(ChatColor.GOLD + "Minecraft Locale: " + np.getMinecraftLocale().name());
    }
}
