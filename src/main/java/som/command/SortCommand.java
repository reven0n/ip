package som.command;

import som.TaskList;
import som.Ui;

public class SortCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui) {
        tasks.sortTasks();
        return ui.showSortedMessage();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
