package garydasnail6531.villagebuff.pools;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;

public class MineshaftPools {
    public static void init(LootTable.Builder tableBuilder) {
        tableBuilder.pool(ImprovedLootPoolHelpers.weightedPool(1.0F, 3.0F)
                .with(ImprovedLootPoolHelpers.stacked(Items.IRON_INGOT, 1.0F, 4.0F).setWeight(18).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.TORCH, 6.0F, 16.0F).setWeight(16).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.RAIL, 6.0F, 18.0F).setWeight(14).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.BREAD, 1.0F, 4.0F).setWeight(12).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.COAL, 3.0F, 8.0F).setWeight(12).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.NAME_TAG, 1.0F, 1.0F).setWeight(3).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.DIAMOND, 1.0F, 1.0F).setWeight(1).build())
                .build());
    }
}
