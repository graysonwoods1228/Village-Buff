package garydasnail6531.villagebuff;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;

public class FarmerTrades {

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
                    new ItemCost(Items.GOLDEN_APPLE, 128),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1),
                    500,
                    200000,
                    0.00f
                ),

                new TradeData(
                    new ItemCost(Items.WHEAT_SEEDS, 1),
                    new ItemStack(Items.WHEAT, 1),
                    500,
                    200000,
                    0.00f
                ),

                new TradeData(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(Items.GOLDEN_CARROT, 3),
                    500,
                    200000,
                    0.00f
                )
        );
    }

    public static List<TradeData> getLevel2Trades() {
        return List.of(
                new TradeData(
                        new ItemCost(Items.EMERALD, 1),
                        new ItemStack(Items.COOKED_BEEF, 64),
                        500,
                        200000,
                        0.00f
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
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
            for (TradeData trade : getLevel1Trades()) {
                factories.add((level, entity, random) ->
                        trade.createOffer()
                );
            }
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2, factories -> {
            for (TradeData trade : getLevel2Trades()) {
                factories.add((level, entity, random) ->
                        trade.createOffer()
                );
            }
        });
    }
}
