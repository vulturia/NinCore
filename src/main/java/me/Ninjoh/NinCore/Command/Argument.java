package me.Ninjoh.NinCore.command;


import me.Ninjoh.NinCore.interfaces.IArgumentDataType;
import me.Ninjoh.NinCore.util.TranslationUtils;

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
