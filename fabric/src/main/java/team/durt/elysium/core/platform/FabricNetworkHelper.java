package team.durt.elysium.core.platform;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;
import team.durt.elysium.core.network.AnimationControllerSyncPayload;
import team.durt.elysium.core.platform.services.INetworkHelper;

@ApiStatus.Internal
public class FabricNetworkHelper implements INetworkHelper {
    @Override
    public <T extends LivingEntity> void sendControllerSyncPacket(int entityId, ElysiumAnimationController<T> controller) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        controller.writeToBuffer(buffer);

        PlayerLookup.tracking(controller.getEntity()).forEach(player -> {
            ServerPlayNetworking.send(player, new AnimationControllerSyncPayload(entityId, buffer));
        });
    }
}
