package com.m1zark.aurapokemon.Commands;

import com.google.common.collect.Lists;
import com.m1zark.m1utilities.api.Chat;
import com.m1zark.m1utilities.api.Inventories;
import org.apache.commons.lang3.text.WordUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class GiveParticleItem implements CommandExecutor {
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> player = args.getOne(Text.of("player"));
        Optional<String> id = args.getOne(Text.of("id"));

        player.ifPresent(p -> {
            if(!Inventories.giveItem(p, particleItem(id.orElse("")), 1)) {
                Chat.sendMessage(src, "&cCouldn't give item to " + p.getName() + " because of a full inventory and enderchest");
            }
        });

        return CommandResult.success();
    }

    public static ItemStack particleItem(String id) {
        Optional<ItemType> sprite = Sponge.getRegistry().getType(ItemType.class, "pixelmon:trio_badge");
        ItemStack Item = ItemStack.builder().itemType(sprite.get()).build();

        Item.offer(Keys.DISPLAY_NAME, Text.of(Chat.embedColours("&f" + WordUtils.capitalizeFully(id) + " Aura")));
        Item.offer(Keys.ITEM_LORE, Lists.newArrayList(Text.of(Chat.embedColours("&aRight click on your Pok\u00E9mon to apply this aura!"))));

        Item.offer(Keys.ITEM_ENCHANTMENTS, Lists.newArrayList(Enchantment.of(EnchantmentTypes.UNBREAKING, 1)));
        Item.offer(Keys.HIDE_ENCHANTMENTS, true);

        return ItemStack.builder().fromContainer(Item.toContainer().set(DataQuery.of("UnsafeData","auraID"), id)).build();
    }
}
