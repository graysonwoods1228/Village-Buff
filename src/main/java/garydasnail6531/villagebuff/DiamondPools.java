//package garydasnail6531.villagebuff;
//
//import net.minecraft.core.HolderGetter;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
//import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
//import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
//import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
//
//public class DiamondPools {
//    public static void init(HolderGetter.Provider registries, LootTable.Builder tableBuilder) {
//        LootPool.Builder swordPool = LootPool.lootPool()
//                .setRolls(UniformGenerator.between(1.0F, 1.0F))
//                .with(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(10)
//                        .apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
//                                        ConstantValue.exactly(3)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SHARPNESS),
//                                        ConstantValue.exactly(5)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT),
//                                        ConstantValue.exactly(2)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK),
//                                        ConstantValue.exactly(2)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
//                                        ConstantValue.exactly(1)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING),
//                                        ConstantValue.exactly(3)
//                                ))
//                        .build());
//
//        LootPool.Builder pickPool = LootPool.lootPool()
//                .setRolls(UniformGenerator.between(1.0F, 1.0F))
//                .with(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(10)
//                        .apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
//                                        ConstantValue.exactly(3)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY),
//                                        ConstantValue.exactly(5)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
//                                        ConstantValue.exactly(1)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE),
//                                        ConstantValue.exactly(3)
//                                ))
//                        .build());
//
//        LootPool.Builder pickPool2 = LootPool.lootPool()
//                .setRolls(UniformGenerator.between(1.0F, 1.0F))
//                .with(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(10)
//                        .apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
//                                        ConstantValue.exactly(3)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY),
//                                        ConstantValue.exactly(5)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
//                                        ConstantValue.exactly(1)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH),
//                                        ConstantValue.exactly(1)
//                                ))
//                        .build());
//
//        LootPool.Builder axePool = LootPool.lootPool()
//                .setRolls(UniformGenerator.between(1.0F, 1.0F))
//                .with(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(10)
//                        .apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING),
//                                        ConstantValue.exactly(3)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY),
//                                        ConstantValue.exactly(5)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
//                                        ConstantValue.exactly(1)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH),
//                                        ConstantValue.exactly(1)
//                                )).apply(new SetEnchantmentsFunction.Builder()
//                                .withEnchantment(
//                                        registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SWEEPING_EDGE),
//                                        ConstantValue.exactly(5)
//                                ))
//                        .build());
//    }
//
//}
//