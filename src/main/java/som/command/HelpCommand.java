package som.command;

import som.SomException;
import som.TaskList;
import som.Ui;

public class HelpCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui) throws SomException {
        ui.showHelp();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
