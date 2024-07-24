package team.durt.elysium.api.animation.controller;

import net.minecraft.network.FriendlyByteBuf;

/**
 * Interface for animation controllers that can be checked for compatibility between server and client.
 */
public interface CompatibleAnimationController {
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
