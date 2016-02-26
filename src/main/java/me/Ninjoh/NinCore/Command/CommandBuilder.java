package me.Ninjoh.NinCore.command;


import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandBuilder
{
    private String Name; // Required
    private String Description; // Automatic
    private String Usage; // Automatic
    private String Permission; // Automatic
    private List<String> Aliases; // Automatic
    private List<SubCommand> SubCommands; // Optional
    private CommandExecutor Executor; // Required
    private JavaPlugin Plugin; // Required

    public CommandBuilder(JavaPlugin plugin)
    {
        this.Plugin = plugin;
    }

    // Case sensitive

    /**
     * Set this command's name.
     *
     * @param name The name for this command. Case sensitive
     * @return {@link SubCommandBuilder}, for method chaining.
     */
    @NotNull
    public CommandBuilder setName(String name)
    {
        this.Name = name;
        return this;
    }

    /**
     * Add a sub command to this command.
     *
     * @param subCommand The {@link SubCommand} to add.
     * @return {@link SubCommandBuilder}, for method chaining.
     */
    @NotNull
    public CommandBuilder addSubCommand(SubCommand subCommand)
    {
        this.SubCommands.add(subCommand);
        return this;
    }

    /**
     * Set this command's executor.
     *
     * @param executor This command's {@link CommandExecutor}
     * @return {@link SubCommandBuilder}, for method chaining
     */
    @NotNull
    public CommandBuilder setExecutor(CommandExecutor executor)
    {
        this.Executor = executor;
        return this;
    }

    /**
     * Construct the command.
     *
     * @return The constructed {@link Command}.
     */
    @NotNull
    public Command construct()
    {
        Command constructedCommand = new Command(this.Name, this.SubCommands, this.Plugin);
        constructedCommand.setExecutor(this.Executor);
        return constructedCommand;
    }
}
