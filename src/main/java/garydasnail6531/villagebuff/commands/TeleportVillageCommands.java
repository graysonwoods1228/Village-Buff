package garydasnail6531.villagebuff.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;

import java.util.HashSet;
import java.util.Set;

public class TeleportVillageCommands {
    private static final int SAMPLE_STEP_BLOCKS = 512;
    private static final int SAMPLE_LOCATE_RADIUS_CHUNKS = 32;
    private static final int MAX_SEARCH_BLOCKS = 8192;
    private static final int START_SCAN_RADIUS_BLOCKS = 160;
    private static final int START_SCAN_STEP_BLOCKS = 16;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("villagebuff")
                        .then(Commands.literal("tpvillage")
                                .then(
                                        Commands.argument(
                                                "blacksmith_houses",
                                                IntegerArgumentType.integer(1)
                                        )

                                                .executes(context -> {
                                                    int requestedBlacksmithHouses =
                                                            IntegerArgumentType.getInteger(
                                                                    context,
                                                                    "blacksmith_houses"
                                                            );

                                                    return teleportToVillage(
                                                            context.getSource(),
                                                            requestedBlacksmithHouses
                                                    );
                                                })
                                )
                        )
        );
    }

    private static int teleportToVillage(
            CommandSourceStack source,
            int requestedBlacksmithHouses
    ) throws com.mojang.brigadier.exceptions.CommandSyntaxException {

        ServerPlayer player = source.getPlayerOrException();
        ServerLevel world = source.getLevel();
        BlockPos origin = player.blockPosition();

        VillageMatch match = findNearestMatchingVillage(
                world,
                origin,
                requestedBlacksmithHouses
        );

        if (match == null) {
            source.sendFailure(
                    Component.literal(
                            "Could not find a village with " +
                                    formatRequestedBlacksmithHouses(requestedBlacksmithHouses) +
                                    " blacksmith house(s) within " +
                                    MAX_SEARCH_BLOCKS +
                                    " blocks."
                    )
            );

            return 0;
        }

        BlockPos target = getSafeTeleportPos(world, match.pos());

        player.teleportTo(
                world,
                target.getX() + 0.5,
                target.getY(),
                target.getZ() + 0.5,
                Set.of(Relative.X_ROT, Relative.Y_ROT),
                player.getYRot(),
                player.getXRot(),
                true
        );

        source.sendSuccess(
                () -> Component.literal(
                        "Teleported to village at " +
                                match.pos().getX() +
                                ", " +
                                match.pos().getY() +
                                ", " +
                                match.pos().getZ() +
                                " with " +
                                match.blacksmithHouses() +
                                " blacksmith house(s)."
                ),
                false
        );

        return 1;
    }

    private static VillageMatch findNearestMatchingVillage(
            ServerLevel world,
            BlockPos origin,
            int requestedBlacksmithHouses
    ) {

        VillageMatch bestMatch = null;
        Set<Long> checkedVillageChunks = new HashSet<>();

        for (
                int radius = 0;
                radius <= MAX_SEARCH_BLOCKS;
                radius += SAMPLE_STEP_BLOCKS
        ) {

            for (int x = -radius; x <= radius; x += SAMPLE_STEP_BLOCKS) {
                bestMatch = checkVillageNear(
                        world,
                        origin,
                        origin.offset(x, 0, -radius),
                        requestedBlacksmithHouses,
                        checkedVillageChunks,
                        bestMatch
                );

                if (radius > 0) {
                    bestMatch = checkVillageNear(
                            world,
                            origin,
                            origin.offset(x, 0, radius),
                            requestedBlacksmithHouses,
                            checkedVillageChunks,
                            bestMatch
                    );
                }
            }

            for (
                    int z = -radius + SAMPLE_STEP_BLOCKS;
                    z <= radius - SAMPLE_STEP_BLOCKS;
                    z += SAMPLE_STEP_BLOCKS
            ) {
                bestMatch = checkVillageNear(
                        world,
                        origin,
                        origin.offset(-radius, 0, z),
                        requestedBlacksmithHouses,
                        checkedVillageChunks,
                        bestMatch
                );

                if (radius > 0) {
                    bestMatch = checkVillageNear(
                            world,
                            origin,
                            origin.offset(radius, 0, z),
                            requestedBlacksmithHouses,
                            checkedVillageChunks,
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

    private static VillageMatch checkVillageNear(
            ServerLevel world,
            BlockPos origin,
            BlockPos searchCenter,
            int requestedBlacksmithHouses,
            Set<Long> checkedVillageChunks,
            VillageMatch bestMatch
    ) {

        BlockPos villagePos = world.findNearestMapStructure(
                StructureTags.VILLAGE,
                searchCenter,
                SAMPLE_LOCATE_RADIUS_CHUNKS,
                false
        );

        if (villagePos == null) {
            return bestMatch;
        }

        ChunkPos villageChunk = new ChunkPos(villagePos);
        if (!checkedVillageChunks.add(villageChunk.toLong())) {
            return bestMatch;
        }

        StructureStart village = findVillageStartNear(world, villagePos);
        if (village == null || !village.isValid()) {
            return bestMatch;
        }

        int blacksmithHouses = countBlacksmithHouses(village);
        if (!matchesRequestedBlacksmithHouses(blacksmithHouses, requestedBlacksmithHouses)) {
            return bestMatch;
        }

        int distanceBlocks = (int) Math.sqrt(origin.distSqr(villagePos));
        if (bestMatch == null || distanceBlocks < bestMatch.distanceBlocks()) {
            return new VillageMatch(villagePos, blacksmithHouses, distanceBlocks);
        }

        return bestMatch;
    }

    private static StructureStart findVillageStartNear(ServerLevel world, BlockPos villagePos) {
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
                BlockPos scanPos = villagePos.offset(x, 0, z);
                world.getChunkAt(scanPos);

                StructureStart start =
                        world.structureManager().getStructureWithPieceAt(
                                scanPos,
                                StructureTags.VILLAGE
                        );

                if (start != null && start.isValid()) {
                    return start;
                }
            }
        }

        return null;
    }

    private static int countBlacksmithHouses(StructureStart village) {
        int blacksmithHouses = 0;

        for (var piece : village.getPieces()) {
            if (!(piece instanceof PoolElementStructurePiece poolPiece)) {
                continue;
            }

            String elementName = poolPiece.getElement().toString().toLowerCase();
            if (elementName.contains("weaponsmith")) {
                blacksmithHouses++;
            }
        }

        return blacksmithHouses;
    }

    private static boolean matchesRequestedBlacksmithHouses(
            int actualBlacksmithHouses,
            int requestedBlacksmithHouses
    ) {

        if (requestedBlacksmithHouses >= 3) {
            return actualBlacksmithHouses >= 3;
        }

        return actualBlacksmithHouses == requestedBlacksmithHouses;
    }

    private static String formatRequestedBlacksmithHouses(int requestedBlacksmithHouses) {
        if (requestedBlacksmithHouses >= 3) {
            return "3 or more";
        }

        return Integer.toString(requestedBlacksmithHouses);
    }

    private static BlockPos getSafeTeleportPos(ServerLevel world, BlockPos villagePos) {
        int y = world.getHeight(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                villagePos.getX(),
                villagePos.getZ()
        );

        return new BlockPos(villagePos.getX(), y, villagePos.getZ());
    }

    private record VillageMatch(BlockPos pos, int blacksmithHouses, int distanceBlocks) {
    }
}
