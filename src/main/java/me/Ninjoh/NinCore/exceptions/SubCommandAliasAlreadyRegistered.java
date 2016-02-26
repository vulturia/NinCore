package me.Ninjoh.NinCore.exceptions;


public class SubCommandAliasAlreadyRegistered extends Exception
{
    public SubCommandAliasAlreadyRegistered(String alias)
    {
        super(alias);
    }
}
