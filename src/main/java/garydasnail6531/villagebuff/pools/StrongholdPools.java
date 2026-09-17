package garydasnail6531.villagebuff.pools;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;

public class StrongholdPools {
    public static void init(LootTable.Builder tableBuilder) {
        tableBuilder.pool(ImprovedLootPoolHelpers.weightedPool(1.0F, 2.0F)
                .with(ImprovedLootPoolHelpers.stacked(Items.ENDER_PEARL, 1.0F, 2.0F).setWeight(12).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.IRON_INGOT, 1.0F, 4.0F).setWeight(10).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.BOOK, 2.0F, 5.0F).setWeight(10).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.EXPERIENCE_BOTTLE, 2.0F, 6.0F).setWeight(6).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.GOLDEN_APPLE, 1.0F, 1.0F).setWeight(2).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.DIAMOND, 1.0F, 1.0F).setWeight(1).build())
                .build());
    }
}
