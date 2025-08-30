package som;

import som.task.Deadline;
import som.task.Event;
import som.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return this.tasks.size();
    }

    public void add(Task task) {
        this.tasks.add(task);
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }

    public Task remove(int index) throws SomException {
        try {
            return this.tasks.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new SomException("No tasks found with that number.");
        }
    }

    public void mark(int index) throws SomException {
        try {
            tasks.get(index).markAsDone();
        } catch (IndexOutOfBoundsException e) {
            throw new SomException("No tasks found with that number.");
        }
    }

    public void unmark(int index) throws SomException {
        try {
            tasks.get(index).markAsUndone();
        } catch (IndexOutOfBoundsException e) {
            throw new SomException("No tasks found with that number.");
        }
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Finds and displays all tasks that occur with the specified keyword.
     * <p>
     * The user provides a keyword in the format {@code find book}.
     * The method parses the input and searches through the task list the keyword.
     * Matching tasks are printed in a formatted list. If no tasks are found,
     * a friendly message is shown instead.
     * <p>
     * Example usage: find book
     *
     * @param keyword the item to be looked for
     * @return a List consisting of all matching tasks with the specified date
     */
    public List<Task> findTasks(String keyword) {
        List<Task> matches = new ArrayList<>();

        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(t);
            }
        }

        return matches;
    }


}
