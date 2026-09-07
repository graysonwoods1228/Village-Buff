package garydasnail6531.villagebuff.mixin;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class ShulkerBoxNestingMixin {

    @Inject(method = "canFitInsideContainerItems", at = @At("HEAD"), cancellable = true)
    private void villagebuff$allowShulkerBoxesInsideShulkerBoxes(CallbackInfoReturnable<Boolean> cir) {
        BlockItem item = (BlockItem) (Object) this;

        if (item.getBlock() instanceof ShulkerBoxBlock) {
            cir.setReturnValue(true);
        }
    }
}
