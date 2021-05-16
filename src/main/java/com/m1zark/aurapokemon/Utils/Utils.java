package com.m1zark.aurapokemon.Utils;

import com.m1zark.aurapokemon.AuraPokemon;
import com.m1zark.aurapokemon.Config.Config;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.Color;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Utils {
    private static double pi = 0;
    private static double phi = 0;

    @Nullable
    public static PlayerPartyStorage getPlayerStorage(Player player) {
        return Pixelmon.storageManager.getParty((EntityPlayerMP) player);
    }

    public static boolean checkSpawnTime() {
        long millis = Instant.now().toEpochMilli() - AuraPokemon.lastEventSpawn;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60L;

        return minutes > Config.getSpawnInterval();
    }

    public static void BlueHelix(Entity entity) {
        phi = phi + Math.PI / 16;
        double x, y, z;
        for (double t = 0; t <= 2 * Math.PI; t = t + Math.PI / 16) {
            for (double i = 0; i <= 1; i = i + 1) {
                x = 0.15 * (2 * Math.PI - t) * Math.cos(t + phi + i * Math.PI);
                y = 0.5 * t;
                z = 0.15 * (2 * Math.PI - t) * Math.sin(t + phi + i * Math.PI);
                World world = entity.getWorld();
                world.spawnParticles(
                        ParticleEffect.builder().type(ParticleTypes.REDSTONE_DUST)
                                .option(ParticleOptions.COLOR, Color.ofRgb(15, 86, 253)).build(),
                        entity.getLocation().getPosition().add(x, y, z));
            }
        }
    }

    public static void StyleGlobe(Entity entity) {
        pi += Math.PI / 10;
        for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 40) {
            double r = 1.5;
            double x = r * Math.cos(theta) * Math.sin(pi);
            double y = r * Math.cos(pi) + 1.5;
            double z = r * Math.sin(theta) * Math.sin(pi);

            // double z = r*Math.sin(theta)+Math.sin(pi);

            World world = entity.getWorld();
            world.spawnParticles(
                    ParticleEffect.builder().type(ParticleTypes.REDSTONE_DUST)
                            .option(ParticleOptions.COLOR, Color.ofRgb(255, 0, 0)).build(),
                    entity.getLocation().getPosition().add(x, y, z));
        }
    }

    public static void RainEffect(Entity entity) {
        World world = entity.getWorld();
        world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.WATER_DROP).quantity(2).build(),
                entity.getLocation().getPosition().add(0, 2.5, 0));
        //world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.DRIP_WATER).build(),
        //	player.getLocation().getPosition().add(0, 2.5, 0));
    }

    public static void CloudEffect(Entity entity) {
        World world = entity.getWorld();
        world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.WATER_DROP).quantity(2).build(),
                entity.getLocation().getPosition().add(0, 2.5, 0));

        world.spawnParticles(
                ParticleEffect.builder().type(ParticleTypes.CLOUD).quantity(2).build(),
                entity.getLocation().getPosition().add(0, 2.5, 0));
        world.spawnParticles(
                ParticleEffect.builder().type(ParticleTypes.CLOUD).quantity(2).build(),
                entity.getLocation().getPosition().add(0.2, 2.5, 0.2));
        world.spawnParticles(
                ParticleEffect.builder().type(ParticleTypes.CLOUD).quantity(2).build(),
                entity.getLocation().getPosition().add(0, 2.5, 0.4));
        world.spawnParticles(
                ParticleEffect.builder().type(ParticleTypes.CLOUD).quantity(2).build(),
                entity.getLocation().getPosition().add(0.4, 2.5, 0));
    }
}
