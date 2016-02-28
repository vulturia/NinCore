package me.ninjoh.nincore.command;


import me.ninjoh.nincore.interfaces.IArgumentDataType;
import me.ninjoh.nincore.util.TranslationUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

public class Argument
{
    private ArgumentType argumentType;
    private IArgumentDataType argumentDataType;
    private String[] name;
    private boolean isCustomArgumentDataType;


    // Custom argument data type.
    public Argument(String[] name, ArgumentType argumentType, IArgumentDataType argumentDataType)
    {
        this.name = name;
        this.isCustomArgumentDataType = true;
        this.argumentType = argumentType;
        this.argumentDataType = argumentDataType;
    }

    // Non custom argument data type.
    public Argument(String[] name, ArgumentType argumentType, ArgumentDataType argumentDataType)
    {
        this.name = name;
        this.isCustomArgumentDataType = false;
        this.argumentType = argumentType;
        this.argumentDataType = argumentDataType;
    }


    @NotNull
    public String getName()
    {
        return TranslationUtils.getStaticMsg(ResourceBundle.getBundle(name[1]), name[0]);
    }


    public boolean isRequired()
    {
        return this.argumentType == ArgumentType.REQUIRED;
    }


    public boolean isCustomArgumentDataType()
    {
        return this.isCustomArgumentDataType;
    }


    public ArgumentType getArgumentType()
    {
        return this.argumentType;
    }


    public IArgumentDataType getArgumentDataType()
    {
        return this.argumentDataType;
    }
}
