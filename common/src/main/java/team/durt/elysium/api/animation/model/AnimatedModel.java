package team.durt.elysium.api.animation.model;

import net.minecraft.client.model.geom.ModelPart;
import org.joml.Vector3f;

import java.util.Optional;

/**
 * Interface for models that can be animated.
 */
public interface AnimatedModel {
    static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    /**
     * Retrieves the root model part of this model.
     *
     * @return The root {@link ModelPart} of this model.
     */
    ModelPart root();

    default Optional<ModelPart> getAnyDescendantWithName(String name) {
        return name.equals("root")
                ? Optional.of(this.root())
                : this.root().getAllParts().filter(part -> part.hasChild(name)).findFirst().map(part -> part.getChild(name));
    }
}
