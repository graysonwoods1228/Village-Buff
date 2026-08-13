package garydasnail6531.villagebuff;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class BlacksmithPools {

    public static void init(HolderGetter.Provider registries, LootTable.Builder tableBuilder) {


        // Pool 1: Scattered Diamonds (Total 3 to 18)
        // We roll 3 to 6 separate times, grabbing 1 to 3 diamonds per slot
        LootPool.Builder diamondPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(3.0F, 6.0F))
                .with(LootItem.lootTableItem(Items.DIAMOND)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .build()
                );

        // Pool 2: Scattered Obsidian (Total 3 to 10)
        // We roll 3 to 5 separate times, grabbing 1 to 2 obsidian blocks per slot
        LootPool.Builder obsidianPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(3.0F, 5.0F))
                .with(LootItem.lootTableItem(Items.OBSIDIAN)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .build()
                );

        // Pool 3: Iron Tools Lottery (Always drops 1 to 2 random un-enchanted iron tools)
        // Tools don't stack, so we keep the count at 1, but roll 1 to 2 times to scatter them
        LootPool.Builder swordPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(1.0F, 1.0F))
                .with(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(10)
                        .apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
                                        ConstantValue.exactly(3)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SHARPNESS),
                                        ConstantValue.exactly(5)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT),
                                        ConstantValue.exactly(2)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK),
                                        ConstantValue.exactly(2)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
                                        ConstantValue.exactly(1)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING),
                                        ConstantValue.exactly(3)
                                ))
                        .build());

        LootPool.Builder pickPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(1.0F, 1.0F))
                .with(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(10)
                        .apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
                                        ConstantValue.exactly(3)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY),
                                        ConstantValue.exactly(5)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
                                        ConstantValue.exactly(1)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE),
                                        ConstantValue.exactly(3)
                                ))
                        .build());

        LootPool.Builder pickPool2 = LootPool.lootPool()
                .setRolls(UniformGenerator.between(1.0F, 1.0F))
                .with(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(10)
                        .apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
                                        ConstantValue.exactly(3)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY),
                                        ConstantValue.exactly(5)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
                                        ConstantValue.exactly(1)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH),
                                        ConstantValue.exactly(1)
                                ))
                        .build());

        LootPool.Builder axePool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(1.0F, 1.0F))
                .with(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(10)
                        .apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
                                        ConstantValue.exactly(3)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY),
                                        ConstantValue.exactly(5)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
                                        ConstantValue.exactly(1)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH),
                                        ConstantValue.exactly(1)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SWEEPING_EDGE),
                                        ConstantValue.exactly(5)
                                ))
                        .build());

        LootPool.Builder ironPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(3.0F, 6.0F))
                .with(LootItem.lootTableItem(Items.IRON_INGOT)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(10.0F, 24.0F)))
                        .build()
                );

        LootPool.Builder foodPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(1, 3))
                .with(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                        .build()
                );

        // Inject all distinct pools into the chest layout
        tableBuilder.pool(diamondPool.build());
        tableBuilder.pool(obsidianPool.build());
        tableBuilder.pool(swordPool.build());
        tableBuilder.pool(pickPool.build());
        tableBuilder.pool(pickPool2.build());
        tableBuilder.pool(axePool.build());
        tableBuilder.pool(ironPool.build());
        tableBuilder.pool(foodPool.build());
    }
}
