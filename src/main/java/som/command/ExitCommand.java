package som.command;

import som.SomException;
import som.TaskList;
import som.Ui;

/**
 * Represents a command to exit the application.
 * <p>When executed, this command displays a goodbye message and terminates the app.</p>
 *
 * @author Darien Tan
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui) throws SomException {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
