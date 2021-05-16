package com.m1zark.aurapokemon.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.m1zark.aurapokemon.AuraPokemon;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.lwjgl.Sys;

public class PlayerData {
    private static ConfigurationLoader<CommentedConfigurationNode> loader;
    private static CommentedConfigurationNode main;

    public PlayerData() {
        this.loadConfig();
    }

    private void loadConfig() {
        Path configFile = Paths.get(AuraPokemon.getInstance().getConfigDir() + "/player-data.conf");
        loader = (HoconConfigurationLoader.builder().setPath(configFile)).build();
        try {
            if (!Files.exists(AuraPokemon.getInstance().getConfigDir())) {
                Files.createDirectory(AuraPokemon.getInstance().getConfigDir());
            }
            if (!Files.exists(configFile)) {
                Files.createFile(configFile);
            }
            if (main == null) {
                main = loader.load(ConfigurationOptions.defaults().setShouldCopyDefaults(true));
            }
            CommentedConfigurationNode accounts = main.getNode("PlayerData");

            loader.save(main);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        this.load();
    }

    public static void saveConfig() {
        try {
            loader.save(main);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            main = loader.load();
            this.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {

    }

    public static void updatePlayerData(String uuid) {
        if(!main.getNode("PlayerData", uuid).isVirtual()) {
            int count = main.getNode("PlayerData", uuid).getInt() + 1;
            main.getNode("PlayerData", uuid).setValue(count);
        } else {
            main.getNode("PlayerData", uuid).setValue(1);
        }

        saveConfig();
    }

    public static HashMap<String,Integer> getAllAccounts() {
        HashMap<String,Integer> accounts = new HashMap<>();

        if(!main.getNode("PlayerData").isVirtual()) {
            main.getNode("PlayerData").getChildrenMap().forEach((uuid,count) -> {
                accounts.put((String) uuid, count.getInt());
            });
        }

        return accounts;
    }

    public static void clearPlayerData() {
        if(!main.getNode("PlayerData").isVirtual()) {
            main.getNode("PlayerData").getChildrenMap().forEach((uuid,count) -> main.getNode("PlayerData", uuid).setValue(0));
        }

        saveConfig();
    }

    public static int getPlayerCount(String uuid) {
        if(!main.getNode("PlayerData", uuid).isVirtual()) {
            return main.getNode("PlayerData", uuid).getInt();
        } else {
            return 0;
        }
    }
}
