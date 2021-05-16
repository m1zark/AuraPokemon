package com.m1zark.aurapokemon.Utils.particles;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public enum EnumParticles {
    Storm("storm", 50, TimeUnit.MILLISECONDS),
    Hearts("hearts", 200, TimeUnit.MILLISECONDS),
    Poseidon("poseidon", 80, TimeUnit.MILLISECONDS),
    BloodHelix("bloodhelix", 75, TimeUnit.MILLISECONDS),
    RainbowHelix("rainbowhelix", 75, TimeUnit.MILLISECONDS),
    FrostLord("frostlord", 50, TimeUnit.MILLISECONDS),
    Apocalyptic("apocalyptic", 80, TimeUnit.MILLISECONDS),
    Shiny("shiny", 1000, TimeUnit.MILLISECONDS),;

    private String id;
    private long time;
    private TimeUnit unit;

    EnumParticles(String id, long time, TimeUnit unit) {
        this.id = id;
        this.time = time;
        this.unit = unit;
    }

    public String getId() { return this.id; }

    public long getTime() { return this.time; }

    public TimeUnit getUnit() { return this.unit; }

    public static EnumParticles randomParticle() {
        return EnumParticles.values()[new Random().nextInt(EnumParticles.values().length)];
    }
}
