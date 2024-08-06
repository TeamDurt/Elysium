package team.durt.elysium.core.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
import team.durt.elysium.core.Constants;
import team.durt.elysium.core.network.AnimationControllerCompatibilityCheckHandler;
import team.durt.elysium.core.network.AnimationControllerCompatibilityCheckPayload;
import team.durt.elysium.core.network.AnimationControllerSyncHandler;
import team.durt.elysium.core.network.AnimationControllerSyncPayload;

public class ElysiumMessages {
    private static final int PROTOCOL_VERSION = 1;
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE = ChannelBuilder
                .named(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "main"))
                .networkProtocolVersion(PROTOCOL_VERSION)
                .acceptedVersions((status, version) -> true)
                .simpleChannel();

        INSTANCE.messageBuilder(AnimationControllerCompatibilityCheckPayload.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .codec(AnimationControllerCompatibilityCheckPayload.STREAM_CODEC)
                .consumerMainThread(AnimationControllerCompatibilityCheckHandler::handle)
                .add();

        INSTANCE.messageBuilder(AnimationControllerSyncPayload.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .codec(AnimationControllerSyncPayload.STREAM_CODEC)
                .consumerMainThread(AnimationControllerSyncHandler::handle)
                .add();
    }

    public static <T> void sendToServer(T message) {
        INSTANCE.send(message, PacketDistributor.SERVER.noArg());
    }

    public static <T> void sendToPlayersTrackingEntity(Entity entity, T message) {
        INSTANCE.send(message, PacketDistributor.TRACKING_ENTITY.with(entity));
    }
}
