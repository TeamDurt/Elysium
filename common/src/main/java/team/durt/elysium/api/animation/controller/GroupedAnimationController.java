package team.durt.elysium.api.animation.controller;

import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.group.ElysiumAnimationGroup;

import java.util.Map;

/**
 * Interface for managing and querying animation groups.
 */
public interface GroupedAnimationController<T extends LivingEntity> {
    /**
     * Retrieves all animation groups.
     *
     * @return A map of all animation groups.
     */
    Map<String, ElysiumAnimationGroup<T>> getAnimationGroups();

    /**
     * Retrieves a specific animation group by its name.
     *
     * @param groupName The name of the group to retrieve.
     * @return The {@link ElysiumAnimationGroup} with the given name.
     */
    ElysiumAnimationGroup<T> getAnimationGroup(String groupName);
}
