package team.durt.elysium.api.animation.group;

import net.minecraft.network.FriendlyByteBuf;

/**
 * Interface for animation groups that can be checked for compatibility between server and client.
 */
public interface CompatibleAnimationGroup {
    /**
     * Writes compatibility data to a buffer for network transmission.
     *
     * @param buffer Target buffer for serialization.
     */
    void writeCompatibilityData(FriendlyByteBuf buffer);

    /**
     * Reads compatibility data from a buffer received over the network.
     *
     * @param buffer Source buffer for deserialization.
     */
    boolean checkCompatibility(FriendlyByteBuf buffer);
}
