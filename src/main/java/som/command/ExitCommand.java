package som.command;

import som.SomException;
import som.TaskList;
import som.Ui;

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
