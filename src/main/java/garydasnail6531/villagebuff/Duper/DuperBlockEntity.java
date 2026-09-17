package garydasnail6531.villagebuff.Duper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.block.state.BlockState;

public class DuperBlockEntity extends BaseContainerBlockEntity {
    public static final int PAYMENT_SLOT = 2;
    private static final int CONTAINER_SIZE = 3;
    private static final int PAYMENT_XP = 100;
    private NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);

    public DuperBlockEntity(BlockPos pos, BlockState state) {
        super(DuperBlocks.DUPER_BLOCK_ENTITY, pos, state);
    }

    public boolean canDuplicate(Player player) {
        if (level == null || level.isClientSide() || !hasItemsToDuplicate()) {
            return false;
        }

        return hasPaymentDiamond() && hasEnoughExperience(player);
    }

    public boolean duplicateContents(Player player) {
        if (!canDuplicate(player)) {
            return false;
        }

        consumePayment(player);

        for (int slot = 0; slot < PAYMENT_SLOT; slot++) {
            ItemStack stack = items.get(slot);
            if (!stack.isEmpty()) {
                Containers.dropItemStack(
                        level,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 1.0,
                        worldPosition.getZ() + 0.5,
                        stack.copy()
                );
            }
        }

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.PORTAL,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 1.05,
                    worldPosition.getZ() + 0.5,
                    32,
                    0.35,
                    0.12,
                    0.35,
                    0.08
            );
            serverLevel.sendParticles(
                    ParticleTypes.ENCHANT,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 1.05,
                    worldPosition.getZ() + 0.5,
                    16,
                    0.25,
                    0.08,
                    0.25,
                    0.03
            );
        }

        setChanged();
        return true;
    }

    private boolean hasItemsToDuplicate() {
        for (int slot = 0; slot < PAYMENT_SLOT; slot++) {
            if (!items.get(slot).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    private boolean hasPaymentDiamond() {
        return items.get(PAYMENT_SLOT).is(Items.DIAMOND);
    }

    private boolean hasEnoughExperience(Player player) {
        return player.getAbilities().instabuild || player.totalExperience >= PAYMENT_XP;
    }

    private void consumePayment(Player player) {
        items.get(PAYMENT_SLOT).shrink(1);

        if (!player.getAbilities().instabuild) {
            player.giveExperiencePoints(-PAYMENT_XP);
        }
    }

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.villagebuff.duper");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new DuperMenu(containerId, inventory, this);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, items);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
    }
}
