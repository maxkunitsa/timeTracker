package models.dto;

import com.fasterxml.jackson.annotation.JsonView;
import models.serialization.JsonViews;
import ninja.validation.Required;

/**
 * Represents a current state of the timer.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 22.11.2015
 */
public class TimerState {
    private boolean isRunning = false;

    public TimerState() {

    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
