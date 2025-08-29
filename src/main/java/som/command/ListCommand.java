package som.command;

import som.SomException;
import som.TaskList;
import som.Ui;

public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui) throws SomException {
        ui.showList(tasks.getAllTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
