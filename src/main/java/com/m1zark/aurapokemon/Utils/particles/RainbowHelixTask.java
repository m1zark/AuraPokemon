package com.m1zark.aurapokemon.Utils.particles;

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

public class RainbowHelixTask implements Consumer<Task> {
    private static double phi = 0;
    private static int R, G, B = 20;
    private static int time = 59;

    @Override
    public void accept(Task t) {
        Sponge.getServer().getWorlds().forEach(world -> {
            for (Player entity : world.getPlayers()) {
                //if (entity instanceof EntityPixelmon && entity.get(EntityDataKeys.PARTICLE_ID).isPresent()) {
                    //if (entity.get(EntityDataKeys.PARTICLE_ID).get().equals("rainbowhelix")) {
                        phi = phi + Math.PI / 16;
                        double x, y, z;
                        for (double ti = 0; ti <= 2 * Math.PI; ti = ti + Math.PI / 16) {
                            for (double i = 0; i <= 1; i = i + 1) {
                                x = 0.4 * (2 * Math.PI - ti) * 0.5 * Math.cos(ti + phi + i * Math.PI);
                                y = 0.5 * ti;
                                z = 0.4 * (2 * Math.PI - ti) * 0.5 * Math.sin(ti + phi + i * Math.PI);

                                world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.REDSTONE_DUST).option(ParticleOptions.COLOR, getColor()).build(), entity.getLocation().getPosition().add(x, y, z));
                            }
                        }
                    //}
                //}
            }
        });
    }

    private static Color getColor() {
        switch (time) {
            case 59:
                R = 255;
                G = 0;
                B = 0;
                time -= 1;
                break;
            case 58:
                R = 255;
                G = 68;
                B = 0;
                time -= 1;
                break;
            case 57:
                R = 255;
                G = 111;
                B = 0;
                time -= 1;
                break;
            case 56:
                R = 255;
                G = 171;
                B = 0;
                time -= 1;
                break;
            case 55:
                R = 255;
                G = 255;
                B = 0;
                time -= 1;
                break;
            case 54:
                R = 188;
                G = 255;
                B = 0;
                time -= 1;
                break;
            case 53:
                R = 128;
                G = 255;
                B = 0;
                time -= 1;
                break;
            case 52:
                R = 43;
                G = 255;
                B = 0;
                time -= 1;
                break;
            case 51:
                R = 0;
                G = 255;
                B = 9;
                time -= 1;
                break;
            case 50:
                R = 0;
                G = 255;
                B = 51;
                time -= 1;
                break;
            case 49:
                R = 0;
                G = 255;
                B = 111;
                time -= 1;
                break;
            case 48:
                R = 0;
                G = 255;
                B = 162;
                time -= 1;
                break;
            case 47:
                R = 0;
                G = 255;
                B = 230;
                time -= 1;
                break;
            case 46:
                R = 0;
                G = 239;
                B = 255;
                time -= 1;
                break;
            case 45:
                R = 0;
                G = 196;
                B = 255;
                time -= 1;
                break;
            case 44:
                R = 0;
                G = 173;
                B = 255;
                time -= 1;
                break;
            case 43:
                R = 0;
                G = 162;
                B = 255;
                time -= 1;
                break;
            case 42:
                R = 0;
                G = 137;
                B = 255;
                time -= 1;
                break;
            case 41:
                R = 0;
                G = 100;
                B = 255;
                time -= 1;
                break;
            case 40:
                R = 0;
                G = 77;
                B = 255;
                time -= 1;
                break;
            case 39:
                R = 0;
                G = 34;
                B = 255;
                time -= 1;
                break;
            case 38:
                R = 17;
                G = 0;
                B = 255;
                time -= 1;
                break;
            case 37:
                R = 37;
                G = 0;
                B = 255;
                time -= 1;
                break;
            case 36:
                R = 68;
                G = 0;
                B = 255;
                time -= 1;
                break;
            case 35:
                R = 89;
                G = 0;
                B = 255;
                time -= 1;
                break;
            case 34:
                R = 102;
                G = 0;
                B = 255;
                time -= 1;
                break;
            case 33:
                R = 124;
                G = 0;
                B = 255;
                time -= 1;
                break;
            case 32:
                R = 154;
                G = 0;
                B = 255;
                time -= 1;
                break;
            case 31:
                R = 222;
                G = 0;
                B = 255;
                time -= 1;
                break;
            case 30:
                R = 255;
                G = 0;
                B = 247;
                time -= 1;
                break;
            case 29:
                R = 255;
                G = 0;
                B = 179;
                time -= 1;
                break;
            case 28:
                R = 255;
                G = 0;
                B = 128;
                time = 59;
                break;
        }

        return Color.ofRgb(R,B,G);
    }
}
