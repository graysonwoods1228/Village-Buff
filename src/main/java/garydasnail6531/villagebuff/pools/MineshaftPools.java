package garydasnail6531.villagebuff.pools;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;

public class MineshaftPools {
    public static void init(LootTable.Builder tableBuilder) {
        tableBuilder.pool(ImprovedLootPoolHelpers.weightedPool(2.0F, 4.0F)
                .with(ImprovedLootPoolHelpers.stacked(Items.IRON_INGOT, 2.0F, 6.0F).setWeight(18).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.TORCH, 8.0F, 24.0F).setWeight(16).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.RAIL, 8.0F, 24.0F).setWeight(14).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.BREAD, 2.0F, 5.0F).setWeight(12).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.COAL, 4.0F, 12.0F).setWeight(12).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.NAME_TAG, 1.0F, 1.0F).setWeight(3).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.DIAMOND, 1.0F, 2.0F).setWeight(2).build())
                .build());
    }
}
