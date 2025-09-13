package som;

import java.util.List;
import java.util.Scanner;

import som.task.Task;

/**
 * Handles all user interactions with the Som chatbot.
 * <p>This class is responsible for displaying messages, reading user input, and formatting output
 * consistently using decorative lines. It encapsulates all console-based I/O, ensuring separation
 * of concerns between user interaction and business logic.</p>
 *
 * @author Darien Tan
 */
public class Ui {
    private Scanner sc;

    /**
     * Constructs a new Ui instance with a Scanner for reading user input.
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Displays the welcome message when the app starts.
     * <p>Prints a greeting, a brief description of the app, and an instruction to type "help".</p>
     */
    public String showWelcome() {
        String items = "";
        items = items.concat(" Hello! I'm Som\n What can I do for you?");
        items = items.concat("\n Type 'help' for a list of commands.");
        return items;
    }

    /**
     * Displays an error message in a formatted way.
     *
     * @param exception the error message to display.
     * @return string representing the error message.
     */
    public String showError(String exception) {
        return (" " + exception);
    }

    /**
     * Displays the goodbye message when the user exits.
     * <p>Prints a farewell message and closes the Scanner to release system resources. </p>
     *
     * @return string representing the goodbye message.
     */
    public String showGoodbye() {
        return (" Bye. Hope to see you again soon!");
    }

    /**
     * Displays all tasks occurring on a specific date.
     *
     * @param matches the list of matching tasks. If no tasks are found,
     *                a friendly message is shown instead.
     * @param keyword the target date to display.
     * @return string representing tasks matching the keyword.
     */
    public String showFindResults(List<Task> matches, String keyword) {
        if (matches.isEmpty()) {
            return " No tasks found matching '" + keyword + "'";
        }
        String items = "";
        items = items.concat(" Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            items = items.concat(" " + (i + 1) + "." + matches.get(i).toString() + "\n");
        }
        return items;
    }

    /**
     * Confirms the deletion of a task.
     *
     * @param task the task that was deleted.
     * @param total the number of tasks remaining in the list.
     * @return string representing the removal of a task.
     */
    public String showDelete(Task task, int total) {
        String items = "";
        items = items.concat(" Noted. I've removed this task:\n");
        items = items.concat("  " + task.toString() + "\n");
        items = items.concat(" Now you have " + total + " tasks in the list");
        return items;
    }

    /**
     * Confirms the addition of a new Task.
     *
     * @param task the task that was added.
     * @param total the total number of tasks in the list after addition.
     * @return string representing the addition of a task.
     */
    public String showAdd(Task task, int total) {
        String items = "";
        items = items.concat(" Got it. I've added this task: \n");
        items = items.concat(task.toString() + "\n");
        items = items.concat(" Now you have " + total + " tasks in the list");
        return items;
    }

    /**
     * Displays all tasks in the current list with their index and status.
     * <p>If the list is empty, a message is shown indicating no tasks exist.
     * Each task is printed with its ID (1-based) and string representation.</p>
     *
     * @param tasks the list of tasks to display.
     * @return string representing the list of tasks.
     */
    public String showList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return " Oops! Your task list is empty!";
        }
        String items = "";
        for (int i = 0; i < tasks.size(); i++) {
            items = items.concat(i + 1 + ". " + tasks.get(i).toString() + "\n ");
        }
        return items;
    }

    /**
     * Confirms that a task has been marked as done.
     *
     * @param task the task that was marked as done.
     * @return string representing a marked task.
     */
    public String showMarked(Task task) {
        return (" Nice! I've marked this task as done:\n" + task.toString());
    }

    /**
     * Confirms that a task has been marked as not done.
     *
     * @param task the task that was marked as not done.
     * @return string representing an unmarked task.
     */
    public String showUnmarked(Task task) {
        return ("OK, I've marked this task as not done yet:\n" + task.toString());
    }

    /**
     * Displays an error message when loading tasks fails.
     *
     * @param error the error message to display.
     * @return string representing an error message.
     */
    public String showLoadingError(String error) {
        return ("Error loading: " + showError(error) + "\nStarting with empty task list.");
    }

    /**
     * Displays a formatted list of all available commands to the user.
     * <p>This method prints a help menu that explains the syntax and purpose.</p>
     *
     * @return string representing the help menu.
     */
    public String showHelp() {
        String items = "";
        items = items.concat(" Available commands:\n");
        items = items.concat("  todo <desc>                  – Add a todo\n");
        items = items.concat("  deadline <desc> /by <time>   – Add a deadline\n");
        items = items.concat("  event <desc> /from <start> /to <end>  – Add an event\n");
        items = items.concat("  list                         – Show all tasks\n");
        items = items.concat("  mark <index>                 – Mark task as done\n");
        items = items.concat("  unmark <index>               – Mark task as not done\n");
        items = items.concat("  find <keyword>               – Find tasks containing the keyword\n");
        items = items.concat("  help                         – Show this message\n");
        items = items.concat("  bye                          – Exit");
        return items;
    }

}
