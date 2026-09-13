package garydasnail6531.villagebuff.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

import java.util.HashSet;
import java.util.Set;

public class TeleportBastionCommands {
    private static final TagKey<Structure> BASTION_REMNANT =
            TagKey.create(
                    Registries.STRUCTURE,
                    Identifier.fromNamespaceAndPath("villagebuff", "bastion_remnant")
            );

    private static final int SAMPLE_STEP_BLOCKS = 512;
    private static final int SAMPLE_LOCATE_RADIUS_CHUNKS = 32;
    private static final int MAX_SEARCH_BLOCKS = 16384;
    private static final int START_SCAN_RADIUS_BLOCKS = 192;
    private static final int START_SCAN_STEP_BLOCKS = 16;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("function")
                        .then(Commands.literal("villagebuff")
                                .then(Commands.literal("tpbastion")
                                        .executes(context -> teleportToTreasureBastion(
                                                context.getSource()
                                        ))
                                )
                        )
        );
    }

    private static int teleportToTreasureBastion(
            CommandSourceStack source
    ) throws com.mojang.brigadier.exceptions.CommandSyntaxException {

        ServerPlayer player = source.getPlayerOrException();
        ServerLevel world = source.getServer().getLevel(Level.NETHER);

        if (world == null) {
            source.sendFailure(
                    Component.literal("Could not find the Nether dimension.")
            );

            return 0;
        }

        BlockPos origin = player.blockPosition();

        BastionMatch match = findNearestTreasureBastion(world, origin);

        if (match == null) {
            source.sendFailure(
                    Component.literal(
                            "Could not find a treasure room bastion within " +
                                    MAX_SEARCH_BLOCKS +
                                    " blocks."
                    )
            );

            return 0;
        }

        BlockPos target = match.treasureRoomPos();

        player.teleportTo(
                world,
                target.getX() + 0.5,
                target.getY(),
                target.getZ() + 0.5,
                Set.of(),
                player.getYRot(),
                player.getXRot(),
                true
        );

        source.sendSuccess(
                () -> Component.literal(
                        "Teleported to treasure room bastion at " +
                                target.getX() +
                                ", " +
                                target.getY() +
                                ", " +
                                target.getZ() +
                                "."
                ),
                true
        );

        return 1;
    }

    private static BastionMatch findNearestTreasureBastion(
            ServerLevel world,
            BlockPos origin
    ) {

        BastionMatch bestMatch = null;
        Set<Long> checkedBastionChunks = new HashSet<>();

        for (
                int radius = 0;
                radius <= MAX_SEARCH_BLOCKS;
                radius += SAMPLE_STEP_BLOCKS
        ) {

            for (int x = -radius; x <= radius; x += SAMPLE_STEP_BLOCKS) {
                bestMatch = checkBastionNear(
                        world,
                        origin,
                        origin.offset(x, 0, -radius),
                        checkedBastionChunks,
                        bestMatch
                );

                if (radius > 0) {
                    bestMatch = checkBastionNear(
                            world,
                            origin,
                            origin.offset(x, 0, radius),
                            checkedBastionChunks,
                            bestMatch
                    );
                }
            }

            for (
                    int z = -radius + SAMPLE_STEP_BLOCKS;
                    z <= radius - SAMPLE_STEP_BLOCKS;
                    z += SAMPLE_STEP_BLOCKS
            ) {
                bestMatch = checkBastionNear(
                        world,
                        origin,
                        origin.offset(-radius, 0, z),
                        checkedBastionChunks,
                        bestMatch
                );

                if (radius > 0) {
                    bestMatch = checkBastionNear(
                            world,
                            origin,
                            origin.offset(radius, 0, z),
                            checkedBastionChunks,
                            bestMatch
                    );
                }
            }

            if (bestMatch != null && radius > bestMatch.distanceBlocks() + SAMPLE_STEP_BLOCKS) {
                return bestMatch;
            }
        }

        return bestMatch;
    }

    private static BastionMatch checkBastionNear(
            ServerLevel world,
            BlockPos origin,
            BlockPos searchCenter,
            Set<Long> checkedBastionChunks,
            BastionMatch bestMatch
    ) {

        BlockPos bastionPos = world.findNearestMapStructure(
                BASTION_REMNANT,
                searchCenter,
                SAMPLE_LOCATE_RADIUS_CHUNKS,
                false
        );

        if (bastionPos == null) {
            return bestMatch;
        }

        ChunkPos bastionChunk = new ChunkPos(bastionPos);
        if (!checkedBastionChunks.add(bastionChunk.toLong())) {
            return bestMatch;
        }

        StructureStart bastion = findBastionStartNear(world, bastionPos);
        if (bastion == null || !bastion.isValid()) {
            return bestMatch;
        }

        BlockPos treasureRoomPos = findTreasureRoomPos(bastion);
        if (treasureRoomPos == null) {
            return bestMatch;
        }

        int distanceBlocks = (int) Math.sqrt(origin.distSqr(bastionPos));
        if (bestMatch == null || distanceBlocks < bestMatch.distanceBlocks()) {
            return new BastionMatch(treasureRoomPos, distanceBlocks);
        }

        return bestMatch;
    }

    private static StructureStart findBastionStartNear(ServerLevel world, BlockPos bastionPos) {
        for (
                int x = -START_SCAN_RADIUS_BLOCKS;
                x <= START_SCAN_RADIUS_BLOCKS;
                x += START_SCAN_STEP_BLOCKS
        ) {
            for (
                    int z = -START_SCAN_RADIUS_BLOCKS;
                    z <= START_SCAN_RADIUS_BLOCKS;
                    z += START_SCAN_STEP_BLOCKS
            ) {
                BlockPos scanPos = bastionPos.offset(x, 0, z);
                world.getChunkAt(scanPos);

                StructureStart start =
                        world.structureManager().getStructureWithPieceAt(
                                scanPos,
                                BASTION_REMNANT
                        );

                if (start != null && start.isValid()) {
                    return start;
                }
            }
        }

        return null;
    }

    private static BlockPos findTreasureRoomPos(StructureStart bastion) {
        PoolElementStructurePiece fallbackTreasurePiece = null;

        for (var piece : bastion.getPieces()) {
            if (!(piece instanceof PoolElementStructurePiece poolPiece)) {
                continue;
            }

            String elementName = poolPiece.getElement().toString().toLowerCase();
            if (elementName.contains("bastion/treasure/brains/center_brain")) {
                return centerOf(poolPiece.getBoundingBox());
            }

            if (fallbackTreasurePiece == null && elementName.contains("bastion/treasure")) {
                fallbackTreasurePiece = poolPiece;
            }
        }

        if (fallbackTreasurePiece == null) {
            return null;
        }

        return centerOf(fallbackTreasurePiece.getBoundingBox());
    }

    private static BlockPos centerOf(BoundingBox box) {
        return new BlockPos(
                (box.minX() + box.maxX()) / 2,
                (box.minY() + box.maxY()) / 2,
                (box.minZ() + box.maxZ()) / 2
        );
    }

    private record BastionMatch(BlockPos treasureRoomPos, int distanceBlocks) {
    }
}
