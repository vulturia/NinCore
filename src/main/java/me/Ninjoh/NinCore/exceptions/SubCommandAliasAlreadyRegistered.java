package me.ninjoh.nincore.exceptions;


public class SubCommandAliasAlreadyRegistered extends Exception
{
    public SubCommandAliasAlreadyRegistered(String alias)
    {
        super(alias);
    }
}
