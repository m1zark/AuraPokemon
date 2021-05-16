package com.m1zark.aurapokemon.Commands;

import com.m1zark.aurapokemon.AuraPokemon;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    public void registerCommands(AuraPokemon plugin) {
        Sponge.getCommandManager().register(plugin, aura, "aurapokemon", "ap", "aura");

        //AuraPokemon.getInstance().getConsole().ifPresent(console -> console.sendMessages(Text.of(APInfo.PREFIX, "Registering commands...")));
    }

    private CommandSpec reload = CommandSpec.builder()
            .permission("aurapokemon.admin.reload")
            .description(Text.of("Reload all config files."))
            .executor(new Reload())
            .build();

    private CommandSpec cleardata = CommandSpec.builder()
            .executor(new Leaderboard.ClearData())
            .permission("aurapokemon.admin.clear")
            .build();

    private CommandSpec tracking = CommandSpec.builder()
            .executor(new Leaderboard.StartTracking())
            .permission("aurapokemon.admin.tracking")
            .build();

    private CommandSpec give = CommandSpec.builder()
            .arguments(GenericArguments.player(Text.of("player")), GenericArguments.choices(Text.of("id"), new HashMap<String, String>(){{put("storm","storm");put("apocalyptic","apocalyptic");put("shiny","shiny");}}))
            .permission("aurapokemon.admin.give")
            .executor(new GiveParticleItem())
            .build();

    private CommandSpec leaderboard = CommandSpec.builder()
            .permission("aurapokemon.player.event")
            .description(Text.of("Displays information regarding the caught amount for this event."))
            .child(cleardata, "clear")
            .child(tracking, "tracking")
            .executor(new Leaderboard())
            .build();

    private CommandSpec aura = CommandSpec.builder()
            .description(Text.of("Default command for AuraPokemon"))
            .child(reload, "reload")
            .child(leaderboard, "event")
            //.child(give, "give")
            .build();
}
