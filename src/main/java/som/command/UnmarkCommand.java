package som.command;

import som.SomException;
import som.Storage;
import som.TaskList;
import som.Ui;

public class UnmarkCommand extends Command {
    private int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws SomException {
        tasks.unmark(index);
        ui.showUnmarked(tasks.get(index));
        Storage.save(tasks.getAllTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
