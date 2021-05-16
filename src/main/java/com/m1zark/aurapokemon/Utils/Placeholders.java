package com.m1zark.aurapokemon.Utils;

import com.m1zark.aurapokemon.APInfo;
import com.m1zark.aurapokemon.Config.PlayerData;
import me.rojo8399.placeholderapi.*;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

public class Placeholders {
    public static void register(Object plugin) {
        PlaceholderService placeholderService = Sponge.getServiceManager().provideUnchecked(PlaceholderService.class);
        placeholderService.loadAll(new Placeholders(), plugin).forEach(b -> {
            try {
                b.version(APInfo.VERSION).author("m1zark").buildAndRegister();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Placeholder(id="auraevent")
    public String auraEventCount(@Source Player player) throws NoValueException {
        return String.valueOf(PlayerData.getPlayerCount(player.getUniqueId().toString()));
    }
}
