package com.m1zark.aurapokemon;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class APInfo {
    public static final String ID = "aurapokemon";
    public static final String NAME = "AuraPokemon";
    public static final String VERSION = "1.2-S7.2";
    public static final String DESCRIPTION = "Spawning of Pokemon with special Auras and effects.";

    public static final Text PREFIX = Text.of(TextColors.AQUA, "AuraPokemon ", TextColors.GRAY, "\u00bb ", TextColors.DARK_AQUA);
    public static final Text ERROR_PREFIX = Text.of(TextColors.RED, "AuraPokemon ", TextColors.GRAY, "(", TextColors.RED, "Error", TextColors.GRAY, ") ", TextColors.DARK_RED);
    public static final Text DEBUG_PREFIX = Text.of(TextColors.AQUA, "AuraPokemon ", TextColors.GRAY, "(", TextColors.RED, "Debug", TextColors.GRAY, ") ", TextColors.DARK_AQUA);

    public enum Dependencies {
        Pixelmon("pixelmon", "8.2.x+");

        private String dependency;
        private String version;

        Dependencies(String dependency, String version){
            this.dependency = dependency;
            this.version = version;
        }

        public String getDependency() { return dependency; }

        public String getVersion() {
            return version;
        }
    }

    static void startup(){
        AuraPokemon.getInstance().getConsole().ifPresent(console -> console.sendMessages(
                Text.of(TextColors.AQUA, NAME, " v.", TextColors.GREEN, VERSION),
                Text.of(TextColors.GREEN, "Author: ", TextColors.AQUA, "m1zark"),
                Text.EMPTY
        ));
    }

    static boolean dependencyCheck(){
        boolean valid = true;

        for(Dependencies dependency : Dependencies.values()){
            if(!Sponge.getPluginManager().isLoaded(dependency.getDependency())){
                AuraPokemon.getInstance().getConsole().ifPresent(console -> console.sendMessages(Text.of(ERROR_PREFIX, Text.of(TextColors.DARK_RED, "==== Missing Dependency ===="))));
                AuraPokemon.getInstance().getConsole().ifPresent(console -> console.sendMessages(Text.of(ERROR_PREFIX, Text.of(TextColors.DARK_RED, "  Dependency: ", TextColors.RED, dependency.name()))));
                AuraPokemon.getInstance().getConsole().ifPresent(console -> console.sendMessages(Text.of(ERROR_PREFIX, Text.of(TextColors.DARK_RED, "  Version: ", TextColors.RED, dependency.getVersion()))));

                valid = false;
            }
        }
        return valid;
    }
}
