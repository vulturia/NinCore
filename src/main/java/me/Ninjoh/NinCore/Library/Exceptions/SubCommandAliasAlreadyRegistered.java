package me.Ninjoh.NinCore.Library.Exceptions;


public class SubCommandAliasAlreadyRegistered extends Exception
{
    public SubCommandAliasAlreadyRegistered(String alias)
    {
        super(alias);
    }
}
