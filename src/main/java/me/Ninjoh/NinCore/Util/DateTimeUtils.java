package me.Ninjoh.NinCore.util;


public class DateTimeUtils
{
    public static long getUnixTime()
    {
        return System.currentTimeMillis() / 1000L;
    }
}
