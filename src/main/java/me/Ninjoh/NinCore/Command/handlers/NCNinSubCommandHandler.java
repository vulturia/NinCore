package me.ninjoh.nincore.command.handlers;


import me.ninjoh.nincore.api.command.NinSubCommand;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.playerexceptions.NotEnoughArgumentsException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class NCNinSubCommandHandler
{
    private NinSubCommand subCommand;



    public NCNinSubCommandHandler(NinSubCommand subCommand)
    {
        this.subCommand = subCommand;
    }


    public void handle(@NotNull CommandSender sender, String[] args, Command command)
    {

        if (this.subCommand.hasArguments())
        {
            //noinspection ConstantConditions
            if (args.length < this.subCommand.getRequiredArguments().size()) // Too less arguments
            {
                new NotEnoughArgumentsException(sender);
                return;
            }
            else //noinspection ConstantConditions
                if (args.length > this.subCommand.getArguments().size()) // Too many arguments
                {
                    new NotEnoughArgumentsException(sender);
                }
            // If the right amount of arguments is supplied.

            boolean success = true;
            int count = 0;
            for (String arg : args)
            {
                // If argument validation fails.
                //noinspection ConstantConditions
                if (!this.subCommand.getArgumentByIndex(count).getArgumentDataType().validate(arg))
                {
                    success = false;
                    //noinspection ConstantConditions
                    this.subCommand.getArgumentByIndex(count).getArgumentDataType().throwException(sender, arg);
                }

                count++;
            }

            if (!success)
            {
                return;
            }
        }


        // If all validation passes, execute the command.
        this.subCommand.getExecutor().execute(sender, args);
    }
}
