import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Som {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        System.out.println(line);
        System.out.println(" Hello! I'm Som\n What can I do for you?");
        System.out.println(line);

        Scanner sc = new Scanner(System.in); // Create a new scanner
        String input = sc.nextLine();
        List<Task> list = new ArrayList<>(); // Stores user inputs

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(i+1 + ". " + list.get(i).toString());
                }

            } else if (input.contains("mark")) {
                int chosenInt = Character.getNumericValue(input.charAt(input.length() - 1));
                Task chosenTask = list.get(chosenInt - 1);
                if (input.contains("un")) {
                    chosenTask.markAsUndone();
                    System.out.println(line + "\n OK, I've marked this task as not done yet: \n" + chosenTask.toString());
                }  else {
                    chosenTask.markAsDone();
                    System.out.println(line + "\n Nice! I've marked this task as done: \n"+ chosenTask.toString());
                }

            } else {
                list.add(new Task(input));
                System.out.println(line + "\n added: " + input);
            }

            System.out.println(line);
            input = sc.nextLine();
        }

        sc.close(); // Close scanner
        System.out.println(line + "\n Bye. Hope to see you again soon!");
        System.out.println(line);
    }
}
