package team.durt.elysium.core.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import team.durt.elysium.core.Constants;

@ApiStatus.Internal
public record AnimationControllerSyncPayload(int entityId, FriendlyByteBuf controllerData) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AnimationControllerSyncPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "animation_controller_sync"));
    public static final StreamCodec<FriendlyByteBuf, AnimationControllerSyncPayload> STREAM_CODEC = CustomPacketPayload.codec(
            AnimationControllerSyncPayload::write, AnimationControllerSyncPayload::read
    );

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeBytes(controllerData);
    }

    private static AnimationControllerSyncPayload read(FriendlyByteBuf buf) {
        return new AnimationControllerSyncPayload(buf.readInt(), new FriendlyByteBuf(buf.readBytes(buf.readableBytes())));
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
