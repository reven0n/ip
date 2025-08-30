package som;

import som.task.Deadline;
import som.task.Event;
import som.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the collection of tasks in the chatbot.
 * <p>This class encapsulates the interal list of Task objects and provides
 * methods for the list. It maintains the state of the task list and
 * ensures operations are performed safely.</p>
 *
 * @author Darien Tan
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Constructs an empty TaskList
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given tasks.
     *
     * @param tasks the initial list of tasks to include.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list.
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Adds a new task to the list.
     *
     * @param task the task to be added, must not be null.
     */
    public void add(Task task) {
        this.tasks.add(task);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index the 0-based index of the task to retrieve.
     * @return the Task at the given index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public Task get(int index) {
        return this.tasks.get(index);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index the 0-based index of the task to remove.
     * @return the removed Task.
     * @throws SomException if the index is out of range.
     */
    public Task remove(int index) throws SomException {
        try {
            return this.tasks.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new SomException("No tasks found with that number.");
        }
    }

    /**
     * Marks the task at the specified index.
     *
     * @param index the 0-based index of the task to mark.
     * @throws SomException if the index is out of range.
     */
    public void mark(int index) throws SomException {
        try {
            tasks.get(index).markAsDone();
        } catch (IndexOutOfBoundsException e) {
            throw new SomException("No tasks found with that number.");
        }
    }

    /**
     * Unmarks the task at the specified index.
     *
     * @param index the 0-based index of the task to unmark.
     * @throws SomException if the index is out of range.
     */
    public void unmark(int index) throws SomException {
        try {
            tasks.get(index).markAsUndone();
        } catch (IndexOutOfBoundsException e) {
            throw new SomException("No tasks found with that number.");
        }
    }

    /**
     * Returns a copy of all tasks in the list.
     *
     * @return a new list containing all tasks.
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Finds and displays all tasks (deadlines and events) that occur on a specified date.
     * <p>The user provides a date in the format {@code dd-MM-yyyy} (e.g., 11-03-2025).
     * The method parses the input and searches through the task list for: Deadline and Event only.
     * Matching tasks are printed in a formatted list. If no tasks are found,
     * a friendly message is shown instead.</p>
     * <p>Example usage: find 11-03-2025</p>
     *
     * @param input the full user command (must start with "find" followed by a date).
     * @return a List consisting of all matching tasks with the specified date.
     * @throws SomException if the date string is invalid or in wrong format.
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
