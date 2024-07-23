package team.durt.elysium.api.animation.entity;

import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;

public interface AnimatedEntity {
    <T extends LivingEntity> ElysiumAnimationController<T> getAnimationController();
}
