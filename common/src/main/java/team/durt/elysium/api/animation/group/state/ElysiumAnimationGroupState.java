package team.durt.elysium.api.animation.group.state;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.group.transition.ElysiumAnimationGroupTransition;

import java.util.List;

/**
 * Abstract state within an animation group for {@link LivingEntity}.
 *
 * @param <T> Type of {@link LivingEntity} applicable.
 */
public abstract class ElysiumAnimationGroupState<T extends LivingEntity> implements SerializableAnimationGroupState {
    /**
     * State name.
     *
     * @return State name.
     */
    public abstract String name();

    /**
     * Animations playing during this state.
     *
     * @return List of animation names.
     */
    public abstract List<String> animations();

    /**
     * Possible transitions to other states.
     *
     * @return List of transitions.
     */
    public abstract List<ElysiumAnimationGroupTransition<T>> transitions();
}
