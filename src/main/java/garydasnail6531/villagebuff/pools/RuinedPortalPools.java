package garydasnail6531.villagebuff.pools;

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

public class RuinedPortalPools {
    public static void init(HolderGetter.Provider registries, LootTable.Builder tableBuilder) {


        // Pool 1: Scattered Diamonds (Total 3 to 18)
        // We roll 3 to 6 separate times, grabbing 1 to 3 diamonds per slot
        LootPool.Builder goldPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(3.0F, 6.0F))
                .with(LootItem.lootTableItem(Items.GOLD_BLOCK)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .build()
                );

        LootPool.Builder pantsPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(1.0F, 1.0F))
                .with(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS).setWeight(10)
                        .apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
                                        ConstantValue.exactly(3)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PROTECTION),
                                        ConstantValue.exactly(4)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SWIFT_SNEAK),
                                        ConstantValue.exactly(3)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_PROTECTION),
                                        ConstantValue.exactly(4)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
                                        ConstantValue.exactly(1)
                                )).apply(new SetEnchantmentsFunction.Builder()
                                .withEnchantment(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BLAST_PROTECTION),
                                        ConstantValue.exactly(4)
                                ))
                        .build());

        tableBuilder.pool(goldPool.build());
    }
}
