package team.durt.elysium.core.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import team.durt.elysium.core.Constants;

@ApiStatus.Internal
public record AnimationControllerCompatibilityCheckPayload(int entityId, FriendlyByteBuf compatibilityData) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AnimationControllerCompatibilityCheckPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "animation_controller_compatibility_check"));
    public static final StreamCodec<FriendlyByteBuf, AnimationControllerCompatibilityCheckPayload> STREAM_CODEC = CustomPacketPayload.codec(
            AnimationControllerCompatibilityCheckPayload::write, AnimationControllerCompatibilityCheckPayload::read
    );

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeBytes(compatibilityData);
    }

    private static AnimationControllerCompatibilityCheckPayload read(FriendlyByteBuf buf) {
        return new AnimationControllerCompatibilityCheckPayload(buf.readInt(), new FriendlyByteBuf(buf.readBytes(buf.readableBytes())));
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
