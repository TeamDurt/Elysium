package team.durt.elysium.api.animation.group.transition;

import net.minecraft.network.FriendlyByteBuf;

/**
 * Interface for animation group transitions that can be serialized/deserialized for network transmission.
 */
public interface SerializableAnimationGroupTransition {
    /**
     * Serializes the transition into a buffer for network transmission.
     *
     * @param buffer Target buffer for serialization.
     */
    void writeToBuffer(FriendlyByteBuf buffer);

    /**
     * Deserializes the transition from a buffer received over the network.
     *
     * @param buffer Source buffer for deserialization.
     */
    void readFromBuffer(FriendlyByteBuf buffer);
}
