package team.durt.elysium.core.network;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class AnimationControllerSyncHandler {
    public static void handle(final AnimationControllerSyncPayload payload, final CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAnimationControllerSyncHandler.handleClient(payload, context));
        });
        context.setPacketHandled(true);
    }
}
