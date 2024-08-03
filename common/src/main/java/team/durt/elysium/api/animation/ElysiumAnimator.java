package team.durt.elysium.api.animation;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.world.entity.LivingEntity;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;
import team.durt.elysium.api.animation.entity.AnimatedEntity;
import team.durt.elysium.api.animation.keyframe.ElysiumKeyframeAnimations;
import team.durt.elysium.api.animation.model.AnimatedModel;

/**
 * Utility class for animating entities.
 */
public class ElysiumAnimator {
    /**
     * Animates an entity using the provided model.
     *
     * @param entity The entity to animate.
     * @param model The model to animate.
     * @param limbSwing The limb swing.
     * @param limbSwingAmount The limb swing amount.
     * @param ageInTicks The age in ticks.
     * @param <T> The entity type.
     */
    public static <T extends LivingEntity & AnimatedEntity> void animate(T entity, AnimatedModel model, float limbSwing, float limbSwingAmount, float ageInTicks) {
        ElysiumAnimationController<T> controller = entity.getAnimationController();
        controller.getWalkingAnimations().stream()
                .filter(tWalkingAnimation -> tWalkingAnimation.predicate().test(entity))
                .forEach(tWalkingAnimation -> animateWalking(tWalkingAnimation, model, limbSwing, limbSwingAmount));
        controller.getAnimationGroups().forEach((groupName, group) -> {
            group.getAnimationStates().forEach((stateName, state) -> {
                // todo provide speed
                state.updateTime(ageInTicks, 1.0F);
                if (state.isPlaying()) {
                    AnimationDefinition animationDefinition = group.getAnimation(stateName).orElseThrow(() -> new IllegalStateException("Animation not found"));
                    ElysiumKeyframeAnimations.animate(model, animationDefinition, state.getAnimatedTime(), 1.0F, model.ANIMATION_VECTOR_CACHE);
                }
            });
        });
    }

    private static <T extends LivingEntity & AnimatedEntity> void animateWalking(ElysiumAnimationController.WalkingAnimation<T> animation, AnimatedModel model, float limbSwing, float limbSwingAmount) {
        long i = (long)(limbSwing * 50.0F * animation.maxSpeed());
        float f = Math.min(limbSwingAmount * animation.scaleFactor(), 1.0F);
        ElysiumKeyframeAnimations.animate(model, animation.animationDefinition(), i, f, model.ANIMATION_VECTOR_CACHE);
    }
}
