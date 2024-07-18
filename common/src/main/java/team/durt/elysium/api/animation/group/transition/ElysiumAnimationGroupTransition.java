package team.durt.elysium.api.animation.group.transition;

import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.function.Predicate;

/**
 * Abstract transition for animation groups targeting {@link LivingEntity}.
 *
 * @param <T> Type of {@link LivingEntity} applicable to the transition.
 */
public abstract class ElysiumAnimationGroupTransition<T extends LivingEntity> implements SerializableAnimationGroupTransition {
    /**
     * Target state of the transition.
     *
     * @return Target state name as a {@link String}.
     */
    public abstract String targetState();

    /**
     * Condition under which the transition occurs.
     *
     * @return {@link Predicate} to test the transition condition.
     */
    public abstract Predicate<T> condition();

    /**
     * Animations involved in the transition.
     *
     * @return List of animation names.
     */
    public abstract List<String> animations();
}
