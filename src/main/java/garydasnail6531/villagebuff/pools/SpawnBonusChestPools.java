package garydasnail6531.villagebuff.pools;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;

public class SpawnBonusChestPools {
    public static void init(LootTable.Builder tableBuilder) {
        tableBuilder.pool(ImprovedLootPoolHelpers.weightedPool(3.0F, 6.0F)
                .with(ImprovedLootPoolHelpers.stacked(Items.BREAD, 3.0F, 8.0F).setWeight(18).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.COOKED_BEEF, 2.0F, 5.0F).setWeight(12).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.TORCH, 8.0F, 20.0F).setWeight(12).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.IRON_INGOT, 2.0F, 6.0F).setWeight(10).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.STONE_PICKAXE, 1.0F, 1.0F).setWeight(8).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.STONE_AXE, 1.0F, 1.0F).setWeight(8).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.OAK_LOG, 8.0F, 20.0F).setWeight(8).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.DIAMOND, 1.0F, 2.0F).setWeight(1).build())
                .build());
    }
}
