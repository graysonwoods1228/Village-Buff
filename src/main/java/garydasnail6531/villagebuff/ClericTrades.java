package garydasnail6531.villagebuff;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

public class ClericTrades {
    public static void init() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 1, factories -> {
            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.ROTTEN_FLESH, 1),
                    new ItemStack(Items.EMERALD, 3),
                    500,
                    200000,
                    0.00f
            ));

            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.ROTTEN_FLESH, 1),
                    new ItemStack(Items.GOLDEN_CARROT, 3),
                    500,
                    200000,
                    0.00f
            ));
        });
    }
}
