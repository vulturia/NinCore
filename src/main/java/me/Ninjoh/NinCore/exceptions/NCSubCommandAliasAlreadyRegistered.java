package me.ninjoh.nincore.exceptions;


public class NCSubCommandAliasAlreadyRegistered extends Exception
{
    public NCSubCommandAliasAlreadyRegistered(String alias)
    {
        super(alias);
    }
}
