import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Som {
    public static void main(String[] args) {
        String line = "____________________________________________________________";

        //starting code
        System.out.println(line);
        System.out.println(" Hello! I'm Som\n What can I do for you?");
        System.out.println(line);

        Scanner sc = new Scanner(System.in); // Create a new scanner
        String input = sc.nextLine();
        List<Task> list = new ArrayList<>(); // Stores user inputs

        while (!input.equals("bye")) {
            String task = input.split(" ")[0];
            if (input.equals("list")) { // Listing out all items
                System.out.println(line);
                for (Task item : list) {
                    System.out.println(item.getID() + ". " + item.toString());
                }

            } else if (task.contains("mark")) { // Marking and Unmarking items
                int chosenInt = Character.getNumericValue(input.charAt(input.length() - 1));
                Task chosenTask = list.get(chosenInt - 1);
                if (task.contains("un")) {
                    chosenTask.markAsUndone();
                    System.out.println(line + "\n OK, I've marked this task as not done yet: \n" + chosenTask.toString());
                }  else {
                    chosenTask.markAsDone();
                    System.out.println(line + "\n Nice! I've marked this task as done: \n"+ chosenTask.toString());
                }

            } else { // Adding items into list
                Task t = getTask(input, task);
                list.add(t);
                System.out.println(line + "\nGot it. I've added this task:");
                System.out.println(t.toString());
                System.out.println("Now you have " + Task.total + " tasks in the list\n" + line);
            }

            System.out.println(line);
            input = sc.nextLine();
        }

        sc.close(); // Close scanner
        System.out.println(line + "\n Bye. Hope to see you again soon!");
        System.out.println(line);
    }

    /**
     * This method returns a Task based on the user input
     *
     * @param input The original user input
     * @param task  The task requested by the user (todo/deadline/event)
     * @return      The correct Task type requested by the user
     */
    private static Task getTask(String input, String task) {
        String fullDesc = input.replace(task, "");
        Task t = new Task();
        switch (task) {
            case "todo" -> { // case 1: todo
                t = new Todo(fullDesc);
            }
            case "deadline" -> { // case 2: deadline
                int byIndex = fullDesc.indexOf("/by ");
                String desc = fullDesc.substring(0, byIndex).trim();
                String deadline = fullDesc.substring(byIndex + 4).trim(); // +4 to skip "/by "
                t = new Deadline(desc, deadline);
            }
            case "event" -> { // case 3: event
                int fromIndex = fullDesc.indexOf("/from ");
                int toIndex = fullDesc.indexOf("/to ");
                String from = fullDesc.substring(fromIndex + 6, toIndex).trim(); // +6 to skip "/from "
                String to = fullDesc.substring(toIndex + 4).trim(); // +6 to skip "/to "
                String desc = fullDesc.substring(0, fromIndex).trim();
                t = new Event(desc, from, to);
            }
            default -> { // if no special task is assigned
                t = new Task(input);
            }
        }

        return t;
    }
}
