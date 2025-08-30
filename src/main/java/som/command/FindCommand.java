package som.command;

import som.SomException;
import som.TaskList;
import som.Ui;

/**
 * Represents a command to find tasks of a specified date.
 * <p>When executed, this command searches the task list case-insensitively
 * and displays matching tasks.</p>
 *
 * @author Darien Tan
 */
public class FindCommand extends Command {
    private String dateStr;

    /**
     * Constructs a FindCommand with the given date.
     *
     * @param dateStr the date to search for; must not be null.
     */
    public FindCommand(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getDateStr() {
        return this.dateStr;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws SomException {
        ui.showTasksOnDate(tasks.findTasks(dateStr), java.time.LocalDate.parse(dateStr));
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
