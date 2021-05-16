package com.m1zark.aurapokemon.Utils.particles;

import com.flowpowered.math.vector.Vector3d;
import com.m1zark.aurapokemon.Utils.LocationUtils;
import com.m1zark.aurapokemon.Utils.MathUtils;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Color;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.function.Consumer;

public class StormTask implements Consumer<Task> {
    private int time = 0;

    @Override
    public void accept(Task t) {
        Sponge.getServer().getWorlds().forEach(world -> {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof EntityPixelmon) {
                    EntityPixelmon pixelmon = (EntityPixelmon) entity;

                    if(pixelmon.getPokemonData().getPersistentData().getString("particle").equals("storm")) {
                        world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.EXPLOSION).quantity(1).build(), entity.getLocation().getPosition().add(MathUtils.randomRange(-1.0, 0.8), 3.0, MathUtils.randomRange(-0.8, 1.0)));
                        int n = 0;
                        while(n < 2) {
                            world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.CLOUD).quantity(1).build(), entity.getLocation().getPosition().add(MathUtils.randomRange(-1.0, 0.8), 3.2, MathUtils.randomRange(-0.8, 1.0)));
                            ++n;
                        }
                        if (this.time <= 100) {
                            if (this.time % 20 == 0) {
                                LocationUtils.getClosestPlayersFromLocation(world, entity.getLocation(), 4.0).forEach(player ->
                                    player.playSound(SoundTypes.WEATHER_RAIN, player.getLocation().getPosition(), 0.05f, 1.0f)
                                );
                            }
                            world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.DRIP_WATER).quantity(1).build(), entity.getLocation().getPosition().add((double)MathUtils.randomRange(-0.8f, 0.8f), 3.2, (double)MathUtils.randomRange(-0.8f, 0.8f)));
                        } else if (this.time <= 200) {
                            world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.SNOW_SHOVEL).quantity(1).build(), entity.getLocation().getPosition().add((double)MathUtils.randomRange(-0.8f, 0.8f), 3.0, (double)MathUtils.randomRange(-0.8f, 0.8f)));
                            world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.CLOUD).quantity(1).build(), entity.getLocation().getPosition().add((double)MathUtils.randomRange(-0.8f, 0.8f), 0.0, (double)MathUtils.randomRange(-0.8f, 0.8f)));
                        } else if (this.time <= 400) {
                            world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.DRIP_WATER).quantity(1).build(), entity.getLocation().getPosition().add((double)MathUtils.randomRange(-0.8f, 0.8f), 3.0, (double)MathUtils.randomRange(-0.8f, 0.8f)));
                            if (this.time % 40 == 0) {
                                this.playLightning(entity.getLocation().copy().add(0.0, 3.0, 0.0), world);
                            }
                        } else {
                            this.time = 0;
                        }
                        ++this.time;
                    }
                }
            }
        });
    }

    private void playThunder(Location location, World world) {
        LocationUtils.getClosestPlayersFromLocation(world, location, 4.0).forEach(player -> {
            PotionEffect blind = PotionEffect.builder().potionType(PotionEffectTypes.BLINDNESS).duration(20).amplifier(3).build();
            PotionEffect night = PotionEffect.builder().potionType(PotionEffectTypes.NIGHT_VISION).duration(40).amplifier(1).build();

            PotionEffectData effects = player.getOrCreate(PotionEffectData.class).get();
            effects.addElement(blind); effects.addElement(night);
            player.offer(effects);

            player.playSound(SoundTypes.ENTITY_LIGHTNING_THUNDER, player.getLocation().getPosition(), 0.05f, 2.0f);
        });
    }

    private void playLightning(Location location, World world) {
        this.playThunder(location, world);

        Location location2 = location.copy();
        Vector3d vector = MathUtils.getRandomVector();
        vector = new Vector3d(vector.getX(), - Math.abs(vector.getY() - 2.0), vector.getZ());

        int n = MathUtils.randomRange(20, 40);
        for(int n2 = 0; n2 < 50; ++n2) {
            float f = (float)n2 * 0.06f / 2.0f;
            Vector3d vector2 = vector.clone().mul(f);

            world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.REDSTONE_DUST)
                    .option(ParticleOptions.COLOR, Color.ofRgb(255, 223, 90))
                    .build(), location2.getPosition().add(vector2.getX(), vector2.getY()-f, vector2.getZ()));

            if (n2 == n || n2 == n + 10) {
                vector = MathUtils.getRandomVector();
                vector = new Vector3d(vector.getX(), - Math.abs(vector.getY()), vector.getZ());
            }
        }
    }
}
