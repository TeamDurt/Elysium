package team.durt.elysium.core.network;

import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.durt.elysium.api.animation.entity.AnimatedEntity;

import java.util.Optional;

public class AnimationControllerSyncHandler {
    public static void handle(final AnimationControllerSyncPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
             findEntity(payload, context).ifPresent(entity ->
                     entity.getAnimationController().readFromBuffer(payload.controllerData()));
        });
    }

    private static Optional<AnimatedEntity> findEntity(AnimationControllerSyncPayload payload, IPayloadContext context) {
        Entity entity = context.player().level().getEntity(payload.entityId());
        if (entity instanceof AnimatedEntity animatedEntity) {
            return Optional.of(animatedEntity);
        }

        return Optional.empty();
    }
}
