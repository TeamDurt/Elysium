package team.durt.elysium.api.animation.keyframe;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3f;
import team.durt.elysium.api.animation.definition.ElysiumAnimationDefinition;
import team.durt.elysium.api.animation.entity.AnimatedEntity;
import team.durt.elysium.api.animation.model.AnimatedModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApiStatus.Internal
public class ElysiumKeyframeAnimations {
    public static <T extends LivingEntity & AnimatedEntity<T>> void animate(AnimatedModel<T> model, ElysiumAnimationDefinition definition, long animatedTime, float scale, Vector3f animationVectorCache) {
        float f = getElapsedSeconds(definition, animatedTime);

        for (Map.Entry<String, List<AnimationChannel>> entry : definition.boneAnimations().entrySet()) {
            Optional<ModelPart> optional = model.getModelPart(entry.getKey());
            List<AnimationChannel> list = entry.getValue();
            optional.ifPresent(part -> list.forEach(channel -> {
                Keyframe[] akeyframe = channel.keyframes();
                int i = Math.max(0, Mth.binarySearch(0, akeyframe.length, i1 -> f <= akeyframe[i1].timestamp()) - 1);
                int j = Math.min(akeyframe.length - 1, i + 1);
                Keyframe keyframe = akeyframe[i];
                Keyframe keyframe1 = akeyframe[j];
                float f1 = f - keyframe.timestamp();
                float delta;
                if (j != i) {
                    delta = Mth.clamp(f1 / (keyframe1.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
                } else {
                    delta = 0.0F;
                }

                keyframe1.interpolation().apply(animationVectorCache, delta, akeyframe, i, j, scale);

                float blendDelta = Mth.clamp((float) animatedTime / definition.blendingDuration(), 0.0F, 1.0F);

                float easedX = easeInOutQuad(0, animationVectorCache.x, blendDelta);
                float easedY = easeInOutQuad(0, animationVectorCache.y, blendDelta);
                float easedZ = easeInOutQuad(0, animationVectorCache.z, blendDelta);

                channel.target().apply(part, new Vector3f(easedX, easedY, easedZ));
            }));
        }
    }

    private static float easeInOutQuad(float from, float to, float delta) {
        float eased = delta < 0.5F ? 2F * delta * delta : (float) (1 - Math.pow(-2 * delta + 2, 2) / 2);
        return from + (to - from) * eased;
    }

    private static float getElapsedSeconds(ElysiumAnimationDefinition animationDefinition, long animatedTime) {
        float f = (float) animatedTime / 1000.0F;
        return animationDefinition.looping() ? f % animationDefinition.lengthInSeconds() : f;
    }

    public static Vector3f posVec(float x, float y, float z) {
        return new Vector3f(x, -y, z);
    }

    public static Vector3f degreeVec(float xDegrees, float yDegrees, float zDegrees) {
        return new Vector3f(xDegrees * (float) (Math.PI / 180.0), yDegrees * (float) (Math.PI / 180.0), zDegrees * (float) (Math.PI / 180.0));
    }

    public static Vector3f scaleVec(double xScale, double yScale, double zScale) {
        return new Vector3f((float) (xScale - 1.0), (float) (yScale - 1.0), (float) (zScale - 1.0));
    }
}
