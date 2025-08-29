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
