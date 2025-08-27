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
     * Returns string representation of the Todo task.
     *
     * @return formatted task string
     */
    @Override
    public String toString(){
        return " [T] " + super.toString();
    }
}
