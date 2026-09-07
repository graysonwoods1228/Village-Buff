package garydasnail6531.villagebuff.trades;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;

public class FletcherTrades {

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
                        new ItemCost(Items.STICK, 1),
                        new ItemStack(Items.EMERALD, 64),
                        500,
                        200000,
                        0.00f
                )
        );
    }

    public static List<TradeData> getTradesForLevel(int level) {
        return switch (level) {
            case 1 -> getLevel1Trades();
            default -> List.of();
        };
    }

    public static void init() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FLETCHER, 1, factories -> {
            for (TradeData trade : getLevel1Trades()) {
                factories.add((level, entity, random) ->
                        trade.createOffer()
                );
            }
        });
    }
}
