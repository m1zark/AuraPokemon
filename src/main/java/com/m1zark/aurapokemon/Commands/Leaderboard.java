package com.m1zark.aurapokemon.Commands;

import com.m1zark.aurapokemon.AuraPokemon;
import com.m1zark.aurapokemon.Config.Config;
import com.m1zark.aurapokemon.Config.PlayerData;
import com.m1zark.m1utilities.api.Chat;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;

import java.util.*;

public class Leaderboard implements CommandExecutor {
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(Config.enableTracking) {
            HashMap<String, Integer> accounts = PlayerData.getAllAccounts();

            List<Text> leaderboard = new ArrayList<>();
            accounts.forEach((uuid, count) -> {
                Text text = Text.of(Chat.embedColours("&b" + getNameFromUUID(UUID.fromString(uuid)).get() + " &f\u21E8 &7Caught Count: &a" + count));
                leaderboard.add(text);
            });

            PaginationList.builder().contents(leaderboard).title(Text.of(Chat.embedColours("&aAuraPok\u00E9mon LeaderBoard:"))).build().sendTo(src);
        } else {
            Chat.sendMessage(src, "&cThis command is currently unavailable. Try again when an event is running.");
        }

        return CommandResult.success();
    }

    public static class ClearData implements CommandExecutor {
        @Override public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            PlayerData.clearPlayerData();
            AuraPokemon.getInstance().getPlayerData().reload();
            Chat.sendMessage(src, "&7Player data has been reset.");

            return CommandResult.success();
        }
    }

    public static class StartTracking implements CommandExecutor {
        @Override public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            Config.trackCaughtPokemon();
            AuraPokemon.getInstance().getConfig().reload();
            Chat.sendMessage(src, "&7Player data tracking has been " + (Config.enableTracking ? "enabled." : "disabled."));

            return CommandResult.success();
        }
    }

    private static Optional<String> getNameFromUUID(UUID uuid){
        UserStorageService uss = Sponge.getServiceManager().provideUnchecked(UserStorageService.class);
        Optional<User> oUser = uss.get(uuid);

        if (oUser.isPresent()){
            String name = oUser.get().getName();
            return Optional.of(name);
        } else {
            return Optional.empty();
        }
    }
}
