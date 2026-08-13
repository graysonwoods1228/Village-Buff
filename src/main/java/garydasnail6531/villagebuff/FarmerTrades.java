package garydasnail6531.villagebuff;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

public class FarmerTrades {
    public static void init()   {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.GOLDEN_APPLE, 128),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1),
                    500,
                    200000,
                    0.00f
            ));

            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.WHEAT_SEEDS, 1),
                    new ItemStack(Items.WHEAT, 1),
                    500,
                    200000,
                    0.00f
            ));

            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(Items.GOLDEN_CARROT, 3),
                    500,
                    200000,
                    0.00f
            ));
        });
    }
}
