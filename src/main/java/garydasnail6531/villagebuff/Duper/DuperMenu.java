package garydasnail6531.villagebuff.Duper;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DuperMenu extends AbstractContainerMenu {
    public static final int DUPE_BUTTON = 0;
    private static final int ITEM_SLOT_COUNT = 2;
    private static final int DUPER_SLOT_COUNT = 3;
    private static final int PLAYER_INVENTORY_START = DUPER_SLOT_COUNT;
    private static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 27;
    private static final int HOTBAR_START = PLAYER_INVENTORY_END;
    private static final int HOTBAR_END = HOTBAR_START + 9;

    private final Container container;
    private final ContainerLevelAccess access;

    public DuperMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(DUPER_SLOT_COUNT), ContainerLevelAccess.NULL);
    }

    public DuperMenu(int containerId, Inventory playerInventory, Container container) {
        this(
                containerId,
                playerInventory,
                container,
                ContainerLevelAccess.create(playerInventory.player.level(), ((DuperBlockEntity) container).getBlockPos())
        );
    }

    private DuperMenu(int containerId, Inventory playerInventory, Container container, ContainerLevelAccess access) {
        super(DuperBlocks.DUPER_MENU, containerId);
        checkContainerSize(container, DUPER_SLOT_COUNT);

        this.container = container;
        this.access = access;

        container.startOpen(playerInventory.player);

        addSlot(new Slot(container, 0, 44, 44));
        addSlot(new Slot(container, 1, 80, 44));
        addSlot(new Slot(container, DuperBlockEntity.PAYMENT_SLOT, 134, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.DIAMOND);
            }
        });
        addStandardInventorySlots(playerInventory, 8, 84);
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id == DUPE_BUTTON && container instanceof DuperBlockEntity duperBlockEntity) {
            return duperBlockEntity.duplicateContents(player);
        }

        return super.clickMenuButton(player, id);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, DuperBlocks.DUPER);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();

            if (index < DUPER_SLOT_COUNT) {
                if (!moveItemStackTo(stack, PLAYER_INVENTORY_START, HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (stack.is(Items.DIAMOND)) {
                if (!moveItemStackTo(stack, DuperBlockEntity.PAYMENT_SLOT, DuperBlockEntity.PAYMENT_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(stack, 0, DUPER_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return result;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        container.stopOpen(player);
    }
}
