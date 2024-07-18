package team.durt.elysium.impl.animation.group;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.group.ElysiumAnimationGroup;
import team.durt.elysium.api.animation.state.ElysiumAnimationState;
import team.durt.elysium.impl.animation.state.ElysiumAnimationStateImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ElysiumAnimationGroupImpl<T extends LivingEntity> extends ElysiumAnimationGroup<T> {
    private final HashMap<String, AnimationDefinition> animations;
    private final HashMap<String, ElysiumAnimationState> animationStates = new HashMap<>();
    private final List<State<T>> states;
    private final String defaultState;
    private String activeState;

    private ElysiumAnimationGroupImpl(HashMap<String, AnimationDefinition> animations, List<State<T>> states, String defaultState) {
        this.animations = animations;
        this.states = states;
        this.defaultState = defaultState;
        this.activeState = defaultState;
        states.forEach(state -> state.animations().forEach(animation -> animationStates.put(animation, new ElysiumAnimationStateImpl())));
        this.getActiveState().animations().forEach(animation -> playAnimation(animation, false, false));
    }

    @Override
    public List<State<T>> getStates() {
        return states;
    }

    @Override
    public State<T> getState(String stateName) {
        return states.stream().filter(state -> state.name().equals(stateName)).findFirst().orElse(null);
    }

    @Override
    public String getDefaultState() {
        return defaultState;
    }

    @Override
    public State<T> getActiveState() {
        return this.getState(activeState);
    }

    @Override
    public void setActiveState(String stateName) {
        this.activeState = stateName;
    }

    @Override
    public HashMap<String, AnimationDefinition> getAnimations() {
        return animations;
    }

    @Override
    public HashMap<String, ElysiumAnimationState> getAnimationStates() {
        return animationStates;
    }

    @Override
    public boolean isAnimationPlaying(String animationName) {
        return animationStates.get(animationName).isPlaying();
    }

    @Override
    public void playAnimation(String animationName, boolean once, boolean force) {
        // todo add once scheduling
        animationStates.get(animationName).play(force);
    }

    @Override
    public void stopAnimation(String animationName) {
        animationStates.get(animationName).stop();
    }

    @Override
    public void performFirstPossibleTransition(T entity) {
        for (Transition<T> transition : getState (activeState).transitions()) {
            if (transition.condition().test(entity)) {
                this.getActiveState().animations().forEach(this::stopAnimation);
                setActiveState(transition.targetState());
                this.getActiveState().animations().forEach(animation -> playAnimation(animation, false, false));
                transition.animations().forEach(animation -> playAnimation(animation, true, false));
                return;
            }
        }
    }

    @Override
    public void writeToBuffer(FriendlyByteBuf buffer) {
        buffer.writeInt(animationStates.size());
        animationStates.forEach((name, state) -> {
            buffer.writeUtf(name);
            state.writeToBuffer(buffer);
        });
    }

    @Override
    public void readFromBuffer(FriendlyByteBuf buffer) {
        int size = buffer.readInt();
        for (int i = 0; i < size; i++) {
            String name = buffer.readUtf();
            ElysiumAnimationState state = new ElysiumAnimationStateImpl();
            state.readFromBuffer(buffer);
            animationStates.put(name, state);
        }
    }

    /**
     * Builder class for {@link ElysiumAnimationGroupImpl}.
     *
     * @param <T> The type of the entity.
     */
    public static class Builder<T extends LivingEntity> {
        private final HashMap<String, AnimationDefinition> animations = new HashMap<>();
        private final List<State<T>> states = new ArrayList<>();
        private String defaultState;

        public Builder() {}

        /**
         * Define the animation with the given name.
         *
         * @param name The name of the animation.
         * @param animation The animation definition.
         * @return The builder instance.
         */
        public Builder<T> define(String name, AnimationDefinition animation) {
            animations.put(name, animation);
            return this;
        }

        /**
         * Add the given state to the animation group.
         *
         * @param name The name of the state.
         * @param animations The animations to be played in the state.
         * @param transitions The transitions to other states.
         * @return The builder instance.
         */
        public Builder<T> addState(String name, List<String> animations, List<Transition<T>> transitions) {
            states.add(new State<>(name, animations, transitions));
            return this;
        }

        /**
         * Set the default state of the animation group.
         *
         * @param defaultState The name of the default state.
         * @return The builder instance.
         */
        public Builder<T> defaultState(String defaultState) {
            this.defaultState = defaultState;
            return this;
        }

        /**
         * Build the animation group.
         *
         * @return The built animation group.
         */
        public ElysiumAnimationGroupImpl<T> build() {
            return new ElysiumAnimationGroupImpl<>(animations, states, defaultState);
        }
    }
}
