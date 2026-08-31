package garydasnail6531.villagebuff;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

import java.util.List;

public class FarmerTradesCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("farmertrade")

                        .then(
                                Commands.argument(
                                                "level",
                                                IntegerArgumentType.integer(1, 2)
                                        )

                                        .executes(context -> {

                                            int level =
                                                    IntegerArgumentType.getInteger(
                                                            context,
                                                            "level"
                                                    );

                                            CommandSourceStack source =
                                                    context.getSource();

                                            ServerLevel world =
                                                    source.getLevel();

                                            List<FarmerTrades.TradeData> trades =
                                                    FarmerTrades.getTradesForLevel(level);

                                            if (trades.isEmpty()) {

                                                source.sendFailure(
                                                        Component.literal(
                                                                "There are no trades for level " + level + "."
                                                        )
                                                );

                                                return 0;
                                            }

                                            int villagersChanged = 0;

                                            for (var entity : world.getAllEntities()) {

                                                if (!(entity instanceof Villager villager)) {
                                                    continue;
                                                }

                                                if (!villager.getVillagerData()
                                                        .profession()
                                                        .is(VillagerProfession.FARMER)) {
                                                    continue;
                                                }

                                                MerchantOffers offers =
                                                        villager.getOffers();

                                                for (FarmerTrades.TradeData trade : trades) {

                                                    MerchantOffer newOffer =
                                                            trade.createOffer();

                                                    offers.add(newOffer);
                                                }

                                                villagersChanged++;
                                            }

                                            int tradeCount = trades.size();
                                            int finalVillagersChanged = villagersChanged;

                                            source.sendSuccess(
                                                    () -> Component.literal(
                                                            "Added " +
                                                                    tradeCount +
                                                                    " level " +
                                                                    level +
                                                                    " trade(s) to " +
                                                                    finalVillagersChanged +
                                                                    " Farmer villager(s)."
                                                    ),
                                                    true
                                            );

                                            return villagersChanged;
                                        })
                        )
        );
    }
}
