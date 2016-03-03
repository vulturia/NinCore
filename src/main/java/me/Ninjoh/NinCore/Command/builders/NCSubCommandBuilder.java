package me.ninjoh.nincore.command.builders;


import me.ninjoh.nincore.command.NCArgument;
import me.ninjoh.nincore.command.NCCommand;
import me.ninjoh.nincore.command.NCSubCommand;
import me.ninjoh.nincore.interfaces.SubCommandExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NCSubCommandBuilder
{
    private String Name; // Required && Always lowercase.
    @NotNull
    private List<String> Aliases = new ArrayList<>(); // Optional && All entries always lowercase.
    private String Usage; // Optional
    private String[] Description; // Optional
    @NotNull
    private List<NCArgument> NCArguments = new ArrayList<>();
    private String RequiredPermission; // Optional.
    private SubCommandExecutor Executor; // Required
    private NCCommand parentNCCommand;


    public NCSubCommandBuilder()
    {

    }

    /**
     * Set this sub command's name
     *
     * @param name The name for this sub command.
     * @return {@link NCSubCommandBuilder}, for method chaining.
     */
    @NotNull
    public NCSubCommandBuilder setName(@NotNull String name) // Required
    {
        this.Name = name.toLowerCase();
        return this;
    }

    /**
     * Add an alias for this sub command.
     *
     * @param alias The alias to add to this sub command.
     * @return {@link NCSubCommandBuilder}, for method chaining.
     */
    @NotNull
    public NCSubCommandBuilder addAlias(@NotNull String alias) // Optional
    {
        Aliases.add(alias.toLowerCase());
        return this;
    }

    /**
     * Set this sub comman's usage.
     *
     * @param usage The usage for this sub command.
     * @return {@link NCSubCommandBuilder}, for method chaining.
     */
    @NotNull
    public NCSubCommandBuilder setUsage(String usage) // Optional
    {
        this.Usage = usage;
        return this;
    }

    /**
     * Set this sub command's description
     *
     * @param description the description to set.
     * @return {@link NCSubCommandBuilder}, for method chaining.
     */
    @NotNull
    public NCSubCommandBuilder setDescription(String[] description) // Optional
    {
        this.Description = description;
        return this;
    }

    /**
     * Set this sub command's required permission.
     *
     * @param permission the permission to set.
     * @return {@link NCSubCommandBuilder}, for method chaining.
     */
    @NotNull
    public NCSubCommandBuilder setRequiredPermission(String permission) // Optional
    {
        this.RequiredPermission = permission;
        return this;
    }


    /**
     * Set this sub command's {@link SubCommandExecutor}.
     *
     * @param executor the {@link SubCommandExecutor} to set.
     * @return {@link NCSubCommandBuilder}, for method chaining.
     */
    @NotNull
    public NCSubCommandBuilder setExecutor(SubCommandExecutor executor) // Required
    {
        this.Executor = executor;
        return this;
    }

    @NotNull
    public NCSubCommandBuilder addArgument(NCArgument arg)
    {
        this.NCArguments.add(arg);
        return this;
    }


    @NotNull
    public NCSubCommandBuilder setParentNCCommand(NCCommand cmd)
    {
        this.parentNCCommand = cmd;
        return this;
    }


    /**
     * Construct the {@link NCSubCommand}.
     *
     * @return The constructed {@link NCSubCommand}.
     */
    @NotNull
    public NCSubCommand construct()
    {
        return new NCSubCommand(this.Name, this.Aliases,
                this.Description, this.RequiredPermission,this.NCArguments, this.Executor, this.parentNCCommand);
    }
}
