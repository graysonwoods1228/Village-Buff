package garydasnail6531.villagebuff;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;


public class VillageBuff implements ModInitializer {
	public static final String MOD_ID = "villagebuff";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

			// 1. Advanced Weaponsmith chest overhaul
			if (BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(key)) {

				// Pool 1: Scattered Diamonds (Total 3 to 18)
				// We roll 3 to 6 separate times, grabbing 1 to 3 diamonds per slot
				LootPool.Builder diamondPool = LootPool.lootPool()
						.setRolls(UniformGenerator.between(3.0F, 6.0F))
						.with(LootItem.lootTableItem(Items.DIAMOND)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
								.build()
						);

				// Pool 2: Scattered Obsidian (Total 3 to 10)
				// We roll 3 to 5 separate times, grabbing 1 to 2 obsidian blocks per slot
				LootPool.Builder obsidianPool = LootPool.lootPool()
						.setRolls(UniformGenerator.between(3.0F, 5.0F))
						.with(LootItem.lootTableItem(Items.OBSIDIAN)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
								.build()
						);

				// Pool 3: Iron Tools Lottery (Always drops 1 to 2 random un-enchanted iron tools)
				// Tools don't stack, so we keep the count at 1, but roll 1 to 2 times to scatter them
				LootPool.Builder toolsPool = LootPool.lootPool()
						.setRolls(UniformGenerator.between(1.0F, 3.0F))
						.with(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(25).build())
						.with(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(10).build())
						.with(LootItem.lootTableItem(Items.IRON_AXE).setWeight(25).build())
						.with(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(10).build())
						.with(LootItem.lootTableItem(Items.IRON_SHOVEL).setWeight(25).build())
						.with(LootItem.lootTableItem(Items.IRON_HOE).setWeight(25).build())
						.with(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(25).build())
						.with(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(10).build());


				LootPool.Builder ironPool = LootPool.lootPool()
						.setRolls(UniformGenerator.between(3.0F, 6.0F))
						.with(LootItem.lootTableItem(Items.IRON_INGOT)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(10.0F, 24.0F)))
								.build()
						);

				// Inject all four distinct pools into the chest layout
				tableBuilder.pool(diamondPool.build());
				tableBuilder.pool(obsidianPool.build());
				tableBuilder.pool(toolsPool.build());
				tableBuilder.pool(ironPool.build());
			}

			// 2. Modifying regular Plains Village houses (Keeping your previous guaranteed setup)
			// Tannery chest loot
			if (BuiltInLootTables.VILLAGE_TANNERY.equals(key)) {
				LootPool.Builder tanneryUnbreakingPool = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.with(LootItem.lootTableItem(Items.ENCHANTED_BOOK)
								.apply((LootItemFunction.Builder) () -> {
									try {
										Constructor<SetEnchantmentsFunction> constructor =
												SetEnchantmentsFunction.class.getDeclaredConstructor(List.class, Map.class, boolean.class);

										constructor.setAccessible(true);

										return constructor.newInstance(
												List.of(),
												Map.of(
														registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
														ConstantValue.exactly(3)
												),
												false
										);
									} catch (ReflectiveOperationException exception) {
										throw new RuntimeException("Failed to create Unbreaking III enchanted book loot function", exception);
									}
								})
								.build()
						);

				tableBuilder.pool(tanneryUnbreakingPool.build());
			}
		});
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
