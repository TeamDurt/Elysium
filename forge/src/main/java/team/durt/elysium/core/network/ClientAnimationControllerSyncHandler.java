package team.durt.elysium.core.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.api.animation.entity.AnimatedEntity;

import java.util.Optional;

@ApiStatus.Internal
public class ClientAnimationControllerSyncHandler {
    public static void handleClient(final AnimationControllerSyncPayload payload, final CustomPayloadEvent.Context context) {
        findEntity(payload, context).ifPresent(entity ->
                entity.getAnimationController().readFromBuffer(payload.controllerData()));
    }

    private static Optional<AnimatedEntity> findEntity(AnimationControllerSyncPayload payload, CustomPayloadEvent.Context context) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return Optional.empty();
        Entity entity = level.getEntity(payload.entityId());
        return Optional.ofNullable(entity).filter(AnimatedEntity.class::isInstance).map(AnimatedEntity.class::cast);
    }
}
