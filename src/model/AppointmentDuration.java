package model;

import java.time.Duration;

public class AppointmentDuration  {
    Duration duration;

    public AppointmentDuration(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }

    public String toString() {

        String duration = "";
        long seconds = this.duration.getSeconds();
        int minutes = 0;
        int hours = 0;

        while(seconds >= 3600) {
            hours++;
            seconds -= 3600;
        }
        while(seconds >= 60) {
            minutes++;
            seconds -= 60;
        }
        duration += hours + ":";

        if(minutes < 10) {
            duration += 0;
        }
        duration += minutes;

        return duration;
    }
}
