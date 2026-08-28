package garydasnail6531.villagebuff;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

public class BlacksmithTrades {

    public static void init() {
        // Add a trade to Blacksmith level 1 (Novice)
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 1, factories -> {
            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.IRON_SWORD, 2), // Cost A
                    new ItemStack(Items.DIAMOND_SWORD, 1), // Resulting item
                    500,  // Max uses
                    200000,  // Experience given
                    0.00f // Price multiplier
            ));

            factories.add((level, entity, random) -> new MerchantOffer(
                            new ItemCost(Items.DIAMOND, 8),
                            new ItemStack(Items.DIAMOND_BLOCK, 1),
                            500, // Max uses
                            200000, // Experience given
                            0.00f // Price multiplier
                    )
            );
        }); // <-- closes Weaponsmith Level 1

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 2, factories -> {
            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.DIAMOND_SWORD, 2),
                    new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1),
                    500,
                    200000,
                    0.00f
            ));

            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.DIAMOND_BLOCK, 64),
                    new ItemStack(Items.NETHERITE_INGOT, 1),
                    500,
                    200000,
                    0.00f
            ));

            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1),
                    new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 2),
                    500,
                    200000,
                    0.00f
            ));
        }); // <-- closes Weaponsmith Level 2

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 3, factories -> {
            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.NETHERITE_INGOT, 5),
                    new ItemStack(Items.NETHERITE_HELMET, 1),
                    500,
                    200000,
                    0.00f
            ));

            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.NETHERITE_INGOT, 4),
                    new ItemStack(Items.NETHERITE_BOOTS, 1),
                    500,
                    200000,
                    0.00f
            ));

            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.NETHERITE_INGOT, 8),
                    new ItemStack(Items.NETHERITE_CHESTPLATE, 1),
                    500,
                    200000,
                    0.00f
            ));

            factories.add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.NETHERITE_INGOT, 7),
                    new ItemStack(Items.NETHERITE_LEGGINGS, 1),
                    500,
                    200000,
                    0.00f
            ));


        });
    }
}
