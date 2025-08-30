package som.command;

import som.SomException;
import som.Storage;
import som.TaskList;
import som.Ui;
import som.task.Task;

public class DeleteCommand extends Command {

    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws SomException {
        Task removed = tasks.remove(index);
        ui.showDelete(removed, tasks.size());
        Storage.save(tasks.getAllTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
