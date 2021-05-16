package com.m1zark.aurapokemon.Utils.particles;

import com.flowpowered.math.vector.Vector3d;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Color;
import org.spongepowered.api.world.Location;

import java.util.function.Consumer;

public class PoseidonTask implements Consumer<Task> {
    private double theta = 0.0;
    private double radius = 1.0;
    private double height = 0.0;
    private static double angle = 0.15707963267948966;
    private double height2 = 1.0;
    private double radius2 = 1.0;

    @Override
    public void accept(Task t) {
        /*
        Sponge.getServer().getWorlds().forEach(world -> {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof EntityPixelmon && entity.get(EntityDataKeys.PARTICLE_ID).isPresent()) {
                    if (entity.get(EntityDataKeys.PARTICLE_ID).get().equals("poseidon")) {
                        Location location = entity.getLocation();
                        Location location2 = entity.getLocation();

                        int n = 1;
                        while (n < 3) {
                            double d = 180 * n;

                            Vector3d vector = new Vector3d(Math.cos((double)this.theta * angle + Math.toRadians(d)) * this.radius, this.height, Math.sin((double)this.theta * angle + Math.toRadians(d)) * this.radius);
                            world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.DRIP_WATER).quantity(1).build(), location.getPosition().add(vector));
                            location.sub(vector);

                            vector = new Vector3d(Math.cos((double)this.theta * angle + Math.toRadians(d)) * this.radius2, this.height2, Math.sin((double)this.theta * angle + Math.toRadians(d)) * this.radius2);
                            world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.REDSTONE_DUST).option(ParticleOptions.COLOR, Color.ofRgb(0, 0, 255)).build(), location2.getPosition().add(vector));
                            location2.sub(vector);

                            ++n;
                        }

                        this.height += 0.05;
                        this.height2 += 0.05;
                        if (this.height >= 2.5) {
                            this.height = 0.0;
                            this.radius = 1.0;
                        }
                        if (this.height2 >= 2.5) {
                            this.height2 = 0.0;
                            this.radius2 = 1.0;
                        }
                        if (this.height2 >= 2.1) {
                            this.radius2 -= 0.10000000149011612;
                        }
                        if (this.height >= 2.1) {
                            this.radius -= 0.10000000149011612;
                        }

                        this.theta += 1;
                    }
                }
            }
        });
        */
    }
}
