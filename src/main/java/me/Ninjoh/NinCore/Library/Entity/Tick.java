package me.Ninjoh.NinCore.Library.Entity;


import org.jetbrains.annotations.NotNull;

public class Tick
{
    private long value;


    public Tick(long value)
    {
        this.value = value;
    }


    @NotNull
    public static Tick valueOf(long value)
    {
        return new Tick(value);
    }


    public long toLong()
    {
        return value;
    }


    @NotNull
    public String toString()
    {
        return Long.toString(value);
    }


    @NotNull
    public Tick toMilliseconds()
    {
        value = value/20/1000;
        return this;
    }


    @NotNull
    public Tick toSeconds()
    {
        value = value/20;
        return this;
    }


    @NotNull
    public Tick toMinutes()
    {
        value = value/20/60;
        return this;
    }


    @NotNull
    public Tick toHours()
    {
        value = value/20/60/60;
        return this;
    }


    @NotNull
    public Tick toDays()
    {
        value = value/20/60/60/24;
        return this;
    }


    @NotNull
    public Tick add(@NotNull Tick value)
    {
        this.value += value.toLong();
        return this;
    }

    @NotNull
    public Tick subtract(@NotNull Tick value)
    {
        this.value -= value.toLong();
        return this;
    }


    @NotNull
    public Tick multiply(@NotNull Tick value)
    {
        this.value *= value.toLong();
        return this;
    }


    @NotNull
    public Tick divide(@NotNull Tick value)
    {
        this.value /= value.toLong();
        return this;
    }
}
