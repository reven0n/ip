package som.command;

import som.SomException;
import som.TaskList;
import som.Ui;

public class FindCommand extends Command {
    private String dateStr;

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
