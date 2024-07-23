package team.durt.elysium.core.platform.services;

import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;

public interface INetworkHelper {
    <T extends LivingEntity> void sendControllerSyncPacket(int entityId, ElysiumAnimationController<T> controller);
}
