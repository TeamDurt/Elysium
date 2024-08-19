package team.durt.elysium.core.event;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.ApiStatus;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;
import team.durt.elysium.api.animation.entity.AnimatedEntity;
import team.durt.elysium.core.Constants;
import team.durt.elysium.core.network.AnimationControllerCompatibilityCheckPayload;
import team.durt.elysium.core.registry.ElysiumMessages;

@ApiStatus.Internal
@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ElysiumForgeGameEvents {
    @SubscribeEvent
    public static void checkEntityAnimationsCompatibility(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) return;
        if (event.getEntity() instanceof AnimatedEntity animated && animated.getAnimationController().getComputedBy().equals(ElysiumAnimationController.ComputedBy.SERVER)) {
            FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
            animated.getAnimationController().writeCompatibilityData(buffer);

            ElysiumMessages.sendToServer(new AnimationControllerCompatibilityCheckPayload(event.getEntity().getId(), buffer));
        }
    }
}
