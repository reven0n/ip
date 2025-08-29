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

    /**
     * This method returns a Task based on the user input
     *
     * @param input the original user input
     * @return the correct Task type requested by the user
     * @throws SomException if input is invalid or missing required parts
     */
    public static Task parseTask(String input) throws SomException {
        if (input == null || input.trim().isEmpty()) {
            throw new SomException("Oops! Input cannot be empty.");
        }

        String[] parts = input.split(" ", 2);
        String task = parts[0].toLowerCase();
        String fullDesc = parts.length > 1 ? parts[1].trim() : "";

        switch (task) {
        case "todo" -> { // case 1: todo
            if (fullDesc.isEmpty()) {
                throw new SomException("Oops! A todo needs a description. Try: todo <description>");
            }
            return new Todo(fullDesc);
        }
        case "deadline" -> { // case 2: deadline
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
            return new Deadline(desc, deadline);
        }
        case "event" -> { // case 3: event
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
            return new Event(desc, from, to);
        }
        default -> { // unknown task is given
            throw new SomException("Unknown command: " + task);
        }
        }
    }

    public static int parseIndex(String input) throws SomException {
        String[] noParts = input.split(" ", 2);
        if (noParts.length < 2 ) {
            throw new SomException("Please specify a task number. Example: " + noParts[0] + " 1");
        }

        try {
            return Integer.parseInt(noParts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new SomException("Task number must be a valid number.");
        }
    }

    public static String parseDate(String input) throws SomException {
        String[] parts = input.trim().split(" ", 2);
        if (parts.length < 2) {
            throw new SomException("Please specify a date. Format: yyyy-MM-dd");
        }
        return parts[1];
    }


}
