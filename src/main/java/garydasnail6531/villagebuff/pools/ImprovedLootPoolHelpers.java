package garydasnail6531.villagebuff.pools;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

final class ImprovedLootPoolHelpers {
    private ImprovedLootPoolHelpers() {
    }

    static LootPool.Builder weightedPool(float minRolls, float maxRolls) {
        return LootPool.lootPool()
                .setRolls(UniformGenerator.between(minRolls, maxRolls));
    }

    static LootPoolSingletonContainer.Builder<?> stacked(ItemLike item, float min, float max) {
        return LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
    }
}
