package garydasnail6531.villagebuff;

import garydasnail6531.villagebuff.Duper.DuperBlocks;
import garydasnail6531.villagebuff.commands.BlacksmithTradesCommands;
import garydasnail6531.villagebuff.commands.ClericTradesCommands;
import garydasnail6531.villagebuff.commands.FarmerTradesCommands;
import garydasnail6531.villagebuff.commands.FletcherTradesCommands;
import garydasnail6531.villagebuff.commands.LibrarianTradesCommands;
import garydasnail6531.villagebuff.commands.TeleportBastionCommands;
import garydasnail6531.villagebuff.commands.TeleportVillageCommands;
import garydasnail6531.villagebuff.pools.BastionChestPools;
import garydasnail6531.villagebuff.pools.BastionTreasurePools;
import garydasnail6531.villagebuff.pools.BlacksmithPools;
import garydasnail6531.villagebuff.pools.EndCityTreasurePools;
import garydasnail6531.villagebuff.pools.MineshaftPools;
import garydasnail6531.villagebuff.pools.PiglinBarteringPools;
import garydasnail6531.villagebuff.pools.PillagerOutpostPools;
import garydasnail6531.villagebuff.pools.ShipwreckPools;
import garydasnail6531.villagebuff.pools.SpawnBonusChestPools;
import garydasnail6531.villagebuff.pools.StrongholdPools;
import garydasnail6531.villagebuff.pools.TrialChamberPools;
import garydasnail6531.villagebuff.pools.tanneryPools;
import garydasnail6531.villagebuff.trades.FletcherTrades;
import garydasnail6531.villagebuff.trades.LibrarianTrades;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class VillageBuff implements ModInitializer {
	public static final String MOD_ID = "villagebuff";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final int CHEST_CHUNKS_PER_TICK = 64;
	private final Map<ServerLevel, LinkedHashSet<Long>> pendingChestChunks = new IdentityHashMap<>();

	@Override
	public void onInitialize() {

//		DuperBlocks.init();
//		BastionCleanup.init();

//		CommandRegistrationCallback.EVENT.register(
//				(dispatcher, registryAccess, environment) -> {
//					BlacksmithTradesCommands.register(dispatcher);
//					FarmerTradesCommands.register(dispatcher);
//					ClericTradesCommands.register(dispatcher);
//					FletcherTradesCommands.register(dispatcher);
//					LibrarianTradesCommands.register(dispatcher);
//					TeleportBastionCommands.register(dispatcher);
//					TeleportVillageCommands.register(dispatcher);
//				}
//		);

		FletcherTrades.init();
		LibrarianTrades.init();

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

			// 1. Advanced Weaponsmith chest overhaul
			if (BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(key)) {
				BlacksmithPools.init(registries, tableBuilder);
			}

			if (BuiltInLootTables.VILLAGE_TANNERY.equals(key)) {
				tanneryPools.init(registries, tableBuilder);
			}

			if (BuiltInLootTables.BASTION_TREASURE.equals(key)) {
				BastionTreasurePools.init(registries, tableBuilder);
			}

			if (BuiltInLootTables.END_CITY_TREASURE.equals(key)) {
				EndCityTreasurePools.init(registries, tableBuilder);
			}

			if (BuiltInLootTables.ABANDONED_MINESHAFT.equals(key)) {
				MineshaftPools.init(tableBuilder);
			}

			if (BuiltInLootTables.BASTION_OTHER.equals(key)
					|| BuiltInLootTables.BASTION_BRIDGE.equals(key)
					|| BuiltInLootTables.BASTION_HOGLIN_STABLE.equals(key)) {
				BastionChestPools.init(tableBuilder);
			}

			if (BuiltInLootTables.SHIPWRECK_SUPPLY.equals(key)) {
				ShipwreckPools.supply(tableBuilder);
			}

			if (BuiltInLootTables.SHIPWRECK_TREASURE.equals(key)) {
				ShipwreckPools.treasure(tableBuilder);
			}

			if (BuiltInLootTables.SHIPWRECK_MAP.equals(key)) {
				ShipwreckPools.map(tableBuilder);
			}

			if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD_UNIQUE.equals(key)
					|| BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE.equals(key)) {
				TrialChamberPools.maceBoost(tableBuilder);
			}

			if (BuiltInLootTables.STRONGHOLD_LIBRARY.equals(key)
					|| BuiltInLootTables.STRONGHOLD_CROSSING.equals(key)
					|| BuiltInLootTables.STRONGHOLD_CORRIDOR.equals(key)) {
				StrongholdPools.init(tableBuilder);
			}

			if (BuiltInLootTables.PILLAGER_OUTPOST.equals(key)) {
				PillagerOutpostPools.init(tableBuilder);
			}

			if (BuiltInLootTables.PIGLIN_BARTERING.equals(key)) {
				PiglinBarteringPools.init(tableBuilder);
			}

			if (BuiltInLootTables.SPAWN_BONUS_CHEST.equals(key)) {
				SpawnBonusChestPools.init(tableBuilder);
			}

		});

		// The chunk event may run as part of an asynchronous chunk task. Copy only
		// its coordinates there, then mutate the queue on the main server thread.
		ServerChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
			int chunkX = chunk.getPos().x;
			int chunkZ = chunk.getPos().z;

			world.getServer().execute(() ->
					queueChestChunks(world, new ChunkPos(chunkX, chunkZ))
			);
		});

		ServerTickEvents.END_WORLD_TICK.register(this::processChestChunks);
		ServerLifecycleEvents.SERVER_STOPPED.register(server -> pendingChestChunks.clear());
	}

	private void queueChestChunks(ServerLevel world, ChunkPos loadedPos) {
		LinkedHashSet<Long> pending = pendingChestChunks.computeIfAbsent(
				world,
				ignored -> new LinkedHashSet<>()
		);

		// A newly available neighbor may unblock a chest at a chunk border.
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				pending.add(ChunkPos.asLong(loadedPos.x + dx, loadedPos.z + dz));
			}
		}
	}

	private void processChestChunks(ServerLevel world) {
		LinkedHashSet<Long> pending = pendingChestChunks.get(world);
		if (pending == null) {
			return;
		}

		ArrayList<Long> batch = new ArrayList<>(CHEST_CHUNKS_PER_TICK);
		var iterator = pending.iterator();

		while (iterator.hasNext() && batch.size() < CHEST_CHUNKS_PER_TICK) {
			batch.add(iterator.next());
			iterator.remove();
		}

		if (pending.isEmpty()) {
			pendingChestChunks.remove(world);
		}

		for (long packedPos : batch) {
			ChunkPos pos = new ChunkPos(packedPos);

			// Never force an unloaded chunk to load.
			LevelChunk chunk = world.getChunkSource().getChunkNow(pos.x, pos.z);
			if (chunk != null) {
				makeWeaponsmithChestsDouble(world, chunk);
			}
		}
	}

	private static boolean hasLoadedNeighbors(ServerLevel world, BlockPos pos) {
		// Placement updates adjacent blocks, so include the update area as well.
		for (int x = (pos.getX() - 1) >> 4; x <= (pos.getX() + 1) >> 4; x++) {
			for (int z = (pos.getZ() - 1) >> 4; z <= (pos.getZ() + 1) >> 4; z++) {
				if (world.getChunkSource().getChunkNow(x, z) == null) {
					return false;
				}
			}
		}
		return true;
	}

	private static void makeWeaponsmithChestsDouble(ServerLevel world, LevelChunk chunk) {
		// Include positions whose block entities are still stored as pending NBT.
		for (BlockPos originalPos : new ArrayList<>(chunk.getBlockEntitiesPos())) {
			BlockState originalState = chunk.getBlockState(originalPos);

			if (!originalState.is(Blocks.CHEST)
					|| originalState.getValue(ChestBlock.TYPE) != ChestType.SINGLE
					|| !hasLoadedNeighbors(world, originalPos)) {
				continue;
			}

			BlockEntity blockEntity = chunk.getBlockEntity(originalPos);
			if (!(blockEntity instanceof ChestBlockEntity chest)) {
				continue;
			}

			// Only target unopened vanilla weaponsmith loot chests.
			if (!BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(chest.getLootTable())) {
				continue;
			}

			Direction facing = originalState.getValue(ChestBlock.FACING);

			// Try the two possible sides of the chest.
			Direction firstSide = facing.getClockWise();
			BlockPos secondPos = originalPos.relative(firstSide);

			if (!hasLoadedNeighbors(world, secondPos)
					|| !world.getBlockState(secondPos).isAir()) {
				firstSide = facing.getCounterClockWise();
				secondPos = originalPos.relative(firstSide);
			}

			// Don't replace anything important.
			if (!hasLoadedNeighbors(world, secondPos)
					|| !world.getBlockState(secondPos).isAir()) {
				continue;
			}

			boolean secondHalfIsRight = firstSide == facing.getClockWise();

			ChestType originalType = secondHalfIsRight
					? ChestType.LEFT
					: ChestType.RIGHT;

			ChestType secondType = secondHalfIsRight
					? ChestType.RIGHT
					: ChestType.LEFT;

			// Preserve the loot table and seed before changing either block.
			ResourceKey<LootTable> lootTableKey = chest.getLootTable();
			long lootSeed = chest.getLootTableSeed();

			BlockState newOriginalState = originalState
					.setValue(ChestBlock.TYPE, originalType);
			world.setBlock(originalPos, newOriginalState, 3);

			BlockState secondState = Blocks.CHEST.defaultBlockState()
					.setValue(ChestBlock.FACING, facing)
					.setValue(ChestBlock.TYPE, secondType);
			world.setBlock(secondPos, secondState, 3);

			BlockEntity secondBlockEntity = world.getBlockEntity(secondPos);
			BlockEntity originalBlockEntity = world.getBlockEntity(originalPos);

			if (secondBlockEntity instanceof ChestBlockEntity secondChest
					&& originalBlockEntity instanceof ChestBlockEntity originalChest) {
				fillDoubleChestLoot(
						world,
						originalPos,
						originalChest,
						secondChest,
						lootTableKey,
						lootSeed
				);
			}
		}
	}

	private static void fillDoubleChestLoot(
			ServerLevel world,
			BlockPos origin,
			ChestBlockEntity originalChest,
			ChestBlockEntity secondChest,
			ResourceKey<LootTable> lootTableKey,
			long lootSeed
	) {
		originalChest.setLootTable(null);
		originalChest.setLootTableSeed(0L);
		secondChest.setLootTable(null);
		secondChest.setLootTableSeed(0L);

		LootTable lootTable = world.getServer()
				.reloadableRegistries()
				.getLootTable(lootTableKey);

		LootParams lootParams = new LootParams.Builder(world)
				.withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(origin))
				.create(LootContextParamSets.CHEST);

		lootTable.fill(
				new CompoundContainer(originalChest, secondChest),
				lootParams,
				lootSeed
		);

		originalChest.setChanged();
		secondChest.setChanged();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
