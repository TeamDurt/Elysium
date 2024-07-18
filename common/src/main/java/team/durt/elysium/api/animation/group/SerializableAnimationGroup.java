package team.durt.elysium.api.animation.group;

import net.minecraft.network.FriendlyByteBuf;

/**
 * Interface for animation groups that can be serialized/deserialized for network transmission via {@link FriendlyByteBuf}.
 */
public interface SerializableAnimationGroup {
    /**
     * Serializes the group into a buffer for network transmission.
     *
     * @param buffer Target buffer for serialization.
     */
    void writeToBuffer(FriendlyByteBuf buffer);

    /**
     * Deserializes the state from a buffer received over the network.
     *
     * @param buffer Source buffer for deserialization.
     */
    void readFromBuffer(FriendlyByteBuf buffer);
}
