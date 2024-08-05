package team.durt.elysium.core.registry;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.core.network.AnimationControllerCompatibilityCheckPayload;
import team.durt.elysium.core.network.AnimationControllerCompatibilityCheckReceiver;
import team.durt.elysium.core.network.AnimationControllerSyncPayload;
import team.durt.elysium.core.network.AnimationControllerSyncReceiver;

@ApiStatus.Internal
public class ElysiumMessages {
    public static void registerPayloadTypes() {
        PayloadTypeRegistry.playS2C().register(AnimationControllerSyncPayload.TYPE, AnimationControllerSyncPayload.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(AnimationControllerCompatibilityCheckPayload.TYPE, AnimationControllerCompatibilityCheckPayload.STREAM_CODEC);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(AnimationControllerSyncPayload.TYPE, AnimationControllerSyncReceiver::receive);
    }

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(AnimationControllerCompatibilityCheckPayload.TYPE, AnimationControllerCompatibilityCheckReceiver::receive);
    }
}
