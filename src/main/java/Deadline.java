/**
 * Represents a deadline task with a due date/time.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Constructs a new Deadline task.
     *
     * @param description the task description
     * @param by the deadline date/time
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns string representation of the Deadline task.
     *
     * @return formatted task string
     */
    @Override
    public String toString() {
        return " [D] " + super.toString() + " (by: " + by + ")";
    }
}
