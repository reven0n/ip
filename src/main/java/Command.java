public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui) throws SomException;

    public abstract boolean isExit();
}
