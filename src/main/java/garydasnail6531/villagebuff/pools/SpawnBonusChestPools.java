package garydasnail6531.villagebuff.pools;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;

public class SpawnBonusChestPools {
    public static void init(LootTable.Builder tableBuilder) {
        tableBuilder.pool(ImprovedLootPoolHelpers.weightedPool(2.0F, 4.0F)
                .with(ImprovedLootPoolHelpers.stacked(Items.BREAD, 2.0F, 5.0F).setWeight(18).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.COOKED_BEEF, 1.0F, 3.0F).setWeight(10).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.TORCH, 4.0F, 12.0F).setWeight(12).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.IRON_NUGGET, 4.0F, 10.0F).setWeight(10).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.STONE_PICKAXE, 1.0F, 1.0F).setWeight(8).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.STONE_AXE, 1.0F, 1.0F).setWeight(8).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.OAK_LOG, 4.0F, 10.0F).setWeight(8).build())
                .build());
    }
}
