package uk.co.epsilontechnologies.taximeter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * <p>Wrapper of a Scheduled Executor Service to provide polling every tenth of a second for updating the taxi meter.
 *
 * <p>By polling every 1/10 of a second, the taxi meter will ensure accurate fares, since the rules of TfL's Tariffs
 * use time limits down to the tenth of a second. The shortest distance at which rates are incremented is 89.2 metres,
 * which equates to a window of 892 metres per second, or 3211 kilometres per hour - much faster than the standard
 * London Taxi. Therefore there should be no need to recursively or iteratively increment the fare in a single
 * calculation.
 *
 * @author Shane Gibson
 */
public class Poller {

    /**
     * The scheduler to use. A single thread is available, since there is no intention of the meter accepting concurrent fares.
     */
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);

    /**
     * The scheduled future for the current journey.
     */
    private ScheduledFuture<?> scheduledFuture;

    public void start(final Runnable runnable) {
        this.scheduledFuture = SCHEDULER.scheduleAtFixedRate(runnable, 100, 100, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops / cancels the current poller or scheduler.
     */
    public void stop() {
        scheduledFuture.cancel(false);
    }

}