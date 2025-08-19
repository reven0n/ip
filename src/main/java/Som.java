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
        List<String> list = new ArrayList<>(); // Stores user inputs
        while (!input.equals("bye")) { 
            if (input.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(i+1 + ". " + list.get(i));
                }
                System.out.println(line);
                input = sc.nextLine();
                continue;
            }
            list.add(input);
            System.out.println(line + "\n added: " + input + "\n" + line);
            input = sc.nextLine();
        }

        sc.close(); // Close scanner
        System.out.println(line + "\n Bye. Hope to see you again soon!");
        System.out.println(line);
    }
}
