package som;

import som.command.Command;

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
     * Creates a new Som Chatbot instance.
     */
    public Som() {
        this.ui = new Ui();
        // Load tasks on startup
        try {
            tasks = new TaskList(Storage.load());
            System.out.println("Loaded " + tasks.size() + " tasks.");
        } catch (SomException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Starts the main interaction loop of the chatbot.
     *
     * <p>Reads user input line by line, processes commands, and responds accordingly.
     * The loop continues until the user types {@code bye}.
     * All exceptions are caught and displayed in a user-friendly format. </p>
     */
    public void run() {
        //starting code
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui);
                isExit = c.isExit();
            } catch (SomException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Something went wrong: " + e.getMessage());
            }
        }
    }
}
