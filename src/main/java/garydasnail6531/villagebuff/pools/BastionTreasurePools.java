package garydasnail6531.villagebuff.pools;

import garydasnail6531.villagebuff.Duper.DuperBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class BastionTreasurePools {

    public static void init(HolderGetter.Provider registries, LootTable.Builder tableBuilder) {
        LootPool.Builder richesPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(2.0F, 4.0F))
                .with(stacked(Items.GOLD_BLOCK, 2.0F, 6.0F).setWeight(18).build())
                .with(stacked(Items.DIAMOND, 2.0F, 5.0F).setWeight(12).build())
                .with(stacked(Items.EMERALD, 3.0F, 8.0F).setWeight(12).build())
                .with(stacked(Items.ANCIENT_DEBRIS, 1.0F, 3.0F).setWeight(8).build())
                .with(stacked(Items.NETHERITE_SCRAP, 1.0F, 2.0F).setWeight(6).build())
                .with(stacked(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1.0F, 1.0F).setWeight(3).build())
                .with(stacked(Items.ENCHANTED_GOLDEN_APPLE, 1.0F, 1.0F).setWeight(2).build())
                .with(stacked(DuperBlocks.DUPER_ITEM, 1.0F, 1.0F).setWeight(1).build());

        LootPool.Builder armorPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(1.0F, 2.0F))
                .with(maxedArmor(registries, Items.NETHERITE_HELMET)
                        .apply(enchant(registries, Enchantments.RESPIRATION, 3.0F))
                        .apply(enchant(registries, Enchantments.AQUA_AFFINITY, 1.0F))
                        .build())
                .with(maxedArmor(registries, Items.NETHERITE_CHESTPLATE).build())
                .with(maxedArmor(registries, Items.NETHERITE_LEGGINGS)
                        .apply(enchant(registries, Enchantments.SWIFT_SNEAK, 3.0F))
                        .build())
                .with(maxedArmor(registries, Items.NETHERITE_BOOTS)
                        .apply(enchant(registries, Enchantments.FEATHER_FALLING, 4.0F))
                        .apply(enchant(registries, Enchantments.DEPTH_STRIDER, 3.0F))
                        .apply(enchant(registries, Enchantments.SOUL_SPEED, 3.0F))
                        .build());

        LootPool.Builder toolPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(1.0F, 2.0F))
                .with(maxedSword(registries).setWeight(12).build())
                .with(maxedFortunePickaxe(registries).setWeight(10).build())
                .with(maxedSilkTouchPickaxe(registries).setWeight(10).build())
                .with(maxedAxe(registries).setWeight(10).build())
                .with(maxedDiggingTool(registries, Items.NETHERITE_SHOVEL).setWeight(8).build())
                .with(maxedDiggingTool(registries, Items.NETHERITE_HOE).setWeight(8).build())
                .with(maxedBow(registries).setWeight(6).build())
                .with(maxedCrossbow(registries).setWeight(6).build());

        tableBuilder.pool(richesPool.build());
        tableBuilder.pool(armorPool.build());
        tableBuilder.pool(toolPool.build());
    }

    private static LootPoolSingletonContainer.Builder<?> stacked(ItemLike item, float min, float max) {
        return LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
    }

    private static LootPoolSingletonContainer.Builder<?> maxedArmor(HolderGetter.Provider registries, Item item) {
        return LootItem.lootTableItem(item)
                .apply(enchant(registries, Enchantments.PROTECTION, 4.0F))
                .apply(enchant(registries, Enchantments.UNBREAKING, 3.0F))
                .apply(enchant(registries, Enchantments.MENDING, 1.0F))
                .apply(enchant(registries, Enchantments.THORNS, 1.0F));
    }

    private static LootPoolSingletonContainer.Builder<?> maxedSword(HolderGetter.Provider registries) {
        return LootItem.lootTableItem(Items.NETHERITE_SWORD)
                .apply(enchant(registries, Enchantments.UNBREAKING, 3.0F))
                .apply(enchant(registries, Enchantments.MENDING, 1.0F))
                .apply(enchant(registries, Enchantments.SHARPNESS, 5.0F))
                .apply(enchant(registries, Enchantments.FIRE_ASPECT, 2.0F))
                .apply(enchant(registries, Enchantments.LOOTING, 2.0F));
    }

    private static LootPoolSingletonContainer.Builder<?> maxedFortunePickaxe(HolderGetter.Provider registries) {
        return LootItem.lootTableItem(Items.NETHERITE_PICKAXE)
                .apply(enchantStandardTool(registries))
                .apply(enchant(registries, Enchantments.EFFICIENCY, 5.0F))
                .apply(enchant(registries, Enchantments.FORTUNE, 3.0F));
    }

    private static LootPoolSingletonContainer.Builder<?> maxedSilkTouchPickaxe(HolderGetter.Provider registries) {
        return LootItem.lootTableItem(Items.NETHERITE_PICKAXE)
                .apply(enchantStandardTool(registries))
                .apply(enchant(registries, Enchantments.EFFICIENCY, 5.0F))
                .apply(enchant(registries, Enchantments.SILK_TOUCH, 1.0F));
    }

    private static LootPoolSingletonContainer.Builder<?> maxedAxe(HolderGetter.Provider registries) {
        return maxedDiggingTool(registries, Items.NETHERITE_AXE)
                .apply(enchant(registries, Enchantments.SHARPNESS, 4.0F));
    }

    private static LootPoolSingletonContainer.Builder<?> maxedDiggingTool(HolderGetter.Provider registries, Item item) {
        return LootItem.lootTableItem(item)
                .apply(enchantStandardTool(registries))
                .apply(enchant(registries, Enchantments.EFFICIENCY, 5.0F))
                .apply(enchant(registries, Enchantments.FORTUNE, 2.0F));
    }

    private static LootPoolSingletonContainer.Builder<?> maxedBow(HolderGetter.Provider registries) {
        return LootItem.lootTableItem(Items.BOW)
                .apply(enchantStandardTool(registries))
                .apply(enchant(registries, Enchantments.POWER, 5.0F))
                .apply(enchant(registries, Enchantments.PUNCH, 2.0F))
                .apply(enchant(registries, Enchantments.FLAME, 1.0F));
    }

    private static LootPoolSingletonContainer.Builder<?> maxedCrossbow(HolderGetter.Provider registries) {
        return LootItem.lootTableItem(Items.CROSSBOW)
                .apply(enchantStandardTool(registries))
                .apply(enchant(registries, Enchantments.PIERCING, 4.0F))
                .apply(enchant(registries, Enchantments.QUICK_CHARGE, 3.0F));
    }

    private static SetEnchantmentsFunction.Builder enchantStandardTool(HolderGetter.Provider registries) {
        return new SetEnchantmentsFunction.Builder()
                .withEnchantment(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING), ConstantValue.exactly(3.0F))
                .withEnchantment(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING), ConstantValue.exactly(1.0F));
    }

    private static SetEnchantmentsFunction.Builder enchant(
            HolderGetter.Provider registries,
            ResourceKey<Enchantment> enchantment,
            float level
    ) {
        return new SetEnchantmentsFunction.Builder()
                .withEnchantment(
                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment),
                        ConstantValue.exactly(level)
                );
    }
}
