package models.dto;

/**
 * Represents a timer state DTO.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 22.11.2015
 */
public class TimerState {
    private boolean isRunning = false;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
