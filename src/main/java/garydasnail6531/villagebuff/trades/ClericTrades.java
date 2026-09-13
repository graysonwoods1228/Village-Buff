package garydasnail6531.villagebuff.trades;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;

public class ClericTrades {

    public record TradeData(
            ItemCost cost,
            ItemStack result,
            int maxUses,
            int xp,
            float priceMultiplier
    ) {

        public MerchantOffer createOffer() {
            return new MerchantOffer(
                    cost,
                    result.copy(),
                    maxUses,
                    xp,
                    priceMultiplier
            );
        }
    }

    public static List<TradeData> getLevel1Trades() {
        return List.of(
                new TradeData(
                        new ItemCost(Items.ROTTEN_FLESH, 1),
                        new ItemStack(Items.EMERALD, 3),
                        500,
                        99999999,
                        0.00f
                ),

                new TradeData(
                        new ItemCost(Items.ROTTEN_FLESH, 1),
                        new ItemStack(Items.GOLDEN_CARROT, 3),
                        500,
                        99999999,
                        0.00f
                )
        );
    }

    public static List<TradeData> getLevel2Trades() {
        return List.of(
                new TradeData(
                        new ItemCost(Items.EMERALD, 1),
                        new ItemStack(Items.ROTTEN_FLESH, 2),
                        500,
                        99999999,
                        0.00f
                ),

                new TradeData(
                        new ItemCost(Items.EMERALD, 10),
                        PotionContents.createItemStack(Items.SPLASH_POTION, Potions.INVISIBILITY),
                        500,
                        99999999,
                        0.00f
                ),

                new TradeData(
                        new ItemCost(Items.EMERALD, 10),
                        PotionContents.createItemStack(Items.SPLASH_POTION, Potions.WATER_BREATHING),
                        500,
                        99999999,
                        0.00f
                ),

                new TradeData(
                        new ItemCost(Items.EMERALD, 3),
                        new ItemStack(Items.EXPERIENCE_BOTTLE, 64),
                        500,
                        99999999,
                        0.00f
                ),

                new TradeData(
                        new ItemCost(Items.EMERALD, 1),
                        new ItemStack(Items.NETHER_WART, 1),
                        500,
                        99999999,
                        0.00f
                ),

                new TradeData(
                        new ItemCost(Items.EMERALD, 1),
                        new ItemStack(Items.BLAZE_ROD, 2),
                        500,
                        99999999,
                        0f
                )
        );
    }

    public static List<TradeData> getTradesForLevel(int level) {
        return switch (level) {
            case 1 -> getLevel1Trades();
            case 2 -> getLevel2Trades();
            default -> List.of();
        };
    }

    public static void init() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 1, factories -> {
            for (TradeData trade : getLevel1Trades()) {
                factories.add((level, entity, random) ->
                        trade.createOffer()
                );
            }
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 2, factories -> {
            for (TradeData trade : getLevel2Trades()) {
                factories.add((level, entity, random) ->
                        trade.createOffer()
                );
            }
        });
    }
}
