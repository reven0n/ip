package som.task;

import som.SomException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with start and end times.
 *
 * <p>An event task includes a description, start time, and end time. Both times
 * are stored as LocalDateTime objects for accurate comparison and formatting.
 * The start time must be before the end time.</p>
 *
 * @author Darien Tan
 */
public class Event extends Task{
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    /**
     * Constructs a new Event task.
     *
     * @param description the task description
     * @param strFrom the start time
     * @param strTo the end time, must be after start tome
     */
    public Event(String description, String strFrom, String strTo) throws SomException {
        super(description);
        try {
            this.from = LocalDateTime.parse(strFrom, INPUT_FORMAT);
            this.to = LocalDateTime.parse(strTo, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new SomException("Invalid date format! Use: yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)");
        }
        if (from.isAfter(to)) {
            throw new SomException("Start time cannot be after end time.");
        }
    }

    /**
     * Returns the start date and time of the Event
     *
     * @return the LocalDateTime representing when the event starts
     */
    public LocalDateTime getFrom() {
        return this.from;
    }

    /**
     * Returns the end date and time of the Event
     *
     * @return the LocalDateTime representing when the event ends
     */
    public LocalDateTime getTo() {
        return this.to;
    }

    /**
     * Encodes a task into a line like: E | 1 | read book | 10am | 10pm
     *
     * @return the formatted representation of the task to be saved
     */
    @Override
    public String encode() {
        return("E | " + (this.isDone ? "1" : "0") + " | " + this.description
                + " | " + this.from.format(INPUT_FORMAT) + " | " + this.to.format(INPUT_FORMAT));
    }

    /**
     * Returns string representation of the Event task.
     *
     * @return formatted task string
     */
    @Override
    public String toString(){
        return " [E] " + super.toString() + " (from: "
                + this.from.format(OUTPUT_FORMAT) + " to: " + this.to.format(OUTPUT_FORMAT) + ")";
    }
}
