import java.util.Scanner;

public class Som {
    public static void main(String[] args) {
        /*
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
         */
        String line = "____________________________________________________________";
        System.out.println(line);
        System.out.println(" Hello! I'm Som\n What can I do for you?");
        System.out.println(line);

        Scanner sc = new Scanner(System.in); // Create a new scanner
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            System.out.println(line + "\n " + input + "\n" + line);
            //System.out.println(line);
            input = sc.nextLine();
        }


        System.out.println(line + "\n Bye. Hope to see you again soon!");
        System.out.println(line);
    }
}
