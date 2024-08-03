package team.durt.elysium.api.animation.keyframe;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3f;
import team.durt.elysium.api.animation.model.AnimatedModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApiStatus.Internal
public class ElysiumKeyframeAnimations {
    public static void animate(AnimatedModel model, AnimationDefinition definition, long animatedTime, float scale, Vector3f animationVectorCache) {
        float f = getElapsedSeconds(definition, animatedTime);

        for (Map.Entry<String, List<AnimationChannel>> entry : definition.boneAnimations().entrySet()) {
            Optional<ModelPart> optional = model.getAnyDescendantWithName(entry.getKey());
            List<AnimationChannel> list = entry.getValue();
            optional.ifPresent(p_232330_ -> list.forEach(p_288241_ -> {
                Keyframe[] akeyframe = p_288241_.keyframes();
                int i = Math.max(0, Mth.binarySearch(0, akeyframe.length, i1 -> f <= akeyframe[i1].timestamp()) - 1);
                int j = Math.min(akeyframe.length - 1, i + 1);
                Keyframe keyframe = akeyframe[i];
                Keyframe keyframe1 = akeyframe[j];
                float f1 = f - keyframe.timestamp();
                float f2;
                if (j != i) {
                    f2 = Mth.clamp(f1 / (keyframe1.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
                } else {
                    f2 = 0.0F;
                }

                keyframe1.interpolation().apply(animationVectorCache, f2, akeyframe, i, j, scale);
                p_288241_.target().apply(p_232330_, animationVectorCache);
            }));
        }
    }

    private static float getElapsedSeconds(AnimationDefinition animationDefinition, long animatedTime) {
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
