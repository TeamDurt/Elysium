package team.durt.elysium.api.animation.entity;

import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;

/**
 * Interface for entities that can be animated.
 *
 * @param <T> The type of entity this controller is for.
 */
public interface AnimatedEntity<T extends LivingEntity> {
    /**
     * Retrieves the animation controller for this entity.
     *
     * @return The {@link ElysiumAnimationController} for this entity.
     */
    ElysiumAnimationController<T> getAnimationController();
}
