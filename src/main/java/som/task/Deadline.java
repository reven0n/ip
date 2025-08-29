package som.task;

import som.SomException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task with a due date/time.
 */
public class Deadline extends Task {
    private LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    /**
     * Constructs a new Deadline task.
     *
     * @param description the task description
     * @param strBy the deadline date/time
     */
    public Deadline(String description, String strBy) throws SomException {
        super(description);
        try {
            this.by = LocalDateTime.parse(strBy, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new SomException("Invalid date format! Use: yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)");
        }
    }

    /**
     * Returns the start date and time of the Deadline
     *
     * @return the LocalDateTime representing when the task is due
     */
    public LocalDateTime getby() {
        return this.by;
    }

    /**
     * Encodes a task into a line like: D | 1 | read book | 16 Aug
     *
     * @return encodes a task into a line like: D | 1 | read book | 16 Aug
     */
    @Override
    public String encode() {
        return("D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.by.format(INPUT_FORMAT));
    }

    /**
     * Returns string representation of the Deadline task.
     *
     * @return the formatted task string
     */
    @Override
    public String toString() {
        return " [D] " + super.toString() + " (by: " + this.by.format(OUTPUT_FORMAT) + ")";
    }
}
