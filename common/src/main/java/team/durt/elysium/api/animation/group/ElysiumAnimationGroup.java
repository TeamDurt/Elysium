package team.durt.elysium.api.animation.group;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.state.ElysiumAnimationState;

import java.util.HashMap;

/**
 * Abstract class for managing {@link LivingEntity} animations and states.
 *
 * @param <T> The entity type.
 */
public abstract class ElysiumAnimationGroup<T extends LivingEntity> implements StateAnimationGroup<T>, SerializableAnimationGroup {
    /**
     * Returns defined animations.
     *
     * @return Map of animation names to definitions.
     */
    public abstract HashMap<String, AnimationDefinition> getAnimations();

    /**
     * Returns states of defined animations.
     *
     * @return Map of animation names to states.
     */
    public abstract HashMap<String, ElysiumAnimationState> getAnimationStates();

    /**
     * Checks if an animation is playing.
     *
     * @param animationName Name of the animation.
     */
    public abstract void isAnimationPlaying(String animationName);

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
     * Performs the first possible animation transition.
     */
    public abstract void performFirstPossibleTransition();
}
