package team.durt.elysium.core.platform;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;
import team.durt.elysium.core.platform.services.INetworkHelper;

@ApiStatus.Internal
public class NeoForgeNetworkHelper implements INetworkHelper {
    @Override
    public <T extends LivingEntity> void sendControllerSyncPacket(int entityId, ElysiumAnimationController<T> controller) {

    }
}