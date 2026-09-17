package garydasnail6531.villagebuff.pools;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;

public class PiglinBarteringPools {
    public static void init(LootTable.Builder tableBuilder) {
        tableBuilder.pool(ImprovedLootPoolHelpers.weightedPool(1.0F, 1.0F)
                .with(ImprovedLootPoolHelpers.stacked(Items.ENDER_PEARL, 2.0F, 5.0F).setWeight(8).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.CRYING_OBSIDIAN, 1.0F, 3.0F).setWeight(7).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.OBSIDIAN, 1.0F, 3.0F).setWeight(7).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.SPECTRAL_ARROW, 4.0F, 10.0F).setWeight(5).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.IRON_NUGGET, 6.0F, 16.0F).setWeight(5).build())
                .with(EmptyLootItem.emptyItem().setWeight(30).build())
                .build());
    }
}
