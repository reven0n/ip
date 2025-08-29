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
     * Finds and displays all tasks (deadlines and events) that occur on a specified date.
     * <p>
     * The user provides a date in the format {@code dd-MM-yyyy} (e.g., 11-03-2025).
     * The method parses the input and searches through the task list for: Deadline and Event only.
     * Matching tasks are printed in a formatted list. If no tasks are found,
     * a friendly message is shown instead.
     * <p>
     * Example usage: find 11-03-2025
     *
     * @param input the full user command (must start with "find" followed by a date)
     * @throws SomException if the date string is invalid or in wrong format
     * @returns a List consisting of all matching tasks with the specified date
     */
    public List<Task> findTasks(String input) throws SomException {
        List<Task> matches = new ArrayList<>();
        try {
            LocalDate targetDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            for (Task t : tasks) {
                if (t instanceof Deadline) {
                    if (((Deadline) t).getby().toLocalDate().isEqual(targetDate)) {
                        matches.add(t);
                    }
                } else if (t instanceof Event) {
                    if (((Event) t).getFrom().toLocalDate().isEqual(targetDate)) {
                        matches.add(t);
                    }
                }
            }
        } catch (DateTimeParseException e) {
            throw new SomException("Please enter a valid date. Format: yyyy-MM-dd (e.g., 2025-03-11)");
        }
        return matches;
    }


}
