package som.command;

import som.SomException;
import som.TaskList;
import som.Ui;
import som.task.Task;

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui) throws SomException;

    public abstract boolean isExit();
}
