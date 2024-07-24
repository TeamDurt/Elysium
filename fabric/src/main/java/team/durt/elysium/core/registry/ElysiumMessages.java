package team.durt.elysium.core.registry;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.core.network.AnimationControllerSyncReceiver;
import team.durt.elysium.core.network.AnimationControllerSyncPayload;

@ApiStatus.Internal
public class ElysiumMessages {
    public static void registerPayloadTypes() {
        PayloadTypeRegistry.playS2C().register(AnimationControllerSyncPayload.TYPE, AnimationControllerSyncPayload.STREAM_CODEC);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(AnimationControllerSyncPayload.TYPE, AnimationControllerSyncReceiver::receive);
    }

    public static void registerC2SPackets() {
    }
}
