package javanut6.ch09;

import java.time.LocalDateTime;

public class Reminder {

    private String action;
    private LocalDateTime time;

    public Reminder(String action, LocalDateTime time) {
        this.action = action;
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
