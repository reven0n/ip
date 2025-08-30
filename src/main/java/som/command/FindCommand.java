package som.command;

import som.SomException;
import som.TaskList;
import som.Ui;

public class FindCommand extends Command {
    private String keyword;

    public FindCommand(String dateStr) {
        this.keyword = dateStr;
    }

    public String getKeyword() {
        return this.keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws SomException {
        ui.showFindResults(tasks.findTasks(keyword), keyword);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
