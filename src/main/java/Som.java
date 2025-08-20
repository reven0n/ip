import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Som {
    private List<Task> list;
    private Scanner sc;
    private final String line = "____________________________________________________________";

    public static void main(String[] args) {
        new Som().run();
    }

    public Som() {
        this.list = new ArrayList<>();
        this.sc = new Scanner(System.in);
    }

    public void run() {
        //starting code
        System.out.println(line);
        System.out.println(" Hello! I'm Som\n What can I do for you?");
        System.out.println(" Type 'help' for a list of commands.");
        System.out.println(line);

        while (true) {
            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    continue;
                }

                if (input.equals("bye")) {
                    sc.close(); // Close scanner
                    System.out.println(line + "\n Bye. Hope to see you again soon!");
                    System.out.println(line);
                    break;
                } else if (input.equals("help")) {
                    showHelp();
                }

                else if (input.equals("list")) {
                    handleList(list);
                } else if (input.startsWith("mark") || input.startsWith("unmark")) {
                    handleMarking(input);
                } else if (input.startsWith("todo") || input.startsWith("deadline") || input.startsWith("event")) {
                    Task t = getTask(input);
                    list.add(t);
                    System.out.println(line + "\nGot it. I've added this task:");
                    System.out.println(t.toString());
                    System.out.println("Now you have " + Task.total + " tasks in the list\n" + line);
                } else {
                    throw new SomException("I don't know what '" + input + "' means. Type 'help' to see what I can do.");
                }

            } catch (SomException e) {
                System.out.println(line);
                System.out.println(" " + e.getMessage());
                System.out.println(line);
            } catch (Exception e) {
                System.out.println(line);
                System.out.println("Something went wrong :/ \nError: " + e.getMessage());
            }
        }
    }

    private void handleList(List<Task> list) {
        System.out.println(line);
        if (list.isEmpty()) {
            System.out.println("Oops! Your task list is empty!");
        } else {
            for (Task item : list) {
                System.out.println(item.getID() + ". " + item.toString());
            }
        }
        System.out.println(line);
    }

    private void handleMarking(String input) throws SomException {
        String[] noParts = input.split(" ", 2);
        if (noParts.length < 2 ) {
            throw new SomException("Please specify a task number. Example: " + noParts[0] + "1");
        }
        try {
            int index = Integer.parseInt(noParts[1]) - 1;
            Task task = list.get(index);
            if (input.startsWith("mark")) {
                task.markAsDone();
                System.out.println(line + "\n Nice! I've marked this task as done: \n"+ task.toString());
            }  else if  (input.startsWith("unmark")) {
                task.markAsUndone();
                System.out.println(line + "\n OK, I've marked this task as not done yet: \n" + task.toString());
            }
            System.out.println(line);
        } catch (NumberFormatException e) {
            throw new SomException("Please specify a task number. Example: " + noParts[0] + 1);
        } catch (IndexOutOfBoundsException e) {
            throw new SomException("No tasks found with that number. ");
        }
    }

    /**
     * This method returns a Task based on the user input
     *
     * @param input The original user input
     * @return      The correct Task type requested by the user
     */
    private Task getTask(String input) throws SomException {
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
                    throw new SomException("Oops! A deadline task must include a task and '/by'. Example: deadline return book /by Sunday");
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
                    throw new SomException("Oops! An event must include '/from' to specify start time \n and '/to' to specify end time");
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

    private void showHelp() {
        System.out.println(line);
        System.out.println(" Available commands:");
        System.out.println("  todo <desc>                  – Add a todo");
        System.out.println("  deadline <desc> /by <time>   – Add a deadline");
        System.out.println("  event <desc> /from <start>   – Add an event\n  /to <end>");
        System.out.println("  list                         – Show all tasks");
        System.out.println("  mark <index>                 – Mark task as done");
        System.out.println("  unmark <index>               – Mark task as not done");
        System.out.println("  help                         – Show this message");
        System.out.println("  bye                          – Exit");
        System.out.println(line);
    }


}
