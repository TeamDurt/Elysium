package team.durt.elysium.core.event;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;
import team.durt.elysium.api.animation.entity.AnimatedEntity;
import team.durt.elysium.core.Constants;
import team.durt.elysium.core.network.AnimationControllerCompatibilityCheckPayload;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class ElysiumNeoForgeGameEvents {
    @SubscribeEvent
    public static void checkEntityAnimationsCompatibility(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) return;
        if (event.getEntity() instanceof AnimatedEntity animated && animated.getAnimationController().getComputedBy().equals(ElysiumAnimationController.ComputedBy.SERVER)) {
            FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
            animated.getAnimationController().writeCompatibilityData(buffer);

            PacketDistributor.sendToServer(new AnimationControllerCompatibilityCheckPayload(event.getEntity().getId(), buffer));
        }
    }
}
