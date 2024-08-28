package team.durt.elysium.api.animation.definition;

import com.google.common.collect.Maps;
import net.minecraft.client.animation.AnimationChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Definition of an animation.
 *
 * @param lengthInSeconds The length of the animation in seconds.
 * @param looping If the animation should loop.
 * @param blendingDuration The duration of blending in seconds.
 * @param boneAnimations Animations mapped to bone names.
 */
public record ElysiumAnimationDefinition(float lengthInSeconds, boolean looping, float blendingDuration, Map<String, List<AnimationChannel>> boneAnimations) {
    /**
     * Builder class for {@link ElysiumAnimationDefinition}.
     */
    public static class Builder {
        private final float length;
        private final Map<String, List<AnimationChannel>> animationByBone = Maps.newHashMap();
        private boolean looping;
        private float blendingDuration = 0.0F;

        /**
         * Create a new builder with the specified animation length.
         *
         * @param lengthInSeconds The length of the animation in seconds.
         * @return A new builder instance.
         */
        public static ElysiumAnimationDefinition.Builder withLength(float lengthInSeconds) {
            return new ElysiumAnimationDefinition.Builder(lengthInSeconds);
        }

        private Builder(float pLengthInSeconds) {
            this.length = pLengthInSeconds;
        }

        /**
         * Mark the animation as looping.
         *
         * @return The builder instance.
         */
        public ElysiumAnimationDefinition.Builder looping() {
            this.looping = true;
            return this;
        }

        /**
         * Enable blending for the animation with the specified duration.
         *
         * @param blendingDuration The duration of blending in seconds.
         * @return The builder instance.
         */
        public ElysiumAnimationDefinition.Builder withBlending(float blendingDuration) {
            this.blendingDuration = blendingDuration;
            return this;
        }

        /**
         * Assign animation to a bone.
         *
         * @param bone The bone name.
         * @param animationChannel The animation channel.
         * @return The builder instance.
         */
        public ElysiumAnimationDefinition.Builder addAnimation(String bone, AnimationChannel animationChannel) {
            this.animationByBone.computeIfAbsent(bone, name -> new ArrayList<>()).add(animationChannel);
            return this;
        }

        /**
         * Builds the animation definition.
         *
         * @return The built animation definition.
         */
        public ElysiumAnimationDefinition build() {
            return new ElysiumAnimationDefinition(this.length, this.looping, this.blendingDuration, this.animationByBone);
        }
    }
}
