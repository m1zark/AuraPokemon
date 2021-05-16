package com.m1zark.aurapokemon.Utils.particles;

import com.flowpowered.math.vector.Vector3d;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Color;

import java.util.function.Consumer;

public class ShinyTask implements Consumer<Task> {
    private double theta = 0.0;
    private double radius = 0.7;
    private double degrees = 1.5;

    private static int R, G, B = 20;
    private static int time = 59;

    @Override public void accept(Task t) {
        Sponge.getServer().getWorlds().forEach(world -> {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof EntityPixelmon) {
                    EntityPixelmon pixelmon = (EntityPixelmon) entity;

                    if (pixelmon.getPokemonData().getPersistentData().hasKey("shiny")) {
                        double playerX;
                        double playerZ;
                        if (this.theta >= 360.0) continue;
                        playerZ = entity.getLocation().getPosition().getZ() + this.radius * Math.sin(this.theta);
                        playerX = entity.getLocation().getPosition().getX() + this.radius * Math.cos(this.theta);

                        ParticleEffect effect = ParticleEffect.builder()
                                .quantity(1)
                                .type(ParticleTypes.REDSTONE_DUST)
                                .option(ParticleOptions.COLOR, getColor())
                                .velocity(new Vector3d(0.0, 0.1, 0.0))
                                .build();

                        world.spawnParticles(effect, new Vector3d(playerX, entity.getLocation().getPosition().getY() + 0.5, playerZ));

                        this.theta += this.degrees;
                        if (this.theta == 360.0) this.theta = 0.0;
                    }
                }
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

