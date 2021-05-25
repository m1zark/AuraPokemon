package com.m1zark.aurapokemon.Listeners;

import com.m1zark.aurapokemon.AuraPokemon;
import com.m1zark.aurapokemon.Config.Config;
import com.m1zark.aurapokemon.Config.PlayerData;
import com.m1zark.aurapokemon.Utils.Utils;
import com.m1zark.aurapokemon.events.AuraEvent;
import com.m1zark.m1utilities.M1utilities;
import com.m1zark.m1utilities.api.Chat;
import com.m1zark.m1utilities.api.Discord.Message;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.events.spawning.PixelmonSpawnerEvent;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.forms.EnumMega;
import de.waterdu.aquaauras.auras.AuraInstance;
import de.waterdu.aquaauras.auras.AuraStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.biome.Biome;
import de.waterdu.aquaauras.helper.FileHelper;
import de.waterdu.aquaauras.structures.AuraDefinition;
import de.waterdu.aquaauras.structures.EffectDefinition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.Sponge;

import java.io.File;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Random;

public class PixelmonListener {
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public void onPokemonCapture(CaptureEvent.SuccessfulCapture event) {
        EntityPlayerMP player = event.player;
        Pokemon pokemon = event.getPokemon().getPokemonData();
        AuraStorage auras = new AuraStorage(event.getPokemon().getPokemonData().getPersistentData());

        if (auras.hasAuras() && pokemon.getPersistentData().hasKey("AuraEvent")) {
                if (Config.enableTracking) PlayerData.updatePlayerData(((Player)player).getUniqueId().toString());
                if (Config.broadcastCaught) Chat.sendServerWideMessage(((String) Config.getEventOption("broadcast-caught-message")).replace("{player}", player.getName()));
        }
    }

    @SubscribeEvent
    public void onSpawnPokemonEvent(final SpawnEvent event) {
        net.minecraft.entity.Entity spawnedEntity = event.action.getOrCreateEntity();

        if (spawnedEntity instanceof EntityPixelmon) {
            final EntityPixelmon pixelmon = (EntityPixelmon) spawnedEntity;
            if (!pixelmon.hasOwner() && pixelmon.getOwnerId() == null && pixelmon.getOwner() == null && pixelmon.battleController == null && pixelmon.getTrainer() == null && pixelmon.getFormEnum() != EnumMega.Mega && !pixelmon.isBossPokemon()) {
                if(Config.enableMessages("General.Event.enable-spawning")) {
                    if (Utils.checkSpawnTime() && RANDOM.nextInt((int) Config.getEventOption("spawn-chance")) <= 1) {
                        Chat.sendServerWideMessage(((String) Config.getEventOption("broadcast-spawn-message")).replace("{biome}",getBiomeName(event.action.spawnLocation.biome)));

                        pixelmon.getPokemonData().setNickname(((String)Config.getEventOption("nickname")).replace("{pokemon}", pixelmon.getPokemonName()));
                        AuraDefinition ad = FileHelper.getAuraDefinitionForName((String)Config.getEventOption("aura-type"));
                        EffectDefinition ed = FileHelper.getEffectDefinitionForName((String)Config.getEventOption("aura-effect"));

                        AuraStorage auras = new AuraStorage(pixelmon.getPokemonData().getPersistentData());
                        int result = auras.addAura(new AuraInstance(ad, ed, true), null, pixelmon);

                        if(result > 0) {
                            pixelmon.getPokemonData().getPersistentData().setBoolean("AuraEvent", true);
                            this.updatePokemonEvent(pixelmon.getStoragePokemonData());
                            pixelmon.canDespawn = false;
                            AuraPokemon.lastEventSpawn = Instant.now().toEpochMilli();

                            AuraEvent auraEvent = new AuraEvent(pixelmon.getPokemonName(), ((String) Config.getEventOption("nickname")).replace(" {pokemon}", ""), PixelmonListener.getBiomeName(event.action.spawnLocation.biome), Cause.builder().append(this).build(EventContext.builder().build()));
                            Sponge.getEventManager().post(auraEvent);

                            if(Config.enableMessages("Discord.enableDiscordNotifications")) {
                                M1utilities.getInstance().getDiscordNotifier().ifPresent(notifier -> {
                                    Message message = notifier.forgeMessage(Config.discordOption("event-spawn",pixelmon.getPokemonName(),getBiomeName(event.action.spawnLocation.biome)));
                                    notifier.sendMessage(message);
                                });
                            }
                        }
                    }
                }

                if(Config.getCustomSize() > 0 && !pixelmon.getPokemonData().isShiny()) {
                    for(int i = 1; i <= Config.getCustomSize(); i++) {
                        if(Config.enableMessages("General.Custom." + i + ".enable-spawning")) {
                            if(Config.getPokemon(i).contains(pixelmon.getSpecies().name())) {
                                if (RANDOM.nextInt((int) Config.getCustomOption(i,"spawn-chance")) <= 1) {
                                    if((Boolean) Config.getCustomOption(i,"enable-spawn-message")) {
                                        Chat.sendServerWideMessage(((String)Config.getCustomOption(i,"spawn-message"))
                                                .replace("{event}",StringUtils.capitalize((String) Config.getCustomOption(i,"custom-texture")))
                                                .replace("{pokemon}",pixelmon.getPokemonName())
                                                .replace("{biome}",PixelmonListener.getBiomeName(event.action.spawnLocation.biome))
                                        );
                                    }

                                    this.updatePokemonCustom(pixelmon.getStoragePokemonData());
                                    pixelmon.getPokemonData().getPersistentData().setBoolean("shiny",true);
                                    pixelmon.getPokemonData().setCustomTexture((String) Config.getCustomOption(i,"custom-texture"));
                                    pixelmon.getPokemonData().setNickname(((String) Config.getCustomOption(i,"nickname")).replace("{pokemon}",pixelmon.getPokemonName()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSpawnerEvent(PixelmonSpawnerEvent event) {
        for(EntityPixelmon pixelmon : event.spawner.spawnedPokemon) {
            if(Config.getCustomSize() > 0 && !pixelmon.getPokemonData().isShiny()) {
                for(int i = 1; i <= Config.getCustomSize(); i++) {
                    if(Config.enableMessages("General.Custom." + i + ".enable-spawning")) {
                        if(Config.getPokemon(i).contains(pixelmon.getSpecies().name())) {
                            if (RANDOM.nextInt((int) Config.getCustomOption(i,"spawn-chance")) <= 1) {
                                this.updatePokemonCustom(pixelmon.getStoragePokemonData());
                                pixelmon.getPokemonData().getPersistentData().setBoolean("shiny",true);
                                pixelmon.getPokemonData().setCustomTexture((String) Config.getCustomOption(i,"custom-texture"));
                                pixelmon.getPokemonData().setNickname(((String) Config.getCustomOption(i,"nickname")).replace("{pokemon}",pixelmon.getPokemonName()));
                            }
                        }
                    }
                }
            }
        }
    }

    private static String getBiomeName(Biome biome) {
        String name = "";
        try {
            Field f = ReflectionHelper.findField(Biome.class, "biomeName", "field_185412_a", "field_76791_y");
            name = (String) f.get(biome);
        } catch (Exception e) {
            return "Error getting biome name";
        }

        return name;
    }

    private void updatePokemonEvent(Pokemon pokemon) {
        int[] ivs = new int[]{pokemon.getIVs().getStat(StatsType.HP), pokemon.getIVs().getStat(StatsType.Attack), pokemon.getIVs().getStat(StatsType.Defence), pokemon.getIVs().getStat(StatsType.SpecialAttack), pokemon.getIVs().getStat(StatsType.SpecialDefence), pokemon.getIVs().getStat(StatsType.Speed)};
        StatsType[] stats = new StatsType[]{StatsType.HP, StatsType.Attack, StatsType.Defence, StatsType.SpecialAttack, StatsType.SpecialDefence, StatsType.Speed};

        for(int i = 0; i <= 5; i++) {
            int amount = (int)(ivs[i] + (ivs[i] * Config.updatePercent));
            if(amount > 31) amount = 31;

            pokemon.getIVs().setStat(stats[i], amount);
        }
    }

    private void updatePokemonCustom(Pokemon pokemon) {
        double percent = 0.2;

        int[] ivs = new int[]{pokemon.getIVs().getStat(StatsType.HP), pokemon.getIVs().getStat(StatsType.Attack), pokemon.getIVs().getStat(StatsType.Defence), pokemon.getIVs().getStat(StatsType.SpecialAttack), pokemon.getIVs().getStat(StatsType.SpecialDefence), pokemon.getIVs().getStat(StatsType.Speed)};
        StatsType[] stats = new StatsType[]{StatsType.HP, StatsType.Attack, StatsType.Defence, StatsType.SpecialAttack, StatsType.SpecialDefence, StatsType.Speed};

        for(int i = 0; i <= 5; i++) {
            int amount = (int)(ivs[i] + (ivs[i] * percent));
            if(amount > 31) amount = 31;

            pokemon.getIVs().setStat(stats[i], amount);
        }
    }
}
