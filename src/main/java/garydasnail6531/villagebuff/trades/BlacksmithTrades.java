package garydasnail6531.villagebuff.trades;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.item.equipment.trim.TrimPatterns;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;

public class BlacksmithTrades {

    public static ItemStack diamondSword(RegistryAccess registryAccess) {
        return new ItemStack(Items.DIAMOND_SWORD, 1);
    }

    public static ItemStack diamondBlock(RegistryAccess registryAccess) {
        return new ItemStack(Items.DIAMOND_BLOCK, 1);
    }

    public static ItemStack netheriteUpgradeTemplate(RegistryAccess registryAccess) {
        return new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1);
    }

    public static ItemStack netheriteIngot(RegistryAccess registryAccess) {
        return new ItemStack(Items.NETHERITE_INGOT, 1);
    }

    public static ItemStack doubledNetheriteUpgradeTemplate(RegistryAccess registryAccess) {
        return new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 2);
    }

    public static ItemStack maxedNetheriteHelmet(RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Items.NETHERITE_HELMET, 1);
        enchantStandardArmor(registryAccess, stack);
        enchant(registryAccess, stack, Enchantments.RESPIRATION, 3);
        enchant(registryAccess, stack, Enchantments.AQUA_AFFINITY, 1);
        trim(registryAccess, stack, TrimPatterns.WARD, TrimMaterials.AMETHYST);
        return stack;
    }

    public static ItemStack maxedNetheriteChestplate(RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Items.NETHERITE_CHESTPLATE, 1);
        enchantStandardArmor(registryAccess, stack);
        trim(registryAccess, stack, TrimPatterns.SILENCE, TrimMaterials.AMETHYST);
        return stack;
    }

    public static ItemStack maxedNetheriteLeggings(RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Items.NETHERITE_LEGGINGS, 1);
        enchantStandardArmor(registryAccess, stack);
        enchant(registryAccess, stack, Enchantments.SWIFT_SNEAK, 3);
        trim(registryAccess, stack, TrimPatterns.RIB, TrimMaterials.REDSTONE);
        return stack;
    }

    public static ItemStack maxedNetheriteBoots(RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Items.NETHERITE_BOOTS, 1);
        enchantStandardArmor(registryAccess, stack);
        enchant(registryAccess, stack, Enchantments.FEATHER_FALLING, 4);
        enchant(registryAccess, stack, Enchantments.DEPTH_STRIDER, 3);
        enchant(registryAccess, stack, Enchantments.FROST_WALKER, 2);
        enchant(registryAccess, stack, Enchantments.SOUL_SPEED, 3);
        trim(registryAccess, stack, TrimPatterns.BOLT, TrimMaterials.REDSTONE);
        return stack;
    }

    public static void enchantStandardArmor(RegistryAccess registryAccess, ItemStack stack) {
        enchant(registryAccess, stack, Enchantments.PROTECTION, 4);
        enchant(registryAccess, stack, Enchantments.BLAST_PROTECTION, 4);
        enchant(registryAccess, stack, Enchantments.FIRE_PROTECTION, 4);
        enchant(registryAccess, stack, Enchantments.PROJECTILE_PROTECTION, 4);
        enchant(registryAccess, stack, Enchantments.UNBREAKING, 3);
        enchant(registryAccess, stack, Enchantments.MENDING, 1);
        enchant(registryAccess, stack, Enchantments.THORNS, 3);
    }

    public static ItemStack maxedNetheriteSword(RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Items.NETHERITE_SWORD, 1);
        enchantStandardTool(registryAccess, stack);
        enchant(registryAccess, stack, Enchantments.SHARPNESS, 5);
        enchant(registryAccess, stack, Enchantments.BANE_OF_ARTHROPODS, 5);
        enchant(registryAccess, stack, Enchantments.SMITE, 5);
        enchant(registryAccess, stack, Enchantments.KNOCKBACK, 2);
        enchant(registryAccess, stack, Enchantments.FIRE_ASPECT, 2);
        enchant(registryAccess, stack, Enchantments.LOOTING, 10);
        enchant(registryAccess, stack, Enchantments.SWEEPING_EDGE, 3);
        return stack;
    }

    public static ItemStack maxedNetheriteFortunePickaxe(RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Items.NETHERITE_PICKAXE, 1);
        enchantStandardTool(registryAccess, stack);
        enchant(registryAccess, stack, Enchantments.EFFICIENCY, 5);
        enchant(registryAccess, stack, Enchantments.FORTUNE, 3);
        return stack;
    }

    public static ItemStack maxedNetheriteSilkTouchPickaxe(RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Items.NETHERITE_PICKAXE, 1);
        enchantStandardTool(registryAccess, stack);
        enchant(registryAccess, stack, Enchantments.EFFICIENCY, 5);
        enchant(registryAccess, stack, Enchantments.SILK_TOUCH, 1);
        return stack;
    }

    public static ItemStack maxedNetheriteAxe(RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Items.NETHERITE_AXE, 1);
        enchantStandardTool(registryAccess, stack);
        enchant(registryAccess, stack, Enchantments.EFFICIENCY, 5);
        enchant(registryAccess, stack, Enchantments.FORTUNE, 3);
        enchant(registryAccess, stack, Enchantments.SILK_TOUCH, 1);
        enchant(registryAccess, stack, Enchantments.SHARPNESS, 5);
        enchant(registryAccess, stack, Enchantments.SMITE, 5);
        enchant(registryAccess, stack, Enchantments.BANE_OF_ARTHROPODS, 5);
        return stack;
    }

    public static ItemStack maxedNetheriteShovel(RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Items.NETHERITE_SHOVEL, 1);
        enchantStandardTool(registryAccess, stack);
        enchant(registryAccess, stack, Enchantments.EFFICIENCY, 5);
        enchant(registryAccess, stack, Enchantments.FORTUNE, 3);
        enchant(registryAccess, stack, Enchantments.SILK_TOUCH, 1);
        return stack;
    }

    public static ItemStack maxedNetheriteHoe(RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Items.NETHERITE_HOE, 1);
        enchantStandardTool(registryAccess, stack);
        enchant(registryAccess, stack, Enchantments.EFFICIENCY, 5);
        enchant(registryAccess, stack, Enchantments.FORTUNE, 3);
        enchant(registryAccess, stack, Enchantments.SILK_TOUCH, 1);
        return stack;
    }

    public static void enchantStandardTool(RegistryAccess registryAccess, ItemStack stack) {
        enchant(registryAccess, stack, Enchantments.UNBREAKING, 3);
        enchant(registryAccess, stack, Enchantments.MENDING, 1);
    }

    private static void enchant(RegistryAccess registryAccess, ItemStack stack, ResourceKey<Enchantment> enchantment, int level) {
        Holder.Reference<Enchantment> enchantmentHolder =
                registryAccess.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment);

        ItemEnchantments.Mutable enchantments =
                new ItemEnchantments.Mutable(stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));

        enchantments.set(enchantmentHolder, 10);
        stack.set(DataComponents.ENCHANTMENTS, enchantments.toImmutable());
    }

    private static void trim(
            RegistryAccess registryAccess,
            ItemStack stack,
            ResourceKey<TrimPattern> pattern,
            ResourceKey<TrimMaterial> material
    ) {
        Holder.Reference<TrimPattern> patternHolder =
                registryAccess.lookupOrThrow(Registries.TRIM_PATTERN).getOrThrow(pattern);
        Holder.Reference<TrimMaterial> materialHolder =
                registryAccess.lookupOrThrow(Registries.TRIM_MATERIAL).getOrThrow(material);

        stack.set(DataComponents.TRIM, new ArmorTrim(materialHolder, patternHolder));
    }

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
            ResultFactory resultFactory,
            int maxUses,
            int xp,
            float priceMultiplier
    ) {

        public TradeData(ItemCost cost, ItemStack result, int maxUses, int xp, float priceMultiplier) {
            this(cost, registryAccess -> result.copy(), maxUses, xp, priceMultiplier);
        }

        public MerchantOffer createOffer() {
            return createOffer(RegistryAccess.EMPTY);
        }

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
                        BlacksmithTrades::diamondSword,
                        500,
                        200000,
                        0.00f
                ),

                // 8 Diamonds -> 1 Diamond Block
                new TradeData(
                        new ItemCost(Items.DIAMOND, 8),
                        BlacksmithTrades::diamondBlock,
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
                        BlacksmithTrades::netheriteUpgradeTemplate,
                        500,
                        200000,
                        0.00f
                ),

                // 64 Diamond Blocks -> 1 Netherite Ingot
                new TradeData(
                        new ItemCost(Items.DIAMOND_BLOCK, 64),
                        BlacksmithTrades::netheriteIngot,
                        500,
                        200000,
                        0.00f
                ),

                // 1 Netherite Upgrade Smithing Template -> 2 Netherite Upgrade Smithing Templates
                new TradeData(
                        new ItemCost(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1),
                        BlacksmithTrades::doubledNetheriteUpgradeTemplate,
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
//                        new ItemStack(Items.NETHERITE_HELMET),
                        BlacksmithTrades::maxedNetheriteHelmet,
                        500,
                        200000,
                        0.00f
                ),

                // 4 Netherite Ingots -> 1 Netherite Boots
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 4),
//                        new ItemCost(Items.NETHERITE_BOOTS, 1),
                        BlacksmithTrades::maxedNetheriteBoots,
                        500,
                        200000,
                        0.00f
                ),

                // 8 Netherite Ingots -> 1 Netherite Chestplate
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 8),
//                        new ItemCost(Items.NETHERITE_CHESTPLATE, 1),
                        BlacksmithTrades::maxedNetheriteChestplate,
                        500,
                        200000,
                        0.00f
                ),

                // 7 Netherite Ingots -> 1 Netherite Leggings
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 7),
//                        new ItemCost(Items.NETHERITE_LEGGINGS, 1),
                        BlacksmithTrades::maxedNetheriteLeggings,
                        500,
                        200000,
                        0.00f
                ),

                // 2 Netherite Ingots -> 1 Maxed Netherite Sword
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 2),
//                        new ItemCost(Items.NETHERITE_SWORD, 1),
                        BlacksmithTrades::maxedNetheriteSword,
                        500,
                        200000,
                        0.00f
                ),

                // 3 Netherite Ingots -> 1 Maxed Fortune Netherite Pickaxe
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 3),
//                        new ItemCost(Items.NETHERITE_PICKAXE, 1),
                        BlacksmithTrades::maxedNetheriteFortunePickaxe,
                        500,
                        200000,
                        0.00f
                ),

                // 3 Netherite Ingots -> 1 Maxed Silk Touch Netherite Pickaxe
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 3),
//                        new ItemCost(Items.NETHERITE_PICKAXE, 1),
                        BlacksmithTrades::maxedNetheriteSilkTouchPickaxe,
                        500,
                        200000,
                        0.00f
                ),

                // 3 Netherite Ingots -> 1 Maxed Netherite Axe
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 3),
//                        new ItemCost(Items.NETHERITE_AXE, 1),
                        BlacksmithTrades::maxedNetheriteAxe,
                        500,
                        200000,
                        0.00f
                ),

                // 1 Netherite Ingot -> 1 Maxed Netherite Shovel
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 1),
//                        new ItemCost(Items.NETHERITE_SHOVEL, 1),
                        BlacksmithTrades::maxedNetheriteShovel,
                        500,
                        200000,
                        0.00f
                ),

                // 2 Netherite Ingots -> 1 Maxed Netherite Hoe
                new TradeData(
                        new ItemCost(Items.NETHERITE_INGOT, 2),
//                        new ItemCost(Items.NETHERITE_HOE, 1),
                        BlacksmithTrades::maxedNetheriteHoe,
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
                                trade.createOffer(entity.registryAccess())
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
                                trade.createOffer(entity.registryAccess())
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
                                trade.createOffer(entity.registryAccess())
                        );
                    }
                }
        );
    }
}
