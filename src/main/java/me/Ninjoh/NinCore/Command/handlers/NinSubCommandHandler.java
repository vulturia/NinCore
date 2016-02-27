package me.Ninjoh.NinCore.command.handlers;


import me.Ninjoh.NinCore.command.Command;
import me.Ninjoh.NinCore.command.SubCommand;
import me.Ninjoh.NinCore.exceptions.NotEnoughArgumentsException;
import me.Ninjoh.NinCore.exceptions.TooManyArgumentsException;
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
            if(args.length < this.subCommand.getArguments().size()) // Too less arguments
            {
                throw new NotEnoughArgumentsException(sender);
            }
            else if(args.length > this.subCommand.getArguments().size()) // Too many arguments
            {
                throw new TooManyArgumentsException(sender);
            }
            // If the right amount of arguments is supplied.

            int count = 0;
            for (String arg : args)
            {
                // If argument validation fails.
                if(!this.subCommand.getArguments().get(count).getArgumentDataType().validate(arg))
                {

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
