package team.durt.elysium.core.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import team.durt.elysium.api.animation.entity.AnimatedEntity;

import java.util.Optional;

public class AnimationControllerCompatibilityCheckReceiver {
    public static void receive(AnimationControllerCompatibilityCheckPayload payload, ServerPlayNetworking.Context context) {
        findEntity(payload, context).ifPresent(entity -> {
            if (!entity.getAnimationController().checkCompatibility(payload.compatibilityData())) {
                context.responseSender().disconnect(Component.translatable("elysium.animation.incompatible"));
            }
        });
    }

    private static Optional<AnimatedEntity> findEntity(AnimationControllerCompatibilityCheckPayload payload, ServerPlayNetworking.Context context) {
        Entity entity = context.player().level().getEntity(payload.entityId());
        return Optional.ofNullable(entity).filter(AnimatedEntity.class::isInstance).map(AnimatedEntity.class::cast);
    }
}
