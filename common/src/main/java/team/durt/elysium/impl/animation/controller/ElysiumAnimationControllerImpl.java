package team.durt.elysium.impl.animation.controller;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;
import team.durt.elysium.api.animation.definition.ElysiumAnimationDefinition;
import team.durt.elysium.api.animation.group.ElysiumAnimationGroup;
import team.durt.elysium.core.platform.Services;
import team.durt.elysium.impl.animation.group.ElysiumAnimationGroupImpl;

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
        if (this.computedBy.equals(ComputedBy.SERVER) && !this.entity.level().isClientSide()) {
            Services.NETWORK.sendControllerSyncPacket(this.entity.getId(), this);
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
            getAnimationGroup(groupName).readFromBuffer(buffer);
        }
    }

    @Override
    public void writeCompatibilityData(FriendlyByteBuf buffer) {
        buffer.writeInt(this.animationGroups.size());
        this.animationGroups.forEach((name, group) -> {
            buffer.writeUtf(name);
            group.writeCompatibilityData(buffer);
        });
    }

    @Override
    public boolean checkCompatibility(FriendlyByteBuf buffer) {
        int expectedSize = buffer.readInt();
        if (expectedSize != this.animationGroups.size()) {
            return false;
        }
        for (int i = 0; i < expectedSize; i++) {
            if (!isGroupCompatible(buffer)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a single animation group is compatible based on the buffer's current position.
     * Reads the group name from the buffer and checks if the group exists and is compatible.
     *
     * @param buffer The buffer containing compatibility data.
     * @return true if the group is compatible, false otherwise.
     */
    private boolean isGroupCompatible(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();
        ElysiumAnimationGroup<T> group = this.animationGroups.get(name);
        return group != null && group.checkCompatibility(buffer);
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
         * @param entity     The entity to be animated.
         * @param computedBy The side on which the animations are computed.
         */
        public Builder(T entity, ComputedBy computedBy) {
            this.entity = entity;
            this.computedBy = computedBy;
        }

        /**
         * Add a walking animation to the controller.
         *
         * @param animation   The animation definition.
         * @param maxSpeed    The maximum speed of the entity.
         * @param scaleFactor The scale factor of the animation.
         * @param predicate   The predicate for the animation.
         * @return The builder instance.
         */
        public Builder<T> walking(ElysiumAnimationDefinition animation, float maxSpeed, float scaleFactor, Predicate<T> predicate) {
            this.walkingAnimations.add(new WalkingAnimation<>(animation, maxSpeed, scaleFactor, predicate));
            return this;
        }

        /**
         * Add the given animation group to the controller.
         *
         * @param name  The name of the group.
         * @param group The animation group.
         * @return The builder instance.
         */
        public Builder<T> addGroup(String name, ElysiumAnimationGroup<T> group) {
            this.animationGroups.put(name, group);
            return this;
        }

        /**
         * Add a group with one animation set that is played when the predicate is true.
         *
         * @param name          The name of the group.
         * @param animations    The animations to be played.
         * @param endAnimations The animations to be played when the predicate turns false.
         * @param predicate     The predicate for the set.
         * @return The builder instance.
         */
        public Builder<T> addSingleton(String name, List<Pair<String, ElysiumAnimationDefinition>> animations, List<Pair<String, ElysiumAnimationDefinition>> endAnimations, Predicate<T> predicate) {
            ElysiumAnimationGroupImpl.Builder<T> builder = new ElysiumAnimationGroupImpl.Builder<>();

            animations.forEach(pair -> builder.define(pair.getFirst(), pair.getSecond()));
            endAnimations.forEach(pair -> builder.define(pair.getFirst(), pair.getSecond()));

            builder.addState("idle", ImmutableList.of(), ImmutableList.of(
                    new ElysiumAnimationGroup.Transition<>("active", predicate, ImmutableList.of())
            ));
            builder.addState(
                    "active",
                    animations.stream().map(Pair::getFirst).collect(ImmutableList.toImmutableList()),
                    ImmutableList.of(
                            new ElysiumAnimationGroup.Transition<>(
                                    "idle",
                                    predicate.negate(),
                                    endAnimations.stream().map(Pair::getFirst).collect(ImmutableList.toImmutableList())
                            )
                    )
            );

            return this.addGroup(name, builder.defaultState("idle").build());
        }

        /**
         * Add a group with one animation set that is played when the predicate is true.
         *
         * @param name       The name of the group.
         * @param animations The animations to be played.
         * @param predicate  The predicate for the set.
         * @return The builder instance.
         */
        public Builder<T> addSingleton(String name, List<Pair<String, ElysiumAnimationDefinition>> animations, Predicate<T> predicate) {
            return this.addSingleton(name, animations, ImmutableList.of(), predicate);
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
