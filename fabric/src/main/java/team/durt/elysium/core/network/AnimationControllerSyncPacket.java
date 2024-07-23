package team.durt.elysium.core.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.api.animation.entity.AnimatedEntity;

import java.util.Optional;

@ApiStatus.Internal
public class AnimationControllerSyncPacket {
    public static void receive(AnimationControllerSyncPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            findEntity(payload, context).ifPresent(entity ->
                    entity.getAnimationController().readFromBuffer(payload.controllerData()));
        });
    }

    private static Optional<AnimatedEntity> findEntity(AnimationControllerSyncPayload payload, ClientPlayNetworking.Context context) {
        if (context.client().level == null) return Optional.empty();

        Entity entity = context.client().level.getEntity(payload.entityId());
        if (entity instanceof AnimatedEntity animatedEntity) {
            return Optional.of(animatedEntity);
        }

        return Optional.empty();
    }
}
