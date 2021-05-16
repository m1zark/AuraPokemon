package com.m1zark.aurapokemon.Config;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.m1zark.aurapokemon.APInfo;
import com.m1zark.aurapokemon.AuraPokemon;
import com.m1zark.m1utilities.api.Discord.DiscordOption;
import com.m1zark.m1utilities.api.Discord.Field;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.text.Text;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Config {
    private static ConfigurationLoader<CommentedConfigurationNode> loader;
    private static CommentedConfigurationNode main;

    public static boolean broadcastCaught;
    public static double updatePercent;
    public static boolean enableTracking;

    public Config() {
        this.loadConfig();
    }

    private void loadConfig(){
        Path configFile = Paths.get(AuraPokemon.getInstance().getConfigDir() + "/settings.conf");

        loader = HoconConfigurationLoader.builder().setPath(configFile).build();

        try {
            if (!Files.exists(AuraPokemon.getInstance().getConfigDir())) Files.createDirectory(AuraPokemon.getInstance().getConfigDir());

            if (!Files.exists(configFile)) Files.createFile(configFile);

            if (main == null) {
                main = loader.load(ConfigurationOptions.defaults().setShouldCopyDefaults(true));
            }

            CommentedConfigurationNode general = main.getNode("General");
            CommentedConfigurationNode discord = main.getNode("Discord");

            general.getNode("spawn-interval").setComment("Interval between spawn attempts... this is in minutes.").getInt(10);
            general.getNode("broadcast-caught").getBoolean(true);

            general.getNode("Event", "stats-percent-increase").setComment("In decimal format... aka 20% would be 0.2").getDouble(0.2);
            general.getNode("Event","enable-spawning").getBoolean(false);
            general.getNode("Event","enable-tracking").getBoolean(false);
            general.getNode("Event","aura").getString("blazing");
            general.getNode("Event","nickname").getString("\u00A7eSummer {pokemon}\u00A7f");
            general.getNode("Event","spawn-chance").setComment("Chance that a spawn attempt will succeed (1/spawnChance)").getInt(100);
            general.getNode("Event","broadcast-spawn-message").getString("&dA &eSummer Pok\u00E9mon &dhas spawned in a &b{biome} &dbiome!");
            general.getNode("Event","broadcast-caught-message").getString("&b{player} &dhas caught a special &eSummer Pok\u00E9mon&d!");

            general.getNode("Custom","custom-count").getInt(1);
            general.getNode("Custom","1","enable-spawning").getBoolean(false);
            general.getNode("Custom","1","custom-texture").getString("event");
            general.getNode("Custom","1","spawn-message").getString("&7[&dPixelmon&7] &a{event} Pok\u00E9mon has spawned in a {biome} biome.");
            general.getNode("Custom","1","enable-spawn-message").getBoolean(true);
            general.getNode("Custom","1","nickname").getString("\u00A7b{pokemon}\u00A7f");
            general.getNode("Custom","1","spawn-chance").setComment("Chance that a spawn attempt will succeed (1/spawnChance)").getInt(100);
            general.getNode("Custom","1","pokemon-list").getList(TypeToken.of(String.class), Lists.newArrayList("Abra","Vulpix"));

            discord.getNode("enableDiscordNotifications").getBoolean(true);
            discord.getNode("webhook-url").getList(TypeToken.of(String.class));
            discord.getNode("notifications","event-spawn","color").getString("#AA00AA");

            loader.save(main);
        } catch (ObjectMappingException | IOException e) {
            AuraPokemon.getInstance().getConsole().ifPresent(console -> console.sendMessages(Text.of(APInfo.ERROR_PREFIX, "There was an issue loading the config...")));
            e.printStackTrace();
        }

        this.load();
        AuraPokemon.getInstance().getConsole().ifPresent(console -> console.sendMessages(Text.of(APInfo.PREFIX, "Loading configuration...")));
    }

    public static void saveConfig() {
        try {
            loader.save(main);
        } catch (IOException var1) {
            var1.printStackTrace();
        }
    }

    public void reload() {
        try {
            main = loader.load();
            this.load();
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    private void load() {
        broadcastCaught = main.getNode("General","broadcast-caught").getBoolean();
        updatePercent = main.getNode("General","Event","stats-percent-increase").getDouble();
        enableTracking = main.getNode("General","Event","enable-tracking").getBoolean();
    }

    public static int getSpawnInterval() { return main.getNode("General","spawn-interval").getInt(); }

    public static boolean enableMessages(String value) {
        return main.getNode((Object[])value.split("\\.")).getBoolean();
    }

    public static void trackCaughtPokemon() {
        main.getNode("General","Event","enable-tracking").setValue(!main.getNode("General","Event","enable-tracking").getBoolean());
        saveConfig();
    }

    public static Object getEventOption(String option) {
        return main.getNode("General","Event",option).getValue();
    }

    public static int getCustomSize() {
        return main.getNode("General","Custom","custom-count").getInt();
        //return main.getNode("General","Custom").getChildrenMap().size();
    }

    public static Object getCustomOption(int id, String option) {
        return main.getNode("General","Custom",String.valueOf(id),option).getValue();
    }

    public static List<String> getPokemon(int id) {
        try {
            return main.getNode("General","Custom",String.valueOf(id),"pokemon-list").getList(TypeToken.of(String.class)).stream().filter(EnumSpecies::hasPokemonAnyCase).collect(Collectors.toList());
        } catch (ObjectMappingException e) {
            return Lists.newArrayList();
        }
    }

    public static DiscordOption discordOption(String id, String pokemon, String biome) {
        String username = main.getNode("Discord","notifications",id,"username").isVirtual() ? null : main.getNode("Discord","notifications",id,"username").getString();
        String avatar = main.getNode("Discord","notifications",id,"avatar").isVirtual() ? null : main.getNode("Discord","notifications",id,"avatar").getString();
        String content = main.getNode("Discord","notifications",id,"content").isVirtual() ? null : main.getNode("Discord","notifications",id,"content").getString().replace("{pokemon}",pokemon).replace("{biome}",biome);
        String title = main.getNode("Discord","notifications",id,"title").isVirtual() ? null : main.getNode("Discord","notifications",id,"title").getString().replace("{pokemon}",pokemon).replace("{biome}",biome);
        String description = main.getNode("Discord","notifications",id,"description").isVirtual() ? null : main.getNode("Discord","notifications",id,"description").getString().replace("{pokemon}",pokemon).replace("{biome}",biome);
        String thumbnail = main.getNode("Discord","notifications",id,"thumbnail").isVirtual() ? null : main.getNode("Discord","notifications",id,"thumbnail").getString().replace("{pokemon}",pokemon.toLowerCase());
        String image = main.getNode("Discord","notifications",id,"image").isVirtual() ? null : main.getNode("Discord","notifications",id,"image").getString().replace("{pokemon}",pokemon.toLowerCase());
        boolean timestamp = !main.getNode("Discord","notifications",id,"timestamp").isVirtual() && main.getNode("Discord","notifications",id,"timestamp").getBoolean();

        List<Field> fields = Lists.newArrayList();
        if(!main.getNode("Discord","notifications",id,"fields").isVirtual()) {
            for (int i = 0; i < main.getNode("Discord","notifications",id,"fields").getChildrenList().size(); i++) {
                CommentedConfigurationNode field = main.getNode("Discord","notifications",id,"fields").getChildrenList().get(i);

                fields.add(new Field(
                        field.getNode("name").isVirtual() ? null : field.getNode("name").getString().replace("{pokemon}",pokemon).replace("{biome}",biome),
                        field.getNode("value").isVirtual() ? null : field.getNode("value").getString().replace("{pokemon}",pokemon).replace("{biome}",biome),
                        !field.getNode("inline").isVirtual() && field.getNode("inline").getBoolean()
                ));
            }
        }

        Map<String,String> footer = new HashMap<>();
        if(!main.getNode("Discord","notifications",id,"footer").isVirtual()) {
            footer.put("text", main.getNode("Discord","notifications",id,"footer","text").getString());
            footer.put("icon", main.getNode("Discord","notifications",id,"footer","icon").getString());
        }

        try {
            return DiscordOption.builder()
                    .webhookChannels(main.getNode("Discord","webhook-url").getList(TypeToken.of(String.class)))
                    .username(username)
                    .avatar_url(avatar)
                    .content(content)
                    .color(Color.decode(main.getNode("Discord","notifications",id,"color").getString()))
                    .title(title)
                    .description(description)
                    .fields(fields)
                    .thumbnail(thumbnail)
                    .image(image)
                    .footer(footer)
                    .timestamp(timestamp)
                    .build();
        }catch (ObjectMappingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
