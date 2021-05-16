/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  lombok.NonNull
 *  org.spongepowered.api.event.cause.Cause
 */
package com.m1zark.aurapokemon.events;

import com.m1zark.aurapokemon.events.BaseEvent;
import lombok.NonNull;
import org.spongepowered.api.event.cause.Cause;

public class AuraEvent
extends BaseEvent {
    public final String pokemonName;
    public final String aura;
    public final String biomeName;
    @NonNull
    private final Cause cause;

    @Override
    public Cause getCause() {
        return this.cause;
    }

    public String getPokemonName() {
        return this.pokemonName;
    }

    public String getAura() {
        return this.aura;
    }

    public String getBiomeName() {
        return this.biomeName;
    }

    public AuraEvent(String pokemonName, String aura, String biomeName, @NonNull Cause cause) {
        if (cause == null) {
            throw new NullPointerException("cause is marked @NonNull but is null");
        }
        this.pokemonName = pokemonName;
        this.aura = aura;
        this.biomeName = biomeName;
        this.cause = cause;
    }
}

