package garydasnail6531.villagebuff.pools;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;

public class ShipwreckPools {
    public static void supply(LootTable.Builder tableBuilder) {
        tableBuilder.pool(ImprovedLootPoolHelpers.weightedPool(2.0F, 4.0F)
                .with(ImprovedLootPoolHelpers.stacked(Items.BREAD, 3.0F, 8.0F).setWeight(16).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.COOKED_COD, 3.0F, 8.0F).setWeight(14).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.COAL, 4.0F, 12.0F).setWeight(10).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.TORCH, 8.0F, 20.0F).setWeight(10).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.IRON_INGOT, 1.0F, 4.0F).setWeight(6).build())
                .build());
    }

    public static void treasure(LootTable.Builder tableBuilder) {
        tableBuilder.pool(ImprovedLootPoolHelpers.weightedPool(1.0F, 2.0F)
                .with(ImprovedLootPoolHelpers.stacked(Items.IRON_INGOT, 3.0F, 8.0F).setWeight(14).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.GOLD_INGOT, 2.0F, 6.0F).setWeight(10).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.EMERALD, 2.0F, 6.0F).setWeight(8).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.DIAMOND, 1.0F, 3.0F).setWeight(3).build())
                .build());
    }

    public static void map(LootTable.Builder tableBuilder) {
        tableBuilder.pool(ImprovedLootPoolHelpers.weightedPool(1.0F, 2.0F)
                .with(ImprovedLootPoolHelpers.stacked(Items.PAPER, 4.0F, 12.0F).setWeight(14).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.COMPASS, 1.0F, 1.0F).setWeight(6).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.CLOCK, 1.0F, 1.0F).setWeight(3).build())
                .with(ImprovedLootPoolHelpers.stacked(Items.EMERALD, 1.0F, 3.0F).setWeight(3).build())
                .build());
    }
}
