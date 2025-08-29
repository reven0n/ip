package som.task;

/**
 * Represents a generic bask.
 * <p>
 * All tasks have a description, a completion status, and an ID.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     *
     * @param description the task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon: "X" if done, " " otherwise.
     *
     * @return "X" or " "
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as done
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done
     */
    public void markAsUndone() {
        isDone = false;
    }

    public abstract String encode();

    /**
     * Returns string representation of the task.
     *
     * @return formatted task string
     */
    @Override
    public String toString() {
        return " [" + getStatusIcon() + "] " + description;
    }
}
