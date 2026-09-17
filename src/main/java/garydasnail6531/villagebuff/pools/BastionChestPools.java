package garydasnail6531.villagebuff.pools;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;

public class BastionChestPools {
    public static void init(LootTable.Builder tableBuilder) {
        tableBuilder.pool(ImprovedLootPoolHelpers.weightedPool(1.0F, 2.0F)
                .with(ImprovedLootPoolHelpers.stacked(Items.GOLD_INGOT, 3.0F, 8.0F).setWeight(18).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.IRON_INGOT, 1.0F, 4.0F).setWeight(10).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.CRYING_OBSIDIAN, 1.0F, 4.0F).setWeight(10).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.OBSIDIAN, 1.0F, 4.0F).setWeight(10).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.GOLDEN_CARROT, 2.0F, 5.0F).setWeight(8).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.SPECTRAL_ARROW, 6.0F, 14.0F).setWeight(6).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.DIAMOND, 1.0F, 1.0F).setWeight(1).build())
                .build());
    }
}
