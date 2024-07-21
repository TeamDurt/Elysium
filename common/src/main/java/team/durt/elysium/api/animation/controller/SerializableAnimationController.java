package team.durt.elysium.api.animation.controller;

import net.minecraft.network.FriendlyByteBuf;

/**
 * Interface for animation controllers that can be serialized/deserialized for network transmission via {@link FriendlyByteBuf}.
 */
public interface SerializableAnimationController {
    /**
     * Serializes the controller into a buffer for network transmission.
     *
     * @param buffer Target buffer for serialization.
     */
    void writeToBuffer(FriendlyByteBuf buffer);

    /**
     * Deserializes the controller from a buffer received over the network.
     *
     * @param buffer Source buffer for deserialization.
     */
    void readFromBuffer(FriendlyByteBuf buffer);
}
