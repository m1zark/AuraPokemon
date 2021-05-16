package com.m1zark.aurapokemon.Utils;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class LocationUtils {
    public List<Entity> getClosestPlayersFromLocation(final Player player, final int radius) {
        ArrayList<Entity> arrayList = new ArrayList<>();

        player.getLocation().getExtent().getEntities().forEach(entity -> {
            if(entity.getLocation().getPosition().distance(player.getLocation().getPosition()) <= radius) arrayList.add(entity);
        });

        return arrayList;
    }

    public static List<Player> getClosestPlayersFromLocation(World world, Location location, double d) {
        ArrayList<Player> arrayList = new ArrayList<>();
        double d2 = d * d;
        for (Player player : world.getPlayers()) {
            if (player.getLocation().add(0.0, 0.85, 0.0).getPosition().distance(location.getPosition()) > d2) continue;
            arrayList.add(player);
        }
        return arrayList;
    }
}
