package team.durt.elysium.api.animation.state;

/**
 * Interface for playable animation state.
 */
public interface PlayableAnimationState {
    /**
     * Determines if the animation is playing.
     *
     * @return true if playing, otherwise false.
     */
    boolean isPlaying();
}