package team.durt.elysium.api.animation.group;

import net.minecraft.world.entity.LivingEntity;

import java.util.List;

/**
 * Interface for managing and querying animation states of {@link LivingEntity} entities.
 *
 * @param <T> Entity type extending {@link LivingEntity}.
 */
public interface StateAnimationGroup<T extends LivingEntity> {
    /**
     * Retrieves a list of all possible animation states for the entity.
     *
     * @return A list of {@link ElysiumAnimationGroup.State} representing all possible states.
     */
    List<ElysiumAnimationGroup.State<T>> getStates();

    /**
     * Retrieves a specific animation state by its name.
     *
     * @param stateName The name of the state to retrieve.
     * @return The {@link ElysiumAnimationGroup.State} with the given name.
     */
    ElysiumAnimationGroup.State<T> getState(String stateName);

    /**
     * Returns the name of the default animation state.
     *
     * @return A {@link String} representing the name of the default state.
     */
    String getDefaultState();

    /**
     * Retrieves the currently active animation state.
     *
     * @return The active {@link ElysiumAnimationGroup.State}.
     */
    ElysiumAnimationGroup.State<T> getActiveState();

    /**
     * Sets the active animation state by its name.
     *
     * @param stateName The name of the state to set as active.
     */
    void setActiveState(String stateName);
}
