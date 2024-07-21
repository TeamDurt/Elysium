package team.durt.elysium.api.animation.controller;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;

/**
 * Abstract class for managing animations of {@link LivingEntity} entity.
 *
 * @param <T> The entity type.
 */
public abstract class ElysiumAnimationController<T extends LivingEntity> implements GroupedAnimationController<T>, SerializableAnimationController {
    /**
     * Steps forward one tick for all animations.
     */
    public abstract void tick();

    /**
     * Retrieves the entity associated with this controller.
     *
     * @return The entity.
     */
    public abstract T getEntity();

    /**
     * Retrieves the side on which the controller is computing.
     *
     * @return The side.
     */
    public abstract ComputedBy getComputedBy();

    /**
     * Retrieves the walking animations.
     *
     * @return The walking animations.
     */
    public abstract List<WalkingAnimation<T>> getWalkingAnimations();

    /**
     * Enum for specifying the side on which the controller is computing.
     */
    public enum ComputedBy {
        CLIENT {
            @Override
            public boolean isComputingSide(Level level) {
                return level.isClientSide();
            }
        },
        SERVER {
            @Override
            public boolean isComputingSide(Level level) {
                return !level.isClientSide();
            }
        };

        public abstract boolean isComputingSide(Level level);
    }

    /**
     * Represents a walking animation.
     *
     * @param <T> The entity type.
     * @param animationDefinition The animation definition.
     * @param maxSpeed The maximum speed at which the animation can play.
     * @param scaleFactor The scale factor of the animation.
     * @param predicate The predicate for the animation.
     */
    public record WalkingAnimation<T extends LivingEntity>(
            AnimationDefinition animationDefinition,
            float maxSpeed,
            float scaleFactor,
            Predicate<T> predicate) {
    }
}
