public class AddCommand extends Command {
    private Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws SomException {
        tasks.add(task);
        ui.showAdd(task, tasks.size());
        Storage.save(tasks.getAllTasks());
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
