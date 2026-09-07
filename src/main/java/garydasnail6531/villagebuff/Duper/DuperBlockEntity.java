package garydasnail6531.villagebuff.Duper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.block.state.BlockState;

public class DuperBlockEntity extends BaseContainerBlockEntity {
    private static final int CONTAINER_SIZE = 2;
    private static final int AUTO_DUPE_INTERVAL_TICKS = 20;
    private NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    private boolean autoDupe;
    private int autoDupeCooldown;
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return index == 0 && autoDupe ? 1 : 0;
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                setAutoDupe(value != 0);
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public DuperBlockEntity(BlockPos pos, BlockState state) {
        super(DuperBlocks.DUPER_BLOCK_ENTITY, pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, DuperBlockEntity blockEntity) {
        if (!blockEntity.autoDupe) {
            blockEntity.autoDupeCooldown = 0;
            return;
        }

        blockEntity.autoDupeCooldown++;
        if (blockEntity.autoDupeCooldown >= AUTO_DUPE_INTERVAL_TICKS) {
            blockEntity.autoDupeCooldown = 0;
            blockEntity.duplicateContents();
        }
    }

    public ContainerData getData() {
        return data;
    }

    public boolean isAutoDupe() {
        return autoDupe;
    }

    public void setAutoDupe(boolean autoDupe) {
        this.autoDupe = autoDupe;
        setChanged();
    }

    public void toggleAutoDupe() {
        setAutoDupe(!autoDupe);
    }

    public void duplicateContents() {
        if (level == null || level.isClientSide()) {
            return;
        }

        boolean duplicatedAny = false;

        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                Containers.dropItemStack(
                        level,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 1.0,
                        worldPosition.getZ() + 0.5,
                        stack.copy()
                );
                duplicatedAny = true;
            }
        }

        if (duplicatedAny && level instanceof ServerLevel serverLevel) {
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
        autoDupe = input.getBooleanOr("AutoDupe", false);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
        output.putBoolean("AutoDupe", autoDupe);
    }
}
