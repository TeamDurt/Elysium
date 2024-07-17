package team.durt.elysium.api.animation.state;

/**
 * Abstract state for animations in the Elysium framework, handling lifecycle operations like play and stop.
 */
public abstract class ElysiumAnimationState implements SerializableAnimationState, PlayableAnimationState, TimedAnimationState {

    /**
     * Initiates or restarts the playback of the animation.
     *
     * @param force If true, the animation will start from the beginning, even if it is already playing.
     */
    public abstract void play(boolean force);

    /**
     * Stops the animation.
     */
    public abstract void stop();
}
