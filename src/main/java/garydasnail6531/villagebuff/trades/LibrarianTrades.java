package garydasnail6531.villagebuff.trades;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;

public class LibrarianTrades {

    public static ItemStack enchantedBook(
            RegistryAccess registryAccess,
            ResourceKey<Enchantment> enchantment,
            int level
    ) {
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK, 1);
        Holder.Reference<Enchantment> enchantmentHolder =
                registryAccess.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment);

        ItemEnchantments.Mutable enchantments =
                new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

        enchantments.set(enchantmentHolder, level);
        stack.set(DataComponents.STORED_ENCHANTMENTS, enchantments.toImmutable());
        return stack;
    }

    public record TradeData(
            ItemCost cost,
            ResultFactory resultFactory,
            int maxUses,
            int xp,
            float priceMultiplier
    ) {

        public MerchantOffer createOffer(RegistryAccess registryAccess) {
            return new MerchantOffer(
                    cost,
                    resultFactory.create(registryAccess),
                    maxUses,
                    xp,
                    priceMultiplier
            );
        }
    }

    private interface ResultFactory {
        ItemStack create(RegistryAccess registryAccess);
    }

    private static TradeData bookTrade(ResourceKey<Enchantment> enchantment, int level, int emeraldCost) {
        return new TradeData(
                new ItemCost(Items.EMERALD, emeraldCost),
                registryAccess -> enchantedBook(registryAccess, enchantment, level),
                500,
                99999999,
                0.00f
        );
    }

    public static List<TradeData> getLevel1Trades() {
        return List.of(
                bookTrade(Enchantments.UNBREAKING, 3, 1),
                bookTrade(Enchantments.PROTECTION, 4, 1),
                bookTrade(Enchantments.SHARPNESS, 5, 1),
                bookTrade(Enchantments.POWER, 5, 1),
                bookTrade(Enchantments.FORTUNE, 3, 1)
        );
    }

    public static List<TradeData> getLevel2Trades() {
        return List.of(
                bookTrade(Enchantments.EFFICIENCY, 5, 1),
                bookTrade(Enchantments.MENDING, 1, 1),
                bookTrade(Enchantments.THORNS, 3, 1),
                bookTrade(Enchantments.SILK_TOUCH, 1, 1),
                bookTrade(Enchantments.LOOTING, 3, 1),
                bookTrade(Enchantments.FEATHER_FALLING, 4, 1),
                bookTrade(Enchantments.RESPIRATION, 3, 1),
                bookTrade(Enchantments.AQUA_AFFINITY, 1, 1)
        );
    }

    public static List<TradeData> getLevel3Trades() {
        return List.of(
                bookTrade(Enchantments.SWEEPING_EDGE, 3, 1),
                bookTrade(Enchantments.FIRE_ASPECT, 2, 1),
                bookTrade(Enchantments.KNOCKBACK, 2, 1),
                bookTrade(Enchantments.FLAME, 1, 1),
                bookTrade(Enchantments.INFINITY, 1, 1),
                bookTrade(Enchantments.PUNCH, 2, 1),
                bookTrade(Enchantments.QUICK_CHARGE, 3, 1),
                bookTrade(Enchantments.MULTISHOT, 1, 1)
        );
    }

    public static List<TradeData> getLevel4Trades() {
        return List.of(
                bookTrade(Enchantments.DEPTH_STRIDER, 3, 1),
                bookTrade(Enchantments.FROST_WALKER, 2, 1),
                bookTrade(Enchantments.SOUL_SPEED, 3, 1),
                bookTrade(Enchantments.SWIFT_SNEAK, 3, 1),
                bookTrade(Enchantments.LUCK_OF_THE_SEA, 3, 1),
                bookTrade(Enchantments.LURE, 3, 1),
                bookTrade(Enchantments.LOYALTY, 3, 1),
                bookTrade(Enchantments.CHANNELING, 1, 1)
        );
    }

    public static List<TradeData> getLevel5Trades() {
        return List.of(
                bookTrade(Enchantments.RIPTIDE, 3, 1),
                bookTrade(Enchantments.IMPALING, 5, 1),
                bookTrade(Enchantments.DENSITY, 5, 1),
                bookTrade(Enchantments.BREACH, 4, 1),
                bookTrade(Enchantments.WIND_BURST, 3, 1),
                bookTrade(Enchantments.BANE_OF_ARTHROPODS, 5, 1),
                bookTrade(Enchantments.SMITE, 5, 1),
                bookTrade(Enchantments.PROJECTILE_PROTECTION, 4, 1)
        );
    }

    public static List<TradeData> getTradesForLevel(int level) {
        return switch (level) {
            case 1 -> getLevel1Trades();
            case 2 -> getLevel2Trades();
            case 3 -> getLevel3Trades();
            case 4 -> getLevel4Trades();
            case 5 -> getLevel5Trades();
            default -> List.of();
        };
    }

    public static void init() {
        for (int level = 1; level <= 5; level++) {
            int tradeLevel = level;
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, tradeLevel, factories -> {
                for (TradeData trade : getTradesForLevel(tradeLevel)) {
                    factories.add((levelValue, entity, random) ->
                            trade.createOffer(entity.registryAccess())
                    );
                }
            });
        }
    }
}
