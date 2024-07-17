package team.durt.elysium.api.animation.state;

/**
 * Interface for timed animation state with methods for time management.
 */
public interface TimedAnimationState {
    /**
     * Returns the current animation time in milliseconds.
     *
     * @return Current animation time in milliseconds.
     */
    long animatedTime();

    /**
     * Returns the time of the last update in milliseconds.
     *
     * @return Last update time in milliseconds.
     */
    long lastUpdateTime();

    /**
     * Updates the animation state's time.
     *
     * @param ageInTicks Entity age in ticks.
     * @param speed Animation speed.
     */
    void updateTime(float ageInTicks, float speed);
}