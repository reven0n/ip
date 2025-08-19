public class Task {
    protected String description;
    protected boolean isDone;
    private int id;
    static int total = 0;

    public Task() {
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        total++;
        this.id = total;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsUndone() {
        isDone = false;
    }

    public int getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return " [" + getStatusIcon() + "] " + description;
    }
}
