package garydasnail6531.villagebuff.pools;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class TrialChamberPools {
    public static void maceBoost(LootTable.Builder tableBuilder) {
        tableBuilder.pool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .with(LootItem.lootTableItem(Items.MACE).setWeight(1).build())
                .with(EmptyLootItem.emptyItem().setWeight(19).build())
                .build());
    }
}
