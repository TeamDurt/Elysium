package team.durt.elysium.core.platform;

import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;
import team.durt.elysium.core.platform.services.INetworkHelper;

public class FabricNetworkHelper implements INetworkHelper {
    @Override
    public <T extends LivingEntity> void sendControllerSyncPacket(int entityId, ElysiumAnimationController<T> controller) {

    }
}
