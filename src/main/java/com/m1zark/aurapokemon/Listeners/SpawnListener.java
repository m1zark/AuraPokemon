package com.m1zark.aurapokemon.Listeners;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.forms.EnumMega;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

public class SpawnListener {
    @Listener
    public void onPixelmonSpawn(SpawnEntityEvent event) {
        event.getEntities().forEach(entity -> {
            if (entity instanceof EntityPixelmon) {
                EntityPixelmon pixelmon = (EntityPixelmon) entity;
                if (!pixelmon.hasOwner() && pixelmon.getOwnerId() == null && pixelmon.getOwner() == null && pixelmon.battleController == null && pixelmon.getTrainer() == null && pixelmon.getFormEnum() != EnumMega.Mega && !pixelmon.isBossPokemon()) {

                }
            }
        });
    }
}