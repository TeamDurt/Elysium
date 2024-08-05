package team.durt.elysium.api.animation.model;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3f;
import team.durt.elysium.api.animation.controller.ElysiumAnimationController;
import team.durt.elysium.api.animation.entity.AnimatedEntity;
import team.durt.elysium.api.animation.keyframe.ElysiumKeyframeAnimations;

import java.util.Optional;

/**
 * Interface for models that can be animated.
 */
public interface AnimatedModel<T extends LivingEntity & AnimatedEntity<T>> {
    static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    /**
     * Retrieves the root model part of this model.
     *
     * @return The root {@link ModelPart} of this model.
     */
    ModelPart root();

    /**
     * Retrieves a model part by name.
     *
     * @param name The name of the model part.
     * @return An {@link Optional} containing the model part if found, or an empty {@link Optional} otherwise.
     */
    default Optional<ModelPart> getModelPart(String name) {
        return name.equals("root")
                ? Optional.of(this.root())
                : this.root().getAllParts().filter(part -> part.hasChild(name)).findFirst().map(part -> part.getChild(name));
    }

    /**
     * Animates an entity using the provided model.
     *
     * @param entity The entity to animate.
     * @param model The model to animate.
     * @param limbSwing The limb swing.
     * @param limbSwingAmount The limb swing amount.
     * @param ageInTicks The age in ticks.
     */
    default void animate(T entity, AnimatedModel<T> model, float limbSwing, float limbSwingAmount, float ageInTicks) {
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

    /**
     * Animates walking.
     *
     * @param animation The walking animation.
     * @param model The model to animate.
     * @param limbSwing The limb swing.
     * @param limbSwingAmount The limb swing amount.
     */
    @ApiStatus.Internal
    default void animateWalking(ElysiumAnimationController.WalkingAnimation<T> animation, AnimatedModel<T> model, float limbSwing, float limbSwingAmount) {
        long i = (long)(limbSwing * 50.0F * animation.maxSpeed());
        float f = Math.min(limbSwingAmount * animation.scaleFactor(), 1.0F);
        ElysiumKeyframeAnimations.animate(model, animation.animationDefinition(), i, f, model.ANIMATION_VECTOR_CACHE);
    }
}
