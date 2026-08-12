package garydasnail6531.villagebuff;

import com.mojang.datafixers.DSL;
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
import garydasnail6531.villagebuff.mixin.ItemPools;
import garydasnail6531.villagebuff.mixin.tanneryPools;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;

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

				ItemPools.init(registries, tableBuilder);

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