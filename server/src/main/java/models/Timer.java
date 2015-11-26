package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Lists;
import models.serialization.JsonViews;
import org.joda.time.Duration;
import org.joda.time.Instant;

import java.util.List;

/**
 * Task timer representation. Timer counts
 * the difference between start and stop, and
 * notifies the task about these actions.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 18.11.2015
 */
public class Timer {
    @JsonView(JsonViews.Public.class)
    private boolean isRunning = false;

    @JsonView(JsonViews.Public.class)
    private Instant started;

    @JsonIgnore
    private List<StateChangedListener> stateChangedListeners = Lists.newArrayList();

    public void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.started = Instant.now();

            fireOnTimerStartEvent();
        }
    }

    public Duration stop() {
        if (this.isRunning()) {
            Duration duration = new Duration(this.started, Instant.now());
            this.isRunning = false;
            this.started = null;

            fireOnTimerStopEvent(duration);

            return duration;
        }

        return new Duration(0);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public Instant getStarted() {
        return started;
    }

    public void subscribe(StateChangedListener listener) {
        if (listener != null) {
            stateChangedListeners.add(listener);
        }
    }

    public void unsubscribe(StateChangedListener listener) {
        if (listener != null && stateChangedListeners.contains(listener)) {
            stateChangedListeners.remove(listener);
        }
    }

    private void fireOnTimerStartEvent() {
        for (StateChangedListener listener : stateChangedListeners) {
            listener.onTimerStart(this);
        }
    }

    private void fireOnTimerStopEvent(Duration duration) {
        for (StateChangedListener listener : stateChangedListeners) {
            listener.onTimerStop(this, duration);
        }
    }

    public interface StateChangedListener {
        /**
         * Notifies when timer changed state to active.
         *
         * @param sender instance of Timer, changed its state
         */
        void onTimerStart(Timer sender);

        /**
         * Notifies that timer state has been changed to inactive.
         * Calculates and returns the time, passed since it has been
         * started.
         *
         * @param sender     instance of Timer, changed its state
         * @param timePassed how much time passed since Timer has been started
         */
        void onTimerStop(Timer sender, Duration timePassed);
    }
}
