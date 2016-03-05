package me.ninjoh.nincore.command;


import me.ninjoh.nincore.api.IArgumentDataType;
import me.ninjoh.nincore.api.command.NinArgument;
import me.ninjoh.nincore.api.command.NinArgumentDataType;
import me.ninjoh.nincore.api.command.NinArgumentType;
import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import me.ninjoh.nincore.api.util.TranslationUtils;

import java.util.ResourceBundle;

public class NCArgument implements NinArgument
{
    private NinArgumentType argumentType;
    private IArgumentDataType argumentDataType;
    private String[] name;
    private boolean isCustomArgumentDataType;


    // Custom argument data type.
    public NCArgument(String[] name, NinArgumentType argumentType, IArgumentDataType argumentDataType)
    {
        this.name = name;
        this.isCustomArgumentDataType = true;
        this.argumentType = argumentType;
        this.argumentDataType = argumentDataType;
    }

    // Non custom argument data type.
    public NCArgument(String[] name, NinArgumentType argumentType, NinArgumentDataType argumentDataType)
    {
        this.name = name;
        this.isCustomArgumentDataType = false;
        this.argumentType = argumentType;
        this.argumentDataType = argumentDataType;
    }


    @NotNull
    @Override
    public String getName()
    {
        return TranslationUtils.getStaticMsg(ResourceBundle.getBundle(name[1]), name[0]);
    }


    @Override
    public boolean isRequired()
    {
        return this.argumentType == NinArgumentType.REQUIRED;
    }

    @Override
    public boolean isCustomArgumentDataType()
    {
        return this.isCustomArgumentDataType;
    }


    @Override
    public NinArgumentType getArgumentType()
    {
        return this.argumentType;
    }


    @Override
    public IArgumentDataType getArgumentDataType()
    {
        return this.argumentDataType;
    }
}
