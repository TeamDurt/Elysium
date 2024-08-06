package team.durt.elysium.core.network;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.network.CustomPayloadEvent;
import team.durt.elysium.api.animation.entity.AnimatedEntity;

import java.util.Optional;

public class AnimationControllerCompatibilityCheckHandler {
    public static void handle(final AnimationControllerCompatibilityCheckPayload payload, final CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            findEntity(payload, context).ifPresent(entity -> {
                if (!entity.getAnimationController().checkCompatibility(payload.compatibilityData())) {
                    context.getConnection().disconnect(Component.translatable("elysium.animation.incompatible"));
                }
            });
        });
        context.setPacketHandled(true);
    }

    private static Optional<AnimatedEntity> findEntity(AnimationControllerCompatibilityCheckPayload payload, CustomPayloadEvent.Context context) {
        if (context.getSender() == null) return Optional.empty();
        Entity entity = context.getSender().level().getEntity(payload.entityId());
        return Optional.ofNullable(entity).filter(AnimatedEntity.class::isInstance).map(AnimatedEntity.class::cast);
    }
}
