package me.Ninjoh.NinCore.Library.Entity;


public class Tick
{
    private long value;


    public Tick(long value)
    {
        this.value = value;
    }


    public static Tick valueOf(long value)
    {
        return new Tick(value);
    }


    public long toLong()
    {
        return value;
    }


    public String toString()
    {
        return Long.toString(value);
    }


    public Tick toMilliseconds()
    {
        value = value/20/1000;
        return this;
    }


    public Tick toSeconds()
    {
        value = value/20;
        return this;
    }


    public Tick toMinutes()
    {
        value = value/20/60;
        return this;
    }


    public Tick toHours()
    {
        value = value/20/60/60;
        return this;
    }


    public Tick toDays()
    {
        value = value/20/60/60/24;
        return this;
    }


    public Tick add(Tick value)
    {
        this.value += value.toLong();
        return this;
    }

    public Tick subtract(Tick value)
    {
        this.value -= value.toLong();
        return this;
    }


    public Tick multiply(Tick value)
    {
        this.value *= value.toLong();
        return this;
    }


    public Tick divide(Tick value)
    {
        this.value /= value.toLong();
        return this;
    }
}
