package team.durt.elysium.core.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApiStatus.Internal
public class ElysiumSchedule {
    private static final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

    public static void schedule(@NotNull Runnable command, long delay, TimeUnit timeUnit) {
        scheduledExecutor.schedule(command, delay, timeUnit);
    }
}
