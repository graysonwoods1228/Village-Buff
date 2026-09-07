package garydasnail6531.villagebuff;

import garydasnail6531.villagebuff.Duper.DuperBlocks;
import garydasnail6531.villagebuff.commands.BlacksmithTradesCommands;
import garydasnail6531.villagebuff.commands.ClericTradesCommands;
import garydasnail6531.villagebuff.commands.FarmerTradesCommands;
import garydasnail6531.villagebuff.commands.FletcherTradesCommands;
import garydasnail6531.villagebuff.pools.BastionTreasurePools;
import garydasnail6531.villagebuff.pools.BlacksmithPools;
import garydasnail6531.villagebuff.pools.tanneryPools;
import garydasnail6531.villagebuff.trades.FletcherTrades;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VillageBuff implements ModInitializer {
	public static final String MOD_ID = "villagebuff";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	@Override
	public void onInitialize() {

		DuperBlocks.init();

		CommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess, environment) -> {
					BlacksmithTradesCommands.register(dispatcher);
					FarmerTradesCommands.register(dispatcher);
					ClericTradesCommands.register(dispatcher);
					FletcherTradesCommands.register(dispatcher);
				}
		);

		FletcherTrades.init();

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

			// 1. Advanced Weaponsmith chest overhaul
			if (BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(key)) {

				BlacksmithPools.init(registries, tableBuilder);

			}

			// 2. Modifying regular Plains Village houses (Keeping your previous guaranteed setup)
			// Tannery chest loot
			if (BuiltInLootTables.VILLAGE_TANNERY.equals(key)) {

				tanneryPools.init(registries, tableBuilder);
			}

			if (BuiltInLootTables.BASTION_TREASURE.equals(key)) {

				BastionTreasurePools.init(registries, tableBuilder);
			}

		});

//		 Make newly generated weaponsmith chests into double chests
//		ServerChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
//			makeWeaponsmithChestsDouble(world, chunk);
//		});
//
//		} // <-- closes onInitialize()
//
//	private static void makeWeaponsmithChestsDouble(ServerLevel world, LevelChunk chunk) {
//
//		for (BlockEntity blockEntity : new ArrayList<>(chunk.getBlockEntities().values())) {
//
//			if (!(blockEntity instanceof ChestBlockEntity chest)) {
//				continue;
//			}
//
//			BlockPos originalPos = chest.getBlockPos();
//			BlockState originalState = world.getBlockState(originalPos);
//
//			// Make sure this is a single normal chest.
//			if (!originalState.is(Blocks.CHEST)) {
//				continue;
//			}
//
//			if (originalState.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
//				continue;
//			}
//
//			// Only target the vanilla weaponsmith loot table.
//			if (!BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(chest.getLootTable())) {
//				continue;
//			}
//
//			Direction facing = originalState.getValue(ChestBlock.FACING);
//
//			// Try the two possible sides of the chest.
//			Direction firstSide = facing.getClockWise();
//			BlockPos secondPos = originalPos.relative(firstSide);
//
//			if (!world.getBlockState(secondPos).isAir()) {
//				firstSide = facing.getCounterClockWise();
//				secondPos = originalPos.relative(firstSide);
//			}
//
//			// Don't replace anything important.
//			if (!world.getBlockState(secondPos).isAir()) {
//				continue;
//			}
//
//			boolean secondHalfIsRight = firstSide == facing.getClockWise();
//
//			ChestType originalType = secondHalfIsRight
//					? ChestType.LEFT
//					: ChestType.RIGHT;
//
//			ChestType secondType = secondHalfIsRight
//					? ChestType.RIGHT
//					: ChestType.LEFT;
//
//			// Generate this loot once into the combined double chest inventory.
//			ResourceKey<LootTable> lootTableKey = chest.getLootTable();
//			long lootSeed = chest.getLootTableSeed();
//
//			// Change the original chest into one half.
//			BlockState newOriginalState = originalState
//					.setValue(ChestBlock.TYPE, originalType);
//
//			world.setBlock(originalPos, newOriginalState, 3);
//
//			// Place the second half.
//			BlockState secondState = Blocks.CHEST.defaultBlockState()
//					.setValue(ChestBlock.FACING, facing)
//					.setValue(ChestBlock.TYPE, secondType);
//
//			world.setBlock(secondPos, secondState, 3);
//
//			// Get the newly created second chest.
//			BlockEntity secondBlockEntity = world.getBlockEntity(secondPos);
//
//			if (secondBlockEntity instanceof ChestBlockEntity secondChest) {
//				ChestBlockEntity originalChest =
//						(ChestBlockEntity) world.getBlockEntity(originalPos);
//
//				if (originalChest != null) {
//					fillDoubleChestLoot(world, originalPos, originalChest, secondChest, lootTableKey, lootSeed);
//				}
//			}
//		}
//	}
//
//	private static void fillDoubleChestLoot(
//			ServerLevel world,
//			BlockPos origin,
//			ChestBlockEntity originalChest,
//			ChestBlockEntity secondChest,
//			ResourceKey<LootTable> lootTableKey,
//			long lootSeed
//	) {
//		originalChest.setLootTable(null);
//		originalChest.setLootTableSeed(0L);
//		secondChest.setLootTable(null);
//		secondChest.setLootTableSeed(0L);
//
//		LootTable lootTable = world.getServer().reloadableRegistries().getLootTable(lootTableKey);
//		LootParams lootParams = new LootParams.Builder(world)
//				.withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(origin))
//				.create(LootContextParamSets.CHEST);
//
//		lootTable.fill(new CompoundContainer(originalChest, secondChest), lootParams, lootSeed);
//		originalChest.setChanged();
//		secondChest.setChanged();
//	}
//
//	public static Identifier id(String path) {
//		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

} // <-- closes VillageBuff
