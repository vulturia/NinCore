package me.ninjoh.nincore;


import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;

/**
 * WARNING: Ticks to time calculations are based on 20 ticks per second.
 * Ideally Minecraft has 20 ticks per seconds. But with lag it CAN be less than 20 ticks per second
 * and thus these calculations will not be accurate when Minecraft does not have 20 ticks per second.
 *
 */
public class NCTick
{
    private long value;


    public NCTick(long value)
    {
        this.value = value;
    }


    @NotNull
    public static NCTick valueOf(long value)
    {
        return new NCTick(value);
    }


    public long toLong()
    {
        return value;
    }


    @NotNull
    @Override
    public String toString()
    {
        return Long.toString(value);
    }


    @NotNull
    public NCTick toMilliseconds()
    {
        value = value/20/1000;
        return this;
    }


    @NotNull
    public NCTick toSeconds()
    {
        value = value/20;
        return this;
    }


    @NotNull
    public NCTick toMinutes()
    {
        value = value/20/60;
        return this;
    }


    @NotNull
    public NCTick toHours()
    {
        value = value/20/60/60;
        return this;
    }


    @NotNull
    public NCTick toDays()
    {
        value = value/20/60/60/24;
        return this;
    }


    @NotNull
    public NCTick add(@NotNull NCTick value)
    {
        this.value += value.toLong();
        return this;
    }

    @NotNull
    public NCTick subtract(@NotNull NCTick value)
    {
        this.value -= value.toLong();
        return this;
    }


    @NotNull
    public NCTick multiply(@NotNull NCTick value)
    {
        this.value *= value.toLong();
        return this;
    }


    @NotNull
    public NCTick divide(@NotNull NCTick value)
    {
        this.value /= value.toLong();
        return this;
    }
}
