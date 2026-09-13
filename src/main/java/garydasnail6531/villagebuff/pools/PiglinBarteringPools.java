package garydasnail6531.villagebuff.pools;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;

public class PiglinBarteringPools {
    public static void init(LootTable.Builder tableBuilder) {
        tableBuilder.pool(ImprovedLootPoolHelpers.weightedPool(1.0F, 1.0F)
                .with(ImprovedLootPoolHelpers.stacked(Items.ENDER_PEARL, 4.0F, 8.0F).setWeight(10).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.CRYING_OBSIDIAN, 2.0F, 6.0F).setWeight(9).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.OBSIDIAN, 2.0F, 6.0F).setWeight(9).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.SPECTRAL_ARROW, 8.0F, 16.0F).setWeight(5).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.IRON_NUGGET, 8.0F, 24.0F).setWeight(5).build())
                .with(EmptyLootItem.emptyItem().setWeight(20).build())
                .build());
    }
}
