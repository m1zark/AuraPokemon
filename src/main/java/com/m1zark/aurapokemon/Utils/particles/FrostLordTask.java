package com.m1zark.aurapokemon.Utils.particles;

import com.flowpowered.math.vector.Vector3d;
import com.m1zark.aurapokemon.Utils.LocationUtils;
import com.m1zark.aurapokemon.Utils.MathUtils;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Color;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.function.Consumer;

public class FrostLordTask implements Consumer<Task> {
    private static int particles = 6;
    private static double angularVelocityY = 0.3141592653589793;
    private float radius = 2.0f;
    private double x;
    private double z;
    private double y = 0.0;
    private Location location;
    private static double phi = 0;

    @Override
    public void accept(Task t) {
        /*
        Sponge.getServer().getWorlds().forEach(world -> {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof EntityPixelmon && entity.get(EntityDataKeys.PARTICLE_ID).isPresent()) {
                    if (entity.get(EntityDataKeys.PARTICLE_ID).get().equals("bloodhelix")) {
                        this.location = entity.getLocation().add(0.0, 0.5, 0.0);
                        double d = phi * angularVelocityY;
                        int n = 0;
                        phi += 1;
                        while (n < particles) {
                            this.x = Math.cos((double)n * Math.PI / 2.0) * (double)this.radius;
                            this.z = Math.sin((double)n * Math.PI / 2.0) * (double)this.radius;
                            Vector3d vector = new Vector3d(this.x, this.y, this.z);
                            vector = MathUtils.rotateAroundAxisY(vector, d);
                            world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.SNOW_SHOVEL).quantity(1).build(), this.location.getPosition().add(vector));
                            this.location.sub(vector);
                            ++n;
                        }
                        this.y += 0.05000000074505806;
                        this.radius -= 0.05f;

                        if (this.y >= 3.0) {
                            this.y = 0.0;
                            this.radius = 2.5f;
                        }
                    }
                }
            }
        });
        */
    }
}
