package team.durt.elysium.impl.animation.state;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import team.durt.elysium.api.animation.state.ElysiumAnimationState;

/**
 * Implementation of {@link ElysiumAnimationState} to manage animation states.
 */
public class ElysiumAnimationStateImpl extends ElysiumAnimationState {
    private boolean playing; // Tracks if the animation is currently playing.
    private long animatedTime = Long.MAX_VALUE; // Stores the animated time in milliseconds.
    private long lastUpdateTime = Long.MAX_VALUE; // Stores the last update time in milliseconds.

    /**
     * Constructs a new {@code ElysiumAnimationStateImpl} instance with initial state.
     */
    public ElysiumAnimationStateImpl() {
        this.playing = false;
        this.animatedTime = 0;
        this.lastUpdateTime = 0;
    }

    /**
     * Starts or resumes the animation.
     *
     * @param force If true, the animation will restart regardless of its current state.
     */
    public void play(boolean force) {
        if (force || !playing) {
            this.animatedTime = 0;
            this.lastUpdateTime = 0;
        }
        this.playing = true;
    }

    /**
     * Stops the animation and resets its state.
     */
    public void stop() {
        this.playing = false;
        this.animatedTime = Long.MAX_VALUE;
        this.lastUpdateTime = Long.MAX_VALUE;
    }

    /**
     * Checks if the animation is currently playing.
     *
     * @return true if the animation is playing, false otherwise.
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Sets the playing state of the animation.
     *
     * @param playing The new playing state.
     */
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    /**
     * Writes the current playing state to a {@link FriendlyByteBuf}.
     *
     * @param buffer The buffer to write to.
     */
    public void writeToBuffer(FriendlyByteBuf buffer) {
        buffer.writeBoolean(playing);
    }

    /**
     * Reads the playing state from a {@link FriendlyByteBuf}.
     *
     * @param buffer The buffer to read from.
     */
    public void readFromBuffer(FriendlyByteBuf buffer) {
        this.playing = buffer.readBoolean();
    }

    /**
     * Gets the animated time.
     *
     * @return The animated time in milliseconds.
     */
    public long animatedTime() {
        return this.animatedTime;
    }

    /**
     * Gets the last update time.
     *
     * @return The last update time in milliseconds.
     */
    public long lastUpdateTime() {
        return this.lastUpdateTime;
    }

    /**
     * Updates the animation time based on the age in ticks and speed.
     *
     * @param ageInTicks The age in ticks.
     * @param speed The speed multiplier for the animation time update.
     */
    public void updateTime(float ageInTicks, float speed) {
        if (this.playing) {
            long i = Mth.lfloor(animatedTime * 1000.0F / 20.0F);
            this.animatedTime += (long)((i - this.lastUpdateTime) * speed);
            this.lastUpdateTime = i;
        }
    }
}