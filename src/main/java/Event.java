/**
 * Represents an event task with start and end times.
 */
public class Event extends Task{

    private String from;
    private String to;

    /**
     * Constructs a new Event task.
     *
     * @param description the task description
     * @param from the start time
     * @param to the end time
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Encodes a task into a line like: E | 1 | read book | 10am | 10pm
     */
    public String encode() {
        return("E | " + (this.isDone ? "1" : "0") + " | " + this.description
                + " | " + this.from + " | " + this.to);
    }

    /**
     * Returns string representation of the Event task.
     *
     * @return formatted task string
     */
    @Override
    public String toString(){
        return " [E] " + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
