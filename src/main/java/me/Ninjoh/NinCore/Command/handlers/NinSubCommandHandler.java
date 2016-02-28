package me.ninjoh.nincore.command.handlers;


import me.ninjoh.nincore.command.SubCommand;
import me.ninjoh.nincore.exceptions.NotEnoughArgumentsException;
import me.ninjoh.nincore.exceptions.TooManyArgumentsException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class NinSubCommandHandler
{
    private SubCommand subCommand;



    public NinSubCommandHandler(SubCommand subCommand)
    {
        this.subCommand = subCommand;
    }


    public void handle(@NotNull CommandSender sender, @NotNull String[] args, Command command, SubCommand subCommand)
    {
        try
        {
            if(this.subCommand.hasArguments())
            {
                //noinspection ConstantConditions
                if(args.length < this.subCommand.getArguments().size()) // Too less arguments
                {
                    throw new NotEnoughArgumentsException(sender);
                }
                else if(args.length > this.subCommand.getArguments().size()) // Too many arguments
                {
                    throw new TooManyArgumentsException(sender);
                }
                // If the right amount of arguments is supplied.

                boolean success = true;
                int count = 0;
                for (String arg : args)
                {
                    // If argument validation fails.
                    if(!this.subCommand.getArguments().get(count).getArgumentDataType().validate(arg))
                    {
                        success = false;
                        this.subCommand.getArguments().get(count).getArgumentDataType().throwException(sender, arg);
                    }

                    count++;
                }

                if(!success)
                {
                    return;
                }
            }


            // If all validation passes, execute the command.
            this.subCommand.getExecutor().execute(sender, args);
        }
        catch(@NotNull TooManyArgumentsException | NotEnoughArgumentsException e)
        {
            //
        }
    }
}
