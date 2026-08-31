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
        tableBuilder.pool(ironPool.build());
        tableBuilder.pool(foodPool.build());
//        DiamondPools.init(registries, tableBuilder);
    }
}
