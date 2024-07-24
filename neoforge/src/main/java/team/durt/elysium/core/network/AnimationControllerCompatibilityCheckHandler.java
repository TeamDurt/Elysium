package team.durt.elysium.core.network;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.durt.elysium.api.animation.entity.AnimatedEntity;

import java.util.Optional;

public class AnimationControllerCompatibilityCheckHandler {
    public static void handle(final AnimationControllerCompatibilityCheckPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
             findEntity(payload, context).ifPresent(entity -> {
                 if (!entity.getAnimationController().checkCompatibility(payload.compatibilityData())) {
                     context.connection().disconnect(Component.translatable("elysium.animation.incompatible"));
                 }
             });
        });
    }

    private static Optional<AnimatedEntity> findEntity(AnimationControllerCompatibilityCheckPayload payload, IPayloadContext context) {
        Entity entity = context.player().level().getEntity(payload.entityId());
        return Optional.ofNullable(entity).filter(AnimatedEntity.class::isInstance).map(AnimatedEntity.class::cast);
    }
}
