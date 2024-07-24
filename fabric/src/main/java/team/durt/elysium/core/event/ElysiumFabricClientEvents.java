package team.durt.elysium.core.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import team.durt.elysium.api.animation.entity.AnimatedEntity;

public class ElysiumFabricClientEvents {
    public static void register() {
        ClientEntityEvents.ENTITY_LOAD.register(ElysiumFabricClientEvents::checkEntityAnimationsCompatibility);
    }

    private static void checkEntityAnimationsCompatibility(Entity entity, ClientLevel level) {
        if (!level.isClientSide()) return;
        if (entity instanceof AnimatedEntity animated) {
//            animated.getAnimationController().getComputedBy().equals(ElysiumAnimationController.ComputedBy.SERVER)
        }
    }
}
