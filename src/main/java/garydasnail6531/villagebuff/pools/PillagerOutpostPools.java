package garydasnail6531.villagebuff.pools;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;

public class PillagerOutpostPools {
    public static void init(LootTable.Builder tableBuilder) {
        tableBuilder.pool(ImprovedLootPoolHelpers.weightedPool(2.0F, 4.0F)
                .with(ImprovedLootPoolHelpers.stacked(Items.ARROW, 12.0F, 32.0F).setWeight(18).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.IRON_INGOT, 2.0F, 6.0F).setWeight(12).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.BREAD, 2.0F, 6.0F).setWeight(12).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.CROSSBOW, 1.0F, 1.0F).setWeight(5).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.EMERALD, 1.0F, 4.0F).setWeight(5).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.DIAMOND, 1.0F, 2.0F).setWeight(1).build())
                .build());
    }
}
