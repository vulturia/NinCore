package me.ninjoh.nincore.command.builders;


import me.ninjoh.nincore.command.NCCommand;
import me.ninjoh.nincore.command.NCSubCommand;
import me.ninjoh.nincore.interfaces.NinCommandExecutor;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NCCommandBuilder
{
    private String Name; // Required
    private String Description; // Automatic
    private String Usage; // Automatic
    private String Permission; // Automatic
    private List<String> Aliases; // Automatic
    private List<NCSubCommand> NCSubCommands; // Optional
    private NinCommandExecutor Executor; // Required


    // Case sensitive

    /**
     * Set this command's name.
     *
     * @param name The name for this command. Case sensitive
     * @return {@link NCSubCommandBuilder}, for method chaining.
     */
    @NotNull
    public NCCommandBuilder setName(String name)
    {
        this.Name = name;
        return this;
    }

    /**
     * Add a sub command to this command.
     *
     * @param NCSubCommand The {@link NCSubCommand} to add.
     * @return {@link NCSubCommandBuilder}, for method chaining.
     */
    @NotNull
    public NCCommandBuilder addSubCommand(NCSubCommand NCSubCommand)
    {
        this.NCSubCommands.add(NCSubCommand);
        return this;
    }

    /**
     * Set this command's executor.
     *
     * @param executor This command's {@link CommandExecutor}
     * @return {@link NCSubCommandBuilder}, for method chaining
     */
    @NotNull
    public NCCommandBuilder setExecutor(NinCommandExecutor executor)
    {
        this.Executor = executor;
        return this;
    }

    /**
     * Construct the command.
     *
     * @return The constructed {@link NCCommand}.
     */
    @NotNull
    public NCCommand construct()
    {
        NCCommand constructedNCCommand = new NCCommand(this.Name, this.NCSubCommands, null, this.Executor);
        constructedNCCommand.setExecutor(this.Executor);
        return constructedNCCommand;
    }
}
