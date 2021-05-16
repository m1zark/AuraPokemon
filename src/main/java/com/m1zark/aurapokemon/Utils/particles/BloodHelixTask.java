package com.m1zark.aurapokemon.Utils.particles;

import com.m1zark.aurapokemon.Utils.MathUtils;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Color;

import java.util.function.Consumer;

public class BloodHelixTask implements Consumer<Task> {
    private static double phi = 0;

    @Override
    public void accept(Task t) {
        /*
        Sponge.getServer().getWorlds().forEach(world -> {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof EntityPixelmon && entity.get(EntityDataKeys.PARTICLE_ID).isPresent()) {
                    if (entity.get(EntityDataKeys.PARTICLE_ID).get().equals("bloodhelix")) {
                        phi = phi + Math.PI / 16;
                        double x, y, z;
                        for (double ti = 0; ti <= 2 * Math.PI; ti = ti + Math.PI / 16) {
                            for (double i = 0; i <= 1; i = i + 1) {
                                x = 0.4 * (2 * Math.PI - ti) * 0.5 * Math.cos(ti + phi + i * Math.PI);
                                y = 0.5 * ti;
                                z = 0.4 * (2 * Math.PI - ti) * 0.5 * Math.sin(ti + phi + i * Math.PI);

                                world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.REDSTONE_DUST)
                                        .option(ParticleOptions.COLOR, Color.ofRgb(MathUtils.randomRange(100, 255), 0, 0))
                                        .build(), entity.getLocation().getPosition().add(x, y, z));
                            }
                        }
                    }
                }
            }
        });
        */
    }
}
