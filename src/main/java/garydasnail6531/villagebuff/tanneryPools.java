package garydasnail6531.villagebuff;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

public class tanneryPools {
    public static void init(HolderGetter.Provider registries, LootTable.Builder tableBuilder) {
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
    }
