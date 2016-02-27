package me.Ninjoh.NinCore.command;


import me.Ninjoh.NinCore.interfaces.IArgumentDataType;

public class Argument
{
    private ArgumentType argumentType;
    private IArgumentDataType argumentDataType;
    private boolean isCustomArgumentDataType;


    // Custom argument data type.
    public Argument(ArgumentType argumentType, IArgumentDataType argumentDataType)
    {
        this.isCustomArgumentDataType = true;
        this.argumentType = argumentType;
        this.argumentDataType = argumentDataType;
    }

    // Non custom argument data type.
    public Argument(ArgumentType argumentType, ArgumentDataType argumentDataType)
    {
        this.isCustomArgumentDataType = false;
        this.argumentType = argumentType;
        this.argumentDataType = argumentDataType;
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
