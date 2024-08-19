package team.durt.elysium.core.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;
import team.durt.elysium.api.animation.entity.AnimatedEntity;
import team.durt.elysium.core.network.AnimationControllerCompatibilityCheckPayload;

@ApiStatus.Internal
public class ElysiumFabricClientEvents {
    public static void register() {
        ClientEntityEvents.ENTITY_LOAD.register(ElysiumFabricClientEvents::checkEntityAnimationsCompatibility);
    }

    private static void checkEntityAnimationsCompatibility(Entity entity, ClientLevel level) {
        if (!level.isClientSide()) return;
        if (entity instanceof AnimatedEntity animated && animated.getAnimationController().getComputedBy().equals(ElysiumAnimationController.ComputedBy.SERVER)) {
            FriendlyByteBuf buffer = PacketByteBufs.create();
            animated.getAnimationController().writeCompatibilityData(buffer);

            ClientPlayNetworking.send(new AnimationControllerCompatibilityCheckPayload(entity.getId(), buffer));
        }
    }
}
