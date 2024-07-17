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

    public void play(boolean force) {
        if (force || !playing) {
            this.animatedTime = 0;
            this.lastUpdateTime = 0;
        }
        this.playing = true;
    }

    public void stop() {
        this.playing = false;
        this.animatedTime = Long.MAX_VALUE;
        this.lastUpdateTime = Long.MAX_VALUE;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void writeToBuffer(FriendlyByteBuf buffer) {
        buffer.writeBoolean(playing);
    }

    public void readFromBuffer(FriendlyByteBuf buffer) {
        this.playing = buffer.readBoolean();
    }

    public long animatedTime() {
        return this.animatedTime;
    }

    public long lastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void updateTime(float ageInTicks, float speed) {
        if (this.playing) {
            long i = Mth.lfloor(animatedTime * 1000.0F / 20.0F);
            this.animatedTime += (long)((i - this.lastUpdateTime) * speed);
            this.lastUpdateTime = i;
        }
    }
}