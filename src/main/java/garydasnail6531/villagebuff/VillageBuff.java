package garydasnail6531.villagebuff;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
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


public class VillageBuff implements ModInitializer {
	public static final String MOD_ID = "villagebuff";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	@Override
	public void onInitialize() {

		CommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess, environment) -> {
					BlacksmithTradesCommands.register(dispatcher);
					FarmerTradesCommands.register(dispatcher);
				}
		);

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

		});

		// Make newly generated weaponsmith chests into double chests
		ServerChunkEvents.CHUNK_GENERATE.register((world, chunk) -> {
			makeWeaponsmithChestsDouble(world, chunk);
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
