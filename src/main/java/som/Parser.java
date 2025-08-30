package som;

import som.command.AddCommand;
import som.command.Command;
import som.command.DeleteCommand;
import som.command.ExitCommand;
import som.command.FindCommand;
import som.command.HelpCommand;
import som.command.ListCommand;
import som.command.MarkCommand;
import som.command.UnmarkCommand;

import som.task.Deadline;
import som.task.Event;
import som.task.Todo;

public class Parser {
    /**
     * Checks if input is a task-adding command.
     *
     * @param input the original user input
     * @return true if input is a valid task, else returns false
     */
    public static boolean isTaskCommand(String input) {
        String cmd = input.trim().split(" ", 2)[0].toLowerCase();
        return cmd.equals("todo") || cmd.equals("deadline") || cmd.equals("event");
    }

    public static Command parse(String fullCommand) throws SomException {
        String[] parts = fullCommand.trim().split(" ", 2);
        if (parts[0].isEmpty()) {
            throw new SomException("The command is empty!");
        }

        String command = parts[0].toLowerCase();
        String fullDesc = parts.length > 1 ? parts[1] : "";

        switch (command) {
        case "bye": {
            return new ExitCommand();
        }
        case "list": {
            return new ListCommand();
        }
        case "help": {
            return new HelpCommand();
        }
        case "todo": {
            return new AddCommand(new Todo(fullDesc));
        }
        case "deadline": {
            int byIndex = fullDesc.indexOf("/by ");
            if (byIndex == -1) {
                throw new SomException("Oops! A deadline task must include a task and '/by'. Example: deadline return book " +
                        "/by 2025-03-10 1300");
            }
            String desc = fullDesc.substring(0, byIndex).trim();
            String deadline = fullDesc.substring(byIndex + 4).trim(); // +4 to skip "/by "
            if (desc.isEmpty()) {
                throw new SomException("Oops! The description of a deadline cannot be empty.");
            }
            if (deadline.isEmpty()) {
                throw new SomException("Oops! The '/by' part cannot be empty. Please specify when it's due.");
            }
            return new AddCommand(new Deadline(desc, deadline));
        } case "event": {
            int fromIndex = fullDesc.indexOf("/from ");
            int toIndex = fullDesc.indexOf("/to ");
            if (fromIndex == -1) {
                throw new SomException("Oops! An event must include '/from' to specify start time \n and '/to' to specify end time" +
                        "\nExample: event birthday /from 2025-03-11 0000 /to 2025-03-11 2359");
            }
            if (toIndex == -1) {
                throw new SomException("Oops! An event must include '/to' to specify end time.");
            }
            if (fromIndex >= toIndex) {
                throw new SomException("Oops! '/from' must come before '/to' in the input.");
            }
            String from = fullDesc.substring(fromIndex + 6, toIndex).trim(); // +6 to skip "/from "
            String to = fullDesc.substring(toIndex + 4).trim(); // +6 to skip "/to "
            String desc = fullDesc.substring(0, fromIndex).trim();
            if (desc.isEmpty()) {
                throw new SomException("Oops! The description of an event cannot be empty.");
            }
            if (from.isEmpty()) {
                throw new SomException("Oops! The '/from' part cannot be empty. Please specify a start time.");
            }
            if (to.isEmpty()) {
                throw new SomException("Oops! The '/to' part cannot be empty. Please specify an end time.");
            }
            return new AddCommand(new Event(desc, from, to));
        }
        case "mark": {
            int index = parseIndex(fullCommand) - 1;
            return new MarkCommand(index);
        }
        case "unmark": {
            int index = parseIndex(fullCommand) - 1;
            return new UnmarkCommand(index);
        }
        case "delete": {
            int index = parseIndex(fullCommand) - 1;
            return new DeleteCommand(index);
        }
        case "find": {
            if (fullDesc.isEmpty()) {
                throw new SomException("Please enter a keyword to search for.");
            }
            return new FindCommand(fullDesc);
        }
        default: {
            throw new SomException("I don't know what '" + fullCommand + "' means. Type 'help' to see what I can do.");
        }




        }

    }

    public static int parseIndex(String input) throws SomException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 ) {
            throw new SomException("Please specify a task number. Example: " + parts[0] + " 1");
        }

        try {
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new SomException("Task number must be a valid number.");
        }
    }


}
