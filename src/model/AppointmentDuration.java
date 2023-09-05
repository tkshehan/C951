package model;

import java.time.Duration;

/** This class models appointment durations for use with JavaFX toString() conversions. (ie: ComboBox)
 */
public class AppointmentDuration  {
    /** The duration of the appointment.
     */
    Duration duration;

    /** Constructor: assigns the duration.
     * @param duration The duration to assign.
     */
    public AppointmentDuration(Duration duration) {
        this.duration = duration;
    }

    /** Returns the duration.
     * @return The duration to return.
     */
    public Duration getDuration() {
        return duration;
    }

    /** Returns the duration as a string in hour:minute format.
     * @return The string to return.
     */
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
