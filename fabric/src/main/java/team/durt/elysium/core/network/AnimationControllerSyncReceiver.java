package team.durt.elysium.core.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.api.animation.entity.AnimatedEntity;

import java.util.Optional;

@ApiStatus.Internal
public class AnimationControllerSyncReceiver {
    public static void receive(AnimationControllerSyncPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            findEntity(payload, context).ifPresent(entity ->
                    entity.getAnimationController().readFromBuffer(payload.controllerData()));
        });
    }

    private static Optional<AnimatedEntity> findEntity(AnimationControllerSyncPayload payload, ClientPlayNetworking.Context context) {
        return Optional.ofNullable(context.client().level)
                .map(level -> level.getEntity(payload.entityId()))
                .filter(AnimatedEntity.class::isInstance)
                .map(AnimatedEntity.class::cast);
    }
}
