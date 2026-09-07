package garydasnail6531.villagebuff.Duper;

import garydasnail6531.villagebuff.VillageBuff;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class DuperBlocks {
    public static final Identifier DUPER_ID = Identifier.fromNamespaceAndPath(VillageBuff.MOD_ID, "duper");
    private static final ResourceKey<Block> DUPER_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, DUPER_ID);
    private static final ResourceKey<Item> DUPER_ITEM_KEY = ResourceKey.create(Registries.ITEM, DUPER_ID);

    public static final DuperBlock DUPER = new DuperBlock(
            BlockBehaviour.Properties.of()
                    .setId(DUPER_BLOCK_KEY)
                    .mapColor(MapColor.STONE)
                    .strength(3.5f)
                    .sound(SoundType.STONE)
    );

    public static final BlockItem DUPER_ITEM = new BlockItem(
            DUPER,
            new Item.Properties()
                    .setId(DUPER_ITEM_KEY)
                    .useBlockDescriptionPrefix()
    );

    public static final BlockEntityType<DuperBlockEntity> DUPER_BLOCK_ENTITY =
            FabricBlockEntityTypeBuilder.create(DuperBlockEntity::new, DUPER).build();

    public static final MenuType<DuperMenu> DUPER_MENU =
            new MenuType<>(DuperMenu::new, FeatureFlags.VANILLA_SET);

    public static void init() {
        Registry.register(BuiltInRegistries.BLOCK, DUPER_ID, DUPER);
        Registry.register(BuiltInRegistries.ITEM, DUPER_ID, DUPER_ITEM);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, DUPER_ID, DUPER_BLOCK_ENTITY);
        Registry.register(BuiltInRegistries.MENU, DUPER_ID, DUPER_MENU);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(entries -> {
            entries.accept(DUPER_ITEM);
        });
    }
}
