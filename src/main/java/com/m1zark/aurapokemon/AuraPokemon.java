package com.m1zark.aurapokemon;

import com.google.inject.Inject;
import com.m1zark.aurapokemon.Commands.CommandManager;
import com.m1zark.aurapokemon.Config.PlayerData;
import com.m1zark.aurapokemon.Listeners.PixelmonListener;
import com.m1zark.aurapokemon.Config.Config;
import com.m1zark.aurapokemon.Utils.Placeholders;
import com.m1zark.aurapokemon.Utils.Utils;
import com.m1zark.aurapokemon.Utils.particles.ApocalypticCloudTask;
import com.m1zark.aurapokemon.Utils.particles.EnumParticles;
import com.m1zark.aurapokemon.Utils.particles.ShinyTask;
import com.m1zark.aurapokemon.Utils.particles.StormTask;
import com.m1zark.m1utilities.api.Inventories;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Getter
@Plugin(id=APInfo.ID, name=APInfo.NAME, version=APInfo.VERSION,description=APInfo.DESCRIPTION,authors="m1zark")
public class AuraPokemon {
    @Inject private Logger logger;
    @Inject private PluginContainer pC;
    private static AuraPokemon instance;
    @Inject @ConfigDir(sharedRoot = false) private Path configDir;
    private Config config;
    private PlayerData playerData;
    private boolean enabled = true;
    public static long lastEventSpawn;

    @Listener
    public void onServerStart(GameInitializationEvent e) {
        instance = this;
        APInfo.startup();

        this.enabled = APInfo.dependencyCheck();

        if(this.enabled) {
            this.config = new Config();
            this.playerData = new PlayerData();

            getConsole().ifPresent(console -> console.sendMessages(Text.of(APInfo.PREFIX, "Initializing listeners...")));
            Pixelmon.EVENT_BUS.register(new PixelmonListener());
            new CommandManager().registerCommands(this);
            if (Sponge.getPluginManager().isLoaded("placeholderapi")) Placeholders.register(this);
        }
    }

    @Listener
    public void onReload(GameReloadEvent e) {
        if (this.enabled) {
            this.config = new Config();
            this.playerData = new PlayerData();
            getConsole().ifPresent(console -> console.sendMessages(Text.of(APInfo.PREFIX, "Configurations have been reloaded")));
        }
    }

    @Listener(order = Order.POST)
    public void postGameStart(GameStartedServerEvent event) {
        // 50 Milliseconds = 1 tick
        // 1000 Milliseconds = 1 second
        // An in-game day lasts exactly 24000 ticks, or 20 minutes.

        //Task.builder().execute(new HeartTask()).interval(EnumParticles.Hearts.getTime(), TimeUnit.MILLISECONDS).name("Hearts").submit(this);
        //Task.builder().execute(new BloodHelixTask()).interval(EnumParticles.BloodHelix.getTime(), TimeUnit.MILLISECONDS).name("Blood Helix").submit(this);
        //Task.builder().execute(new RainbowHelixTask()).interval(EnumParticles.RainbowHelix.getTime(), TimeUnit.MILLISECONDS).name("Rainbow Helix").submit(this);
        //Task.builder().execute(new ApocalypticCloudTask()).interval(EnumParticles.Apocalyptic.getTime(), TimeUnit.MILLISECONDS).name("Apocalyptic").submit(this);
        //Task.builder().execute(new StormTask()).interval(EnumParticles.Storm.getTime(), TimeUnit.MILLISECONDS).name("Storm").submit(this);
        Task.builder().execute(new ShinyTask()).interval(EnumParticles.Shiny.getTime(), EnumParticles.Shiny.getUnit()).name("Shiny").submit(this);
        //Task.builder().execute(new PoseidonTask()).interval(EnumParticles.Poseidon.getTime(), TimeUnit.MILLISECONDS).name("Poseidon").submit(this);
        //Task.builder().execute(new FrostLordTask()).interval(EnumParticles.FrostLord.getTime(), TimeUnit.MILLISECONDS).name("Frost Lord").submit(this);
    }

    @Listener
    public void pokeInteract(InteractEntityEvent.Secondary.MainHand event, @First Player player) {
        Optional<ItemStack> optHeldItem = player.getItemInHand(HandTypes.MAIN_HAND);
        if (event.getTargetEntity() instanceof EntityPixelmon) {
            if(optHeldItem.isPresent() && Inventories.doesHaveNBT(optHeldItem.get(), "auraID")) {
                PlayerPartyStorage storage = Utils.getPlayerStorage(player);
                EntityPixelmon pokemon = (EntityPixelmon) event.getTargetEntity();
                String type = optHeldItem.get().toContainer().get(DataQuery.of("UnsafeData","auraID")).get().toString();

                if(pokemon.hasOwner() && pokemon.getTrainer() == null && storage != null && storage.getTeam().contains(pokemon.getStoragePokemonData())) {
                    pokemon.getPokemonData().getPersistentData().setString("particle",type);

                    Inventories.removeItem(player, optHeldItem.get(), 1);
                }
            }
        }
    }

    public static AuraPokemon getInstance() { return instance; }

    public Optional<ConsoleSource> getConsole() {
        return Optional.ofNullable(Sponge.isServerAvailable() ? Sponge.getServer().getConsole() : null);
    }
}
