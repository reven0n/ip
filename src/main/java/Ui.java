import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;

public class Ui {
    private Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm Som\n What can I do for you?");
        System.out.println(" Type 'help' for a list of commands.");
        showLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public String readCommand() {
        return sc.nextLine().trim();
    }

    public void showError(String exception) {
        showLine();
        System.out.println(" " + exception);
        showLine();
    }

    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
        sc.close();
    }

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

    public void showDelete(Task task, int total) {
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("  " + task.toString());
        System.out.println(" Now you have " + total + " tasks in the list");
        showLine();
    }

    public void showAdd(Task task, int total) {
        showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + total + " tasks in the list");
        showLine();
    }

    /**
     * Displays all tasks in the current list with their index and status.
     * <p>
     * If the list is empty, a message is shown indicating no tasks exist.
     * Each task is printed with its ID (1-based) and string representation.
     *
     * @param tasks the list of tasks to display
     */
    public void showList(List<Task> tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println("Oops! Your task list is empty!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i+1 + ". " + tasks.get(i).toString());
            }
        }
        showLine();
    }

    public void showMarked(Task task) {
        showLine();
        System.out.println( " Nice! I've marked this task as done:\n"+ task.toString());
        showLine();
    }

    public void showUnmarked(Task task) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet:\n" + task.toString());
        showLine();
    }

    /**
     * Displays a formatted list of all available commands to the user.
     * <p>
     * This method prints a help menu that explains the syntax and purpose
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
