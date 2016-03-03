package me.ninjoh.nincore.command.handlers;


import me.ninjoh.nincore.command.NCSubCommand;
import me.ninjoh.nincore.exceptions.NCNotEnoughArgumentsException;
import me.ninjoh.nincore.exceptions.NCTooManyArgumentsException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class NCNinSubCommandHandler
{
    private NCSubCommand NCSubCommand;



    public NCNinSubCommandHandler(NCSubCommand NCSubCommand)
    {
        this.NCSubCommand = NCSubCommand;
    }


    public void handle(@NotNull CommandSender sender, String[] args, Command command, NCSubCommand NCSubCommand)
    {
        try
        {
            if(this.NCSubCommand.hasArguments())
            {
                //noinspection ConstantConditions
                if(args.length < this.NCSubCommand.getRequiredArguments().size()) // Too less arguments
                {
                    throw new NCNotEnoughArgumentsException(sender);
                }
                else //noinspection ConstantConditions
                    if(args.length > this.NCSubCommand.getNCArguments().size()) // Too many arguments
                {
                    throw new NCTooManyArgumentsException(sender);
                }
                // If the right amount of arguments is supplied.

                boolean success = true;
                    int count = 0;
                    for (String arg : args)
                    {
                        // If argument validation fails.
                        //noinspection ConstantConditions
                        if(!this.NCSubCommand.getArgumentByIndex(count).getArgumentDataType().validate(arg))
                        {
                            success = false;
                            //noinspection ConstantConditions
                            this.NCSubCommand.getArgumentByIndex(count).getArgumentDataType().throwException(sender, arg);
                        }

                        count++;
                    }

                if(!success)
                {
                    return;
                }
            }


            // If all validation passes, execute the command.
            this.NCSubCommand.getExecutor().execute(sender, args);
        }
        catch(@NotNull NCTooManyArgumentsException | NCNotEnoughArgumentsException e)
        {
            //
        }
    }
}
