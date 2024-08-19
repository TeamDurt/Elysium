package team.durt.elysium.core.platform.services;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;

@ApiStatus.Internal
public interface INetworkHelper {
    <T extends LivingEntity> void sendControllerSyncPacket(int entityId, ElysiumAnimationController<T> controller);
}
