package com.m1zark.aurapokemon.Utils.particles;

import com.flowpowered.math.vector.Vector3d;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.scheduler.Task;

import java.util.function.Consumer;

public class HeartTask implements Consumer<Task> {
    private double theta = 0.0;
    private double radius = 3.0;
    private double insideRadius = 1.0;
    private double degrees = 1.5;

    @Override
    public void accept(Task t) {
        Sponge.getServer().getWorlds().forEach(world -> {
            for(Entity entity : world.getEntities()) {
                if(entity instanceof EntityPixelmon) {
                    EntityPixelmon pixelmon = (EntityPixelmon) entity;

                    if(pixelmon.getPokemonData().getPersistentData().getString("particle").equals("hearts")) {
                        double playerX;
                        double playerZ;
                        if (this.theta >= 360.0) continue;
                        playerZ = entity.getLocation().getPosition().getZ() + this.insideRadius * Math.sin(this.theta);
                        playerX = entity.getLocation().getPosition().getX() + this.insideRadius * Math.cos(this.theta);
                        ParticleEffect hearts = ParticleEffect.builder().type(ParticleTypes.HEART).quantity(1).velocity(new Vector3d(0.0, 0.1, 0.0)).build();
                        world.spawnParticles(hearts, new Vector3d(playerX, entity.getLocation().getPosition().getY() + 1.0, playerZ));

                        this.theta += this.degrees;
                        if (this.theta == 360.0) this.theta = 0.0;
                    }
                }
            }
        });
    }
}
