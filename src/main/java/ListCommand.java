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
