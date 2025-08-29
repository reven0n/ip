import java.util.List;
import java.time.LocalDate;

/**
 * The main chatbot class that handles user interaction, task management, and persistence.
 * This class reads user input, parses commands, manages a list of tasks.
 *
 * @author Darien Tan
 */
public class Som {
    private Ui ui;
    private TaskList tasks;

    public static void main(String[] args) {
        new Som().run();
    }

    /**
     * Creates a new Som Chatbot.
     */
    public Som() {
        this.ui = new Ui();
        // Load tasks on startup
        try {
            tasks = new TaskList(Storage.load());
            System.out.println("Loaded " + tasks.size() + " tasks.");
        } catch (SomException e) {
            System.out.print("Error loading: ");
            ui.showError(e.getMessage());
            System.out.println("Starting with empty task list.");
            tasks = new TaskList();
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
        ui.showWelcome();
        while (true) {
            try {
                String input = ui.readCommand();
                if (input.isEmpty()) {
                    continue;
                }
                if (input.equals("bye")) {
                    ui.showGoodbye();
                    break;
                } else if (input.equals("help")) {
                    ui.showHelp();
                } else if (input.equals("list")) {
                    ui.showList(tasks.getAllTasks());
                } else if (input.startsWith("mark")) {
                    handleMarking(input, true);
                } else if (input.startsWith("unmark")) {
                    handleMarking(input, false);
                } else if (Parser.isTaskCommand(input)) {
                    Task t = Parser.parseTask(input);
                    tasks.add(t);
                    ui.showAdd(t, tasks.size());
                } else if (input.startsWith("delete")) {
                    handleDelete(input);
                } else if (input.startsWith("find")) {
                    handleFind(input);
                } else {
                    throw new SomException("I don't know what '" + input + "' means. Type 'help' to see what I can do.");
                }

            } catch (SomException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Something went wrong: " + e.getMessage());
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
        try {
            int index = Parser.parseIndex(input);
            Task removed = tasks.remove(index);
            ui.showDelete(removed, tasks.size());
            Storage.save(tasks.getAllTasks());
        } catch (IndexOutOfBoundsException e) {
            throw new SomException("No tasks found with that number.");
        }
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
    private void handleMarking(String input, boolean isMark) throws SomException {
        try {
            int index = Parser.parseIndex(input);
            if (isMark) {
                tasks.mark(index);
                ui.showMarked(tasks.get(index));
            } else {
                tasks.unmark(index);
                ui.showUnmarked(tasks.get(index));
            }
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
        String dateStr = Parser.parseDate(input);
        LocalDate target = LocalDate.parse(dateStr);
        List<Task> matches = tasks.findTasks(input);
        ui.showTasksOnDate(matches, target);
    }
}
