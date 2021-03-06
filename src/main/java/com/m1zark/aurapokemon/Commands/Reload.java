package com.m1zark.aurapokemon.Commands;

import com.m1zark.aurapokemon.AuraPokemon;
import com.m1zark.m1utilities.api.Chat;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class Reload implements CommandExecutor {
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        AuraPokemon.getInstance().getConfig().reload();
        AuraPokemon.getInstance().getPlayerData().reload();
        Chat.sendMessage(src, "&7AuraPokemon configs successfully reloaded.");

        return CommandResult.success();
    }
}