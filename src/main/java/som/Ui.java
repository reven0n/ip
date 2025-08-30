package som;

import som.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;
import java.util.List;

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
    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm Som\n What can I do for you?");
        System.out.println(" Type 'help' for a list of commands.");
        showLine();
    }

    /**
     * Prints a decorative line separator to improve readability.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Reads a command from the user input.
     *
     * @return the trimmed user input, or empty string if input is blank.
     */
    public String readCommand() {
        return sc.nextLine().trim();
    }

    /**
     * Displays an error message in a formatted way.
     *
     * @param exception the error message to display.
     */
    public void showError(String exception) {
        showLine();
        System.out.println(" " + exception);
        showLine();
    }

    /**
     * Displays the goodbye message when the user exits.
     * <p>Prints a farewell message and closes the Scanner to release system resources.</p>
     */
    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
        sc.close();
    }

    /**
     * Displays all tasks occurring on a specific date.
     *
     * @param matches the list of matching tasks. If no tasks are found,
     *                a friendly message is shown instead.
     * @param date the target date to display.
     */
    public void showTasksOnDate(List<Task> matches, LocalDate date) {
        showLine();
        String formatted = date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        if (matches.isEmpty()) {
            System.out.println(" No tasks found on " + formatted);
        } else {
            System.out.println(" Tasks on " + formatted + ":");
            for (Task t : matches) {
                System.out.println("  " + t.toString());
            }
        }
        showLine();
    }

    /**
     * Confirms the deletion of a task.
     *
     * @param task the task that was deleted.
     * @param total the number of tasks remaining in the list.
     */
    public void showDelete(Task task, int total) {
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("  " + task.toString());
        System.out.println(" Now you have " + total + " tasks in the list");
        showLine();
    }

    /**
     * Confirms the addition of a new Task.
     *
     * @param task the task that was added.
     * @param total the total number of tasks in the list after addition.
     */
    public void showAdd(Task task, int total) {
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println(task.toString());
        System.out.println(" Now you have " + total + " tasks in the list");
        showLine();
    }

    /**
     * Displays all tasks in the current list with their index and status.
     * <p>If the list is empty, a message is shown indicating no tasks exist.
     * Each task is printed with its ID (1-based) and string representation.</p>
     *
     * @param tasks the list of tasks to display.
     */
    public void showList(List<Task> tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println(" Oops! Your task list is empty!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i+1 + ". " + tasks.get(i).toString());
            }
        }
        showLine();
    }

    /**
     * Confirms that a task has been marked as done.
     *
     * @param task the task that was marked as done.
     */
    public void showMarked(Task task) {
        showLine();
        System.out.println( " Nice! I've marked this task as done:\n"+ task.toString());
        showLine();
    }

    /**
     * Confirms that a task has been marked as not done.
     *
     * @param task the task that was marked as not done.
     */
    public void showUnmarked(Task task) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet:\n" + task.toString());
        showLine();
    }

    /**
     * Displays an error message when loading tasks fails.
     *
     * @param error the error message to display.
     */
    public void showLoadingError(String error) {
        showLine();
        System.out.print("Error loading: ");
        showError(error);
        System.out.println("Starting with empty task list.");
        showLine();
    }

    /**
     * Displays a formatted list of all available commands to the user.
     * <p>This method prints a help menu that explains the syntax and purpose.</p>
     */
    public void showHelp() {
        showLine();
        System.out.println(" Available commands:");
        System.out.println("  todo <desc>                  – Add a todo");
        System.out.println("  deadline <desc> /by <time>   – Add a deadline");
        System.out.println("  event <desc> /from <start>   – Add an event\n  /to <end>");
        System.out.println("  list                         – Show all tasks");
        System.out.println("  mark <index>                 – Mark task as done");
        System.out.println("  unmark <index>               – Mark task as not done");
        System.out.println("  help                         – Show this message");
        System.out.println("  bye                          – Exit");
        showLine();
    }

}
