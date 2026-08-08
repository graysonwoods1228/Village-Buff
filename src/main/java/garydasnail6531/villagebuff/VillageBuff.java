package garydasnail6531.villagebuff;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.chunk.LevelChunk;

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
				LootPool.Builder swordPool = LootPool.lootPool()
						.setRolls(UniformGenerator.between(1.0F, 1.0F))
						.with(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(10)
								 .apply(new SetEnchantmentsFunction.Builder()
								.withEnchantment(
										registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
										ConstantValue.exactly(3)
								)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SHARPNESS),
												ConstantValue.exactly(5)
								)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT),
												ConstantValue.exactly(2)
								)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK),
												ConstantValue.exactly(2)
								)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
												ConstantValue.exactly(1)
								)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING),
												ConstantValue.exactly(3)
								))
								.build());

				LootPool.Builder pickPool = LootPool.lootPool()
						.setRolls(UniformGenerator.between(1.0F, 1.0F))
						.with(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(10)
								.apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
												ConstantValue.exactly(3)
										)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY),
												ConstantValue.exactly(5)
										)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
												ConstantValue.exactly(1)
										)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE),
												ConstantValue.exactly(3)
										))
								.build());

				LootPool.Builder pickPool2 = LootPool.lootPool()
						.setRolls(UniformGenerator.between(1.0F, 1.0F))
						.with(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(10)
								.apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
												ConstantValue.exactly(3)
										)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY),
												ConstantValue.exactly(5)
										)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
												ConstantValue.exactly(1)
										)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH),
												ConstantValue.exactly(1)
										))
								.build());

				LootPool.Builder axePool = LootPool.lootPool()
						.setRolls(UniformGenerator.between(1.0F, 1.0F))
						.with(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(10)
								.apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
												ConstantValue.exactly(3)
										)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY),
												ConstantValue.exactly(5)
										)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
												ConstantValue.exactly(1)
										)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH),
												ConstantValue.exactly(1)
										)).apply(new SetEnchantmentsFunction.Builder()
										.withEnchantment(
												registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SWEEPING_EDGE),
												ConstantValue.exactly(5)
										))
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


				// Inject all distinct pools into the chest layout
				tableBuilder.pool(diamondPool.build());
				tableBuilder.pool(obsidianPool.build());
				tableBuilder.pool(swordPool.build());
				tableBuilder.pool(ironPool.build());
				tableBuilder.pool(pickPool.build());
				tableBuilder.pool(pickPool2.build());
				 tableBuilder.pool(foodPool.build());
				 tableBuilder.pool(axePool.build());

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

		// Make newly generated weaponsmith chests into double chests
		ServerChunkEvents.CHUNK_GENERATE.register((world, chunk) -> {
			makeWeaponsmithChestsDouble(world, chunk);
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

		TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
			factories.add((level, entity, random) -> new MerchantOffer(
					new ItemCost(Items.GOLDEN_APPLE, 128),
					new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1),
					500,
					200000,
					0.00f
			));

			factories.add((level, entity, random) -> new MerchantOffer(
					new ItemCost(Items.DIRT, 1),
					new ItemStack(Items.ELYTRA, 1),
					500,
					200000,
					0.00f
			));
		});

		} // <-- closes onInitialize()

	private static void makeWeaponsmithChestsDouble(ServerLevel world, LevelChunk chunk) {

		for (BlockEntity blockEntity : chunk.getBlockEntities().values()) {

			if (!(blockEntity instanceof ChestBlockEntity chest)) {
				continue;
			}

			BlockPos originalPos = chest.getBlockPos();
			BlockState originalState = world.getBlockState(originalPos);

			// Make sure this is a single normal chest.
			if (!originalState.is(Blocks.CHEST)) {
				continue;
			}

			if (originalState.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
				continue;
			}

			// Only target the vanilla weaponsmith loot table.
			if (!BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(chest.getLootTable())) {
				continue;
			}

			Direction facing = originalState.getValue(ChestBlock.FACING);

			// Try the two possible sides of the chest.
			Direction firstSide = facing.getClockWise();
			BlockPos secondPos = originalPos.relative(firstSide);

			if (!world.getBlockState(secondPos).isAir()) {
				firstSide = facing.getCounterClockWise();
				secondPos = originalPos.relative(firstSide);
			}

			// Don't replace anything important.
			if (!world.getBlockState(secondPos).isAir()) {
				continue;
			}

			boolean secondHalfIsRight = firstSide == facing.getClockWise();

			ChestType originalType = secondHalfIsRight
					? ChestType.LEFT
					: ChestType.RIGHT;

			ChestType secondType = secondHalfIsRight
					? ChestType.RIGHT
					: ChestType.LEFT;

			// Keep the original chest's existing loot table.
			var lootTable = chest.getLootTable();
			long lootSeed = chest.getLootTableSeed();

			// Change the original chest into one half.
			BlockState newOriginalState = originalState
					.setValue(ChestBlock.TYPE, originalType);

			world.setBlock(originalPos, newOriginalState, 3);

			// Place the second half.
			BlockState secondState = Blocks.CHEST.defaultBlockState()
					.setValue(ChestBlock.FACING, facing)
					.setValue(ChestBlock.TYPE, secondType);

			world.setBlock(secondPos, secondState, 3);

			// Get the newly created second chest.
			BlockEntity secondBlockEntity = world.getBlockEntity(secondPos);

			if (secondBlockEntity instanceof ChestBlockEntity secondChest) {

				// IMPORTANT:
				// The second half does NOT get its own loot table.
				secondChest.setLootTable(null);

				// Restore the original loot table to the original half.
				ChestBlockEntity originalChest =
						(ChestBlockEntity) world.getBlockEntity(originalPos);

				if (originalChest != null) {
					originalChest.setLootTable(lootTable);

					if (lootSeed != 0L) {
						originalChest.setLootTableSeed(lootSeed);
					}
				}
			}

			// Only do this once for this chest.
			return;
		}
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

} // <-- closes VillageBuff