package garydasnail6531.villagebuff;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
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
						.with(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(10)
								// .apply(new SetEnchantmentsFunction.Builder()
//								.withEnchantment(
//										registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
//										ConstantValue.exactly(3)
//								)).
								.build());

				LootPool.Builder ironPool = LootPool.lootPool()
						.setRolls(UniformGenerator.between(3.0F, 6.0F))
						.with(LootItem.lootTableItem(Items.IRON_INGOT)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(10.0F, 24.0F)))
								.build()
						);

				LootPool.Builder foodPool = LootPool.lootPool()
						.setRolls(UniformGenerator.between(1, 3))
						.with(LootItem.lootTableItem(Items.GOLDEN_APPLE)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
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

				LootPool.Builder tanneryMendingPool = LootPool.lootPool()
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
														registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
														ConstantValue.exactly(1)
												),
												false
										);
									} catch (ReflectiveOperationException exception) {
										throw new RuntimeException("Failed to create Mending enchanted book loot function", exception);
									}
								})
								.build()
						);

				LootPool.Builder tanneryEfficiencyPool = LootPool.lootPool()
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
														registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY),
														ConstantValue.exactly(5)
												),
												false
										);
									} catch (ReflectiveOperationException exception) {
										throw new RuntimeException("Failed to create Efficiency V enchanted book loot function", exception);
									}
								})
								.build()
						);


				LootPool.Builder tannerySilkTouchPool = LootPool.lootPool()
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
														registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH),
														ConstantValue.exactly(1)
												),
												false
										);
									} catch (ReflectiveOperationException exception) {
										throw new RuntimeException("Failed to create Silk Touch enchanted book loot function", exception);
									}
								})
								.build()
						);

				LootPool.Builder tanneryProtectionPool = LootPool.lootPool()
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
														registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PROTECTION),
														ConstantValue.exactly(4)
												),
												false
										);
									} catch (ReflectiveOperationException exception) {
										throw new RuntimeException("Failed to create Protection VI enchanted book loot function", exception);
									}
								})
								.build()
						);
				tableBuilder.pool(tanneryUnbreakingPool.build());
				tableBuilder.pool(tanneryMendingPool.build());
				tableBuilder.pool(tanneryEfficiencyPool.build());
				tableBuilder.pool(tannerySilkTouchPool.build());
				tableBuilder.pool(tanneryProtectionPool.build());
			}
		});

		// Add a trade to Blacksmith level 1 (Novice)
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 1, factories -> {
			factories.add((level, entity, random) -> new MerchantOffer(
					new ItemCost(Items.IRON_SWORD, 1), // Cost A
					new ItemStack(Items.DIAMOND_SWORD, 1), // Resulting item
					500,  // Max uses
					200000,  // Experience given
					0.00f // Price multiplier
			));

			factories.add((level, entity, random) -> new MerchantOffer(
					new ItemCost(Items.DIAMOND, 8),
					new ItemStack(Items.DIAMOND_BLOCK, 1),
					500, // Max uses
					200000, // Experience given
					0.00f // Price multiplier
			)
			);
		}); // <-- closes Weaponsmith Level 1

		TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 2, factories -> {
			factories.add((level, entity, random) -> new MerchantOffer(
					new ItemCost(Items.DIAMOND_SWORD, 2),
					new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1),
					500,
					200000,
					0.00f
			));

			TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 2, factories -> {
				factories.add((level, entity, random) -> new MerchantOffer(
						new ItemCost(Items.DIAMOND_BLOCK, 64),
						new ItemStack(Items.NETHERITE_SCRAP, 1),
						500,
						200000,
						0.00f
				));

			factories.add((level, entity, random) -> new MerchantOffer(
					new ItemCost(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1),
					new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 2),
					500,
					200000,
					0.00f
			));
		}); // <-- closes Weaponsmith Level 2
	} // <-- closes onInitialize()

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

} // <-- closes VillageBuff