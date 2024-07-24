package team.durt.elysium.core.platform;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;
import team.durt.elysium.core.network.AnimationControllerSyncPayload;
import team.durt.elysium.core.platform.services.INetworkHelper;

@ApiStatus.Internal
public class NeoForgeNetworkHelper implements INetworkHelper {
    @Override
    public <T extends LivingEntity> void sendControllerSyncPacket(int entityId, ElysiumAnimationController<T> controller) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        controller.writeToBuffer(buffer);

        PacketDistributor.sendToPlayersTrackingEntity(controller.getEntity(), new AnimationControllerSyncPayload(entityId, buffer));
    }
}