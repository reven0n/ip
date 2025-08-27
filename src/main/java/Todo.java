/**
 * * Represents a todo task with no time constraints.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task.
     *
     * @param description the task description
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Encodes a task into a line like: T | 1 | read book
     */
    @Override
    public String encode() {
        return("T | " + (this.isDone ? "1" : "0") + " | " + this.description);
    }

    /**
     * Returns string representation of the Todo task.
     *
     * @return formatted task string
     */
    @Override
    public String toString(){
        return " [T] " + super.toString();
    }
}
