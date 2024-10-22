package team.durt.elysium.api.animation.group;

import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.definition.ElysiumAnimationDefinition;
import team.durt.elysium.api.animation.state.ElysiumAnimationState;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Abstract class for managing {@link LivingEntity} animations and states.
 *
 * @param <T> The entity type.
 */
public abstract class ElysiumAnimationGroup<T extends LivingEntity> implements StateAnimationGroup<T>, SerializableAnimationGroup, CompatibleAnimationGroup {
    /**
     * Returns defined animations.
     *
     * @return Map of animation names to definitions.
     */
    public abstract Map<String, ElysiumAnimationDefinition> getAnimations();

    /**
     * Retrieves an animation definition by name.
     *
     * @param animationName Name of the animation.
     * @return The animation definition.
     */
    public abstract Optional<ElysiumAnimationDefinition> getAnimation(String animationName);

    /**
     * Returns states of defined animations.
     *
     * @return Map of animation names to states.
     */
    public abstract Map<String, ElysiumAnimationState> getAnimationStates();

    /**
     * Checks if an animation is playing.
     *
     * @param animationName Name of the animation.
     */
    public abstract boolean isAnimationPlaying(String animationName);

    /**
     * Plays an animation.
     *
     * @param animationName Name of the animation.
     * @param once If true, the animation will stop after one time iteration.
     * @param force If true, the animation will start from the beginning, even if it is already playing.
     */
    public abstract void playAnimation(String animationName, boolean once, boolean force);

    /**
     * Stops an animation.
     *
     * @param animationName Name of the animation to stop.
     */
    public abstract void stopAnimation(String animationName);

    /**
     * Performs the first possible animation state transition.
     */
    public abstract void performFirstPossibleTransition(T entity);

    /**
     * Defines a transition for animation groups targeting {@link LivingEntity}.
     *
     * @param <T> The entity type.
     * @param targetState The target state name.
     * @param condition The transition condition.
     * @param animations The involved animations.
     */
    public record Transition<T extends LivingEntity>(
            String targetState,
            Predicate<T> condition,
            List<String> animations) {
    }

    /**
     * Represents an abstract state within an animation group for {@link LivingEntity}.
     *
     * @param <T> Type of {@link LivingEntity} applicable.
     * @param name The name of the state.
     * @param animations The animations playing during this state.
     * @param transitions The possible transitions to other states.
     */
    public record State<T extends LivingEntity>(
            String name,
            List<String> animations,
            List<Transition<T>> transitions) {
    }
}
