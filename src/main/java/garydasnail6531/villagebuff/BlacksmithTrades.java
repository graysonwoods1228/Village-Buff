package garydasnail6531.villagebuff;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;

public class BlacksmithTrades {

    /*
     * ============================================================
     * TRADE DATA
     * ============================================================
     *
     * This stores the information needed to create a trade.
     * It does NOT store MerchantOffers themselves.
     */
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


    /*
     * ============================================================
     * LEVEL 1 TRADES
     * ============================================================
     */
    public static List<TradeData> getLevel1Trades() {

        return List.of(

                // 2 Iron Swords -> 1 Diamond Sword
                new TradeData(
                        new ItemCost(Items.IRON_SWORD, 2),
                        new ItemStack(Items.DIAMOND_SWORD, 1),
                        500,
                        200000,
                        0.00f
                ),

                // 8 Diamonds -> 1 Diamond Block
                new TradeData(
                        new ItemCost(Items.DIAMOND, 8),
                        new ItemStack(Items.DIAMOND_BLOCK, 1),
                        500,
                        200000,
                        0.00f
                )
        );
    }


    /*
     * ============================================================
     * LEVEL 2 TRADES
     * ============================================================
     */
    public static List<TradeData> getLevel2Trades() {

        return List.of(

                // 2 Diamond Swords -> 1 Netherite Upgrade Smithing Template
                new TradeData(
                        new ItemCost(Items.DIAMOND_SWORD, 2),
                        new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1),
                        500,
                        200000,
                        0.00f
                ),

                // 64 Diamond Blocks -> 1 Netherite Ingot
                new TradeData(
                        new ItemCost(Items.DIAMOND_BLOCK, 64),
                        new ItemStack(Items.NETHERITE_INGOT, 1),
                        500,
                        200000,
                        0.00f
                ),

                // 1 Netherite Upgrade Smithing Template -> 2 Netherite Upgrade Smithing Templates
                new TradeData(
                        new ItemCost(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1),
                        new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 2),
                        500,
                        200000,
                        0.00f
                )
        );
    }


    /*
     * ============================================================
     * LEVEL 3 TRADES
     * ============================================================
     */
    public static List<TradeData> getLevel3Trades() {

        return List.of(

                // 5 Netherite Ingots -> 1 Netherite Helmet
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 5),
                        new ItemStack(Items.NETHERITE_HELMET, 1),
                        500,
                        200000,
                        0.00f
                ),

                // 4 Netherite Ingots -> 1 Netherite Boots
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 4),
                        new ItemStack(Items.NETHERITE_BOOTS, 1),
                        500,
                        200000,
                        0.00f
                ),

                // 8 Netherite Ingots -> 1 Netherite Chestplate
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 8),
                        new ItemStack(Items.NETHERITE_CHESTPLATE, 1),
                        500,
                        200000,
                        0.00f
                ),

                // 7 Netherite Ingots -> 1 Netherite Leggings
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 7),
                        new ItemStack(Items.NETHERITE_LEGGINGS, 1),
                        500,
                        200000,
                        0.00f
                )
        );
    }


    /*
     * ============================================================
     * GET TRADES FOR A SPECIFIC LEVEL
     * ============================================================
     */
    public static List<TradeData> getTradesForLevel(int level) {

        return switch (level) {
            case 1 -> getLevel1Trades();
            case 2 -> getLevel2Trades();
            case 3 -> getLevel3Trades();
            default -> List.of();
        };
    }


    /*
     * ============================================================
     * NORMAL VILLAGER TRADE REGISTRATION
     * ============================================================
     *
     * These are the trades Weaponsmith villagers will normally
     * receive as they level up.
     */
    public static void init() {

        // LEVEL 1
        TradeOfferHelper.registerVillagerOffers(
                VillagerProfession.WEAPONSMITH,
                1,
                factories -> {

                    for (TradeData trade : getLevel1Trades()) {

                        factories.add((level, entity, random) ->
                                trade.createOffer()
                        );
                    }
                }
        );


        // LEVEL 2
        TradeOfferHelper.registerVillagerOffers(
                VillagerProfession.WEAPONSMITH,
                2,
                factories -> {

                    for (TradeData trade : getLevel2Trades()) {

                        factories.add((level, entity, random) ->
                                trade.createOffer()
                        );
                    }
                }
        );


        // LEVEL 3
        TradeOfferHelper.registerVillagerOffers(
                VillagerProfession.WEAPONSMITH,
                3,
                factories -> {

                    for (TradeData trade : getLevel3Trades()) {

                        factories.add((level, entity, random) ->
                                trade.createOffer()
                        );
                    }
                }
        );
    }
}