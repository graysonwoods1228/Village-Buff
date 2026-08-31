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

public class BlacksmithTradesCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("blacksmithtrade")

                        .then(
                                Commands.argument(
                                                "level",
                                                IntegerArgumentType.integer(1, 3)
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


                                            /*
                                             * Get the trades directly from
                                             * BlacksmithTrades.java
                                             */
                                            List<BlacksmithTrades.TradeData> trades =
                                                    BlacksmithTrades.getTradesForLevel(level);


                                            if (trades.isEmpty()) {

                                                source.sendFailure(
                                                        Component.literal(
                                                                "There are no trades for level " + level + "."
                                                        )
                                                );

                                                return 0;
                                            }


                                            int villagersChanged = 0;


                                            /*
                                             * Find every Weaponsmith villager
                                             * in the current dimension.
                                             */
                                            for (var entity : world.getAllEntities()) {

                                                if (!(entity instanceof Villager villager)) {
                                                    continue;
                                                }

                                                if (!villager.getVillagerData()
                                                        .profession()
                                                        .is(VillagerProfession.WEAPONSMITH)) {
                                                    continue;
                                                }


                                                /*
                                                 * Get the villager's current offers.
                                                 */
                                                MerchantOffers offers =
                                                        villager.getOffers();


                                                /*
                                                 * Add every trade from the
                                                 * selected level.
                                                 */
                                                for (
                                                        BlacksmithTrades.TradeData trade :
                                                        trades
                                                ) {

                                                    MerchantOffer newOffer =
                                                            trade.createOffer();

                                                    offers.add(newOffer);
                                                }


                                                villagersChanged++;
                                            }


                                            /*
                                             * Tell the player how many villagers
                                             * were changed.
                                             */
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
                                                                    " Weaponsmith villager(s)."
                                                    ),
                                                    true
                                            );


                                            return villagersChanged;
                                        })
                        )
        );
    }
}
