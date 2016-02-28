package me.Ninjoh.NinCore.interfaces;


import org.bukkit.command.CommandSender;

public interface IArgumentDataType
{
    boolean validate(String value);
    void throwException(CommandSender target, String actualValue);
    String name();
    String getHumanFriendlyName();
}
