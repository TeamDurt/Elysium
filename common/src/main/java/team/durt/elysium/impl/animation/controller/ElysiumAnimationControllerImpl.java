package team.durt.elysium.impl.animation.controller;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;
import team.durt.elysium.api.animation.group.ElysiumAnimationGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ElysiumAnimationControllerImpl<T extends LivingEntity> extends ElysiumAnimationController<T> {
    private final T entity;
    private final ComputedBy computedBy;
    private final ArrayList<WalkingAnimation<T>> walkingAnimations;
    private final HashMap<String, ElysiumAnimationGroup<T>> animationGroups;

    private ElysiumAnimationControllerImpl(T entity, ComputedBy computedBy, ArrayList<WalkingAnimation<T>> walkingAnimations, HashMap<String, ElysiumAnimationGroup<T>> animationGroups) {
        this.entity = entity;
        this.computedBy = computedBy;
        this.walkingAnimations = walkingAnimations;
        this.animationGroups = animationGroups;
    }

    @Override
    public void tick() {
        if (this.computedBy.isComputingSide(this.entity.level())) {
            this.animationGroups.values().forEach(group -> group.performFirstPossibleTransition(this.entity));
        }
        if (this.computedBy.equals(ComputedBy.SERVER)) {
            // todo send to client
        }
    }

    @Override
    public T getEntity() {
        return this.entity;
    }

    @Override
    public ComputedBy getComputedBy() {
        return this.computedBy;
    }

    @Override
    public Map<String, ElysiumAnimationGroup<T>> getAnimationGroups() {
        return Map.copyOf(this.animationGroups);
    }

    @Override
    public ElysiumAnimationGroup<T> getAnimationGroup(String groupName) {
        return this.animationGroups.get(groupName);
    }

    @Override
    public List<WalkingAnimation<T>> getWalkingAnimations() {
        return List.copyOf(this.walkingAnimations);
    }

    @Override
    public void writeToBuffer(FriendlyByteBuf buffer) {
        buffer.writeInt(this.animationGroups.size());
        for (Map.Entry<String, ElysiumAnimationGroup<T>> entry : this.animationGroups.entrySet()) {
            buffer.writeUtf(entry.getKey());
            entry.getValue().writeToBuffer(buffer);
        }
    }

    @Override
    public void readFromBuffer(FriendlyByteBuf buffer) {
        int size = buffer.readInt();
        for (int i = 0; i < size; i++) {
            String groupName = buffer.readUtf();
            ElysiumAnimationGroup<T> group = getAnimationGroup(groupName);
            group.readFromBuffer(buffer);
            this.animationGroups.put(groupName, group);
        }
    }

    /**
     * Builder class for {@link ElysiumAnimationControllerImpl}.
     *
     * @param <T> The type of the entity.
     */
    public static class Builder<T extends LivingEntity> {
        private final T entity;
        private final ComputedBy computedBy;
        private final ArrayList<WalkingAnimation<T>> walkingAnimations = new ArrayList<>();
        private final HashMap<String, ElysiumAnimationGroup<T>> animationGroups = new HashMap<>();

        /**
         * Create a new builder instance.
         *
         * @param entity The entity to be animated.
         * @param computedBy The side on which the animations are computed.
         */
        public Builder(T entity, ComputedBy computedBy) {
            this.entity = entity;
            this.computedBy = computedBy;
        }

        /**
         * Add a walking animation to the controller.
         *
         * @param animation The animation definition.
         * @param maxSpeed The maximum speed of the entity.
         * @param scaleFactor The scale factor of the animation.
         * @param predicate The predicate for the animation.
         * @return The builder instance.
         */
        public Builder<T> walking(AnimationDefinition animation, float maxSpeed, float scaleFactor, Predicate<T> predicate) {
            this.walkingAnimations.add(new WalkingAnimation<>(animation, maxSpeed, scaleFactor, predicate));
            return this;
        }

        /**
         * Add the given animation group to the controller.
         *
         * @param name The name of the group.
         * @param group The animation group.
         * @return The builder instance.
         */
        public Builder<T> addGroup(String name, ElysiumAnimationGroup<T> group) {
            this.animationGroups.put(name, group);
            return this;
        }

        /**
         * Build the animation controller.
         *
         * @return The built animation controller.
         */
        public ElysiumAnimationControllerImpl<T> build() {
            return new ElysiumAnimationControllerImpl<>(this.entity, this.computedBy, this.walkingAnimations, this.animationGroups);
        }
    }
}
