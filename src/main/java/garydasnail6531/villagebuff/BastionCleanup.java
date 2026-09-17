package garydasnail6531.villagebuff;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BastionCleanup {
    private static final double PIGLIN_BRUTE_MAX_HEALTH = 20.0;

    public static void init() {
        ServerEntityEvents.ENTITY_LOAD.register(BastionCleanup::cleanEntity);
    }

    private static void cleanEntity(Entity entity, ServerLevel world) {
        if (!entity.getType().equals(EntityType.PIGLIN_BRUTE)) {
            return;
        }

        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        weakenPiglinBrute(livingEntity);
    }

    private static void weakenPiglinBrute(LivingEntity piglinBrute) {
        AttributeInstance maxHealth =
                piglinBrute.getAttribute(Attributes.MAX_HEALTH);

        if (maxHealth != null) {
            maxHealth.setBaseValue(PIGLIN_BRUTE_MAX_HEALTH);
        }

        piglinBrute.setHealth((float) PIGLIN_BRUTE_MAX_HEALTH);
    }
}
