package team.durt.elysium.api.animation.entity;

import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;

/**
 * Interface for entities that can be animated.
 */
public interface AnimatedEntity {
    /**
     * Retrieves the animation controller for this entity.
     *
     * @param <T> The type of entity this controller is for.
     * @return The {@link ElysiumAnimationController} for this entity.
     */
    <T extends LivingEntity> ElysiumAnimationController<T> getAnimationController();
}
