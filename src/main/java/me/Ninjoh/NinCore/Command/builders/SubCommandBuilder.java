package me.Ninjoh.NinCore.command.builders;


import me.Ninjoh.NinCore.command.Argument;
import me.Ninjoh.NinCore.command.Command;
import me.Ninjoh.NinCore.command.SubCommand;
import me.Ninjoh.NinCore.interfaces.SubCommandExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SubCommandBuilder
{
    private String Name; // Required && Always lowercase.
    @NotNull
    private List<String> Aliases = new ArrayList<>(); // Optional && All entries always lowercase.
    private String Usage; // Optional
    private String[] Description; // Optional
    @NotNull
    private List<Argument> arguments = new ArrayList<>();
    private String RequiredPermission; // Optional.
    private SubCommandExecutor Executor; // Required
    private Command parentCommand;


    public SubCommandBuilder()
    {

    }

    /**
     * Set this sub command's name
     *
     * @param name The name for this sub command.
     * @return {@link SubCommandBuilder}, for method chaining.
     */
    @NotNull
    public SubCommandBuilder setName(@NotNull String name) // Required
    {
        this.Name = name.toLowerCase();
        return this;
    }

    /**
     * Add an alias for this sub command.
     *
     * @param alias The alias to add to this sub command.
     * @return {@link SubCommandBuilder}, for method chaining.
     */
    @NotNull
    public SubCommandBuilder addAlias(@NotNull String alias) // Optional
    {
        Aliases.add(alias.toLowerCase());
        return this;
    }

    /**
     * Set this sub comman's usage.
     *
     * @param usage The usage for this sub command.
     * @return {@link SubCommandBuilder}, for method chaining.
     */
    @NotNull
    public SubCommandBuilder setUsage(String usage) // Optional
    {
        this.Usage = usage;
        return this;
    }

    /**
     * Set this sub command's description
     *
     * @param description the description to set.
     * @return {@link SubCommandBuilder}, for method chaining.
     */
    @NotNull
    public SubCommandBuilder setDescription(String[] description) // Optional
    {
        this.Description = description;
        return this;
    }

    /**
     * Set this sub command's required permission.
     *
     * @param permission the permission to set.
     * @return {@link SubCommandBuilder}, for method chaining.
     */
    @NotNull
    public SubCommandBuilder setRequiredPermission(String permission) // Optional
    {
        this.RequiredPermission = permission;
        return this;
    }


    /**
     * Set this sub command's {@link SubCommandExecutor}.
     *
     * @param executor the {@link SubCommandExecutor} to set.
     * @return {@link SubCommandBuilder}, for method chaining.
     */
    @NotNull
    public SubCommandBuilder setExecutor(SubCommandExecutor executor) // Required
    {
        this.Executor = executor;
        return this;
    }

    @NotNull
    public SubCommandBuilder addArgument(Argument arg)
    {
        this.arguments.add(arg);
        return this;
    }


    @NotNull
    public SubCommandBuilder setParentCommand(Command cmd)
    {
        this.parentCommand = cmd;
        return this;
    }


    /**
     * Construct the {@link SubCommand}.
     *
     * @return The constructed {@link SubCommand}.
     */
    @NotNull
    public SubCommand construct()
    {
        return new SubCommand(this.Name, this.Aliases,
                this.Description, this.RequiredPermission,this.arguments, this.Executor, this.parentCommand);
    }
}
