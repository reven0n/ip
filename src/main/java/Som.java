import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * The main chatbot class that handles user interaction, task management, and persistence.
 * This class reads user input, parses commands, manages a list of tasks.
 *
 * @author Darien Tan
 */
public class Som {
    private List<Task> list;
    private Scanner sc;
    private final String LINE = "____________________________________________________________";

    public static void main(String[] args) {
        new Som().run();
    }

    /**
     * Creates a new Som Chatbot.
     */
    public Som() {
        this.list = new ArrayList<>();
        this.sc = new Scanner(System.in);
        // Load tasks on startup
        try {
            this.list = Storage.load();
            System.out.println("Loaded " + list.size() + " tasks.");
        } catch (SomException e) {
            System.out.println("Error loading: " + e.getMessage());
            System.out.println("Starting with empty task list.");
        }
    }

    /**
     * Starts the main interaction loop of the chatbot.
     * <p>
     * Reads user input line by line, processes commands, and responds accordingly.
     * The loop continues until the user types {@code bye}.
     * All exceptions are caught and displayed in a user-friendly format.
     */
    public void run() {
        //starting code
        System.out.println(LINE);
        System.out.println(" Hello! I'm Som\n What can I do for you?");
        System.out.println(" Type 'help' for a list of commands.");
        System.out.println(LINE);
        while (true) {
            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    continue;
                }

                if (input.equals("bye")) {
                    sc.close(); // Close scanner
                    System.out.println(LINE + "\n Bye. Hope to see you again soon!");
                    System.out.println(LINE);
                    break;
                } else if (input.equals("help")) {
                    showHelp();
                }

                else if (input.equals("list")) {
                    handleList(list);
                } else if (input.startsWith("mark") || input.startsWith("unmark")) {
                    handleMarking(input);
                } else if (input.startsWith("todo") || input.startsWith("deadline") || input.startsWith("event")) {
                    Task t = getTask(input);
                    list.add(t);
                    System.out.println(LINE + "\nGot it. I've added this task:");
                    System.out.println(t.toString());
                    System.out.println("Now you have " + list.size() + " tasks in the list\n" + LINE);
                } else if (input.startsWith("delete")) {
                    handleDelete(input);
                } else if (input.startsWith("find")) {
                    handleFind(input);
                } else {
                    throw new SomException("I don't know what '" + input + "' means. Type 'help' to see what I can do.");
                }
                Storage.save(list); // tasks are updated after every action

            } catch (SomException e) {
                System.out.println(LINE);
                System.out.println(" " + e.getMessage());
                System.out.println(LINE);
            } catch (Exception e) {
                System.out.println("Something went wrong :/ \nError: " + e.getMessage());
                System.out.println(LINE);
            }
        }
    }

    /**
     * Handles deleting a task from the list.
     * <p>
     * Parses the task number from input like {@code delete 1}.
     * Validates input and removes the task. Throws {@link SomException}
     * for invalid input or out-of-bounds indices.
     *
     * @param   input the full user input (e.g., "delete 1")
     * @throws  SomException if input is invalid or task number is out of range
     */
    private void handleDelete(String input) throws SomException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new SomException("Please enter a task number: ");
        }
        try {
            System.out.println(LINE);
            int taskNo = Integer.parseInt(parts[1]) - 1;
            Task task = list.get(taskNo);
            list.remove(taskNo);
            System.out.println(" Noted. I've removed this task:");
            System.out.println("  " + task.toString());
            System.out.println(" Now you have " + list.size() + " tasks in the list");
            System.out.println(LINE);
        } catch (IndexOutOfBoundsException e) {
            throw new SomException("No tasks found with that number.");
        } catch (NumberFormatException e) {
            throw new SomException("Task number must be a valid number.");
        }
    }

    /**
     * Displays all tasks in the current list with their index and status.
     * <p>
     * If the list is empty, a message is shown indicating no tasks exist.
     * Each task is printed with its ID (1-based) and string representation.
     *
     * @param list the list of tasks to display
     */
    private void handleList(List<Task> list) {
        System.out.println(LINE);
        if (list.isEmpty()) {
            System.out.println("Oops! Your task list is empty!");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i+1 + ". " + list.get(i).toString());
            }
        }
        System.out.println(LINE);
    }

    /**
     * Handles marking a task as done or undone based on user input.
     * <p>
     * Parses the task number from input like {@code mark 1} or {@code unmark 2}.
     * Validates input and updates the task status. Throws {@link SomException}
     * for invalid input or out-of-bounds indices.
     *
     * @param   input the full user input (e.g., "mark 1")
     * @throws  SomException if input is invalid or task number is out of range
     */
    private void handleMarking(String input) throws SomException {
        String[] noParts = input.split(" ", 2);
        if (noParts.length < 2 ) {
            throw new SomException("Please specify a task number. Example: mark 1 / unmark 1");
        }
        try {
            int index = Integer.parseInt(noParts[1]) - 1;
            Task task = list.get(index);
            if (input.startsWith("mark")) {
                task.markAsDone();
                System.out.println(LINE + "\n Nice! I've marked this task as done:\n"+ task.toString());
            }  else if  (input.startsWith("unmark")) {
                task.markAsUndone();
                System.out.println(LINE + "\n OK, I've marked this task as not done yet:\n" + task.toString());
            }
            System.out.println(LINE);
        } catch (NumberFormatException e) {
            throw new SomException("Please specify a task number. Example: " + noParts[0] + " 1");
        } catch (IndexOutOfBoundsException e) {
            throw new SomException("No tasks found with that number.");
        }
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
     */
    private void handleFind(String input) throws SomException {
        String dateStr = input.substring(5).trim();
        try {
            LocalDate targetDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<Task> matches = new ArrayList<>();
            for (Task t : list) {
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
            System.out.println(LINE);
            if (matches.isEmpty()) {
                System.out.println(" No tasks found on " + targetDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
            } else {
                System.out.println(" Tasks on " + targetDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
                for  (Task t : matches) {
                    System.out.println(t.toString());
                }
            }
            System.out.println(LINE);
        } catch (DateTimeParseException e) {
            throw new SomException("Please enter a valid date. Format: yyyy-MM-dd (e.g., 2025-03-11)");
        }
    }

    /**
     * This method returns a Task based on the user input
     *
     * @param   input the original user input
     * @return the correct Task type requested by the user
     * @throws  SomException if input is invalid or missing required parts
     */
    private Task getTask(String input) throws SomException {
        if (input == null || input.trim().isEmpty()) {
            throw new SomException("Oops! Input cannot be empty.");
        }

        String[] parts = input.split(" ", 2);
        String task = parts[0].toLowerCase();
        String fullDesc = parts.length > 1 ? parts[1].trim() : "";

        switch (task) {
            case "todo" -> { // case 1: todo
                if (fullDesc.isEmpty()) {
                    throw new SomException("Oops! A todo needs a description. Try: todo <description>");
                }
                return new Todo(fullDesc);
            }
            case "deadline" -> { // case 2: deadline
                int byIndex = fullDesc.indexOf("/by ");
                if (byIndex == -1) {
                    throw new SomException("Oops! A deadline task must include a task and '/by'. Example: deadline return book " +
                            "/by 2025-03-10 1300");
                }
                String desc = fullDesc.substring(0, byIndex).trim();
                String deadline = fullDesc.substring(byIndex + 4).trim(); // +4 to skip "/by "
                if (desc.isEmpty()) {
                    throw new SomException("Oops! The description of a deadline cannot be empty.");
                }
                if (deadline.isEmpty()) {
                    throw new SomException("Oops! The '/by' part cannot be empty. Please specify when it's due.");
                }
                return new Deadline(desc, deadline);
            }
            case "event" -> { // case 3: event
                int fromIndex = fullDesc.indexOf("/from ");
                int toIndex = fullDesc.indexOf("/to ");
                if (fromIndex == -1) {
                    throw new SomException("Oops! An event must include '/from' to specify start time \n and '/to' to specify end time" +
                            "\nExample: event birthday /from 2025-03-11 0000 /to 2025-03-11 2359");
                }
                if (toIndex == -1) {
                    throw new SomException("Oops! An event must include '/to' to specify end time.");
                }
                if (fromIndex >= toIndex) {
                    throw new SomException("Oops! '/from' must come before '/to' in the input.");
                }
                String from = fullDesc.substring(fromIndex + 6, toIndex).trim(); // +6 to skip "/from "
                String to = fullDesc.substring(toIndex + 4).trim(); // +6 to skip "/to "
                String desc = fullDesc.substring(0, fromIndex).trim();
                if (desc.isEmpty()) {
                    throw new SomException("Oops! The description of an event cannot be empty.");
                }
                if (from.isEmpty()) {
                    throw new SomException("Oops! The '/from' part cannot be empty. Please specify a start time.");
                }
                if (to.isEmpty()) {
                    throw new SomException("Oops! The '/to' part cannot be empty. Please specify an end time.");
                }
                return new Event(desc, from, to);
            }
            default -> { // unknown task is given
                throw new SomException("Unknown command: " + task);
            }
        }
    }

    /**
     * Displays a formatted list of all available commands to the user.
     * <p>
     * This method prints a help menu that explains the syntax and purpose
     */
    private void showHelp() {
        System.out.println(LINE);
        System.out.println(" Available commands:");
        System.out.println("  todo <desc>                  – Add a todo");
        System.out.println("  deadline <desc> /by <time>   – Add a deadline");
        System.out.println("  event <desc> /from <start>   – Add an event\n  /to <end>");
        System.out.println("  list                         – Show all tasks");
        System.out.println("  mark <index>                 – Mark task as done");
        System.out.println("  unmark <index>               – Mark task as not done");
        System.out.println("  help                         – Show this message");
        System.out.println("  bye                          – Exit");
        System.out.println(LINE);
    }


}
