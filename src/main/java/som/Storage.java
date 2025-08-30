package som;

import som.task.Deadline;
import som.task.Event;
import som.task.Task;
import som.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Manages persistent storage of tasks to and from a file on disk.
 * <p>This class handles saving the current task list to a file and loading it back
 * when the application starts.</p>
 * <p>The data file is stored at ./data/som.txt (relative to the project root).
 * Corrupted lines in the file are skipped with a warning message.
 * All file operations use OS-independent paths via java.nio.file.Paths.</p>
 *
 * @author Darien Tan
 */
public class Storage {
    private static final String APP_DIR = "data";
    private static final String FILE_NAME = "som.txt";
    private static final Path FILE_PATH = Paths.get(APP_DIR, FILE_NAME);

    /**
     * Loads the list of tasks from the data file.
     * <p>If the file does not exist, throws SomException with "File not found!".
     * Corrupted lines are skipped, and a warning is printed to the console.
     * Requires all tasks to be of a fixed pre-determined input.</p>
     *
     * @return a list of loaded tasks.
     * @throws SomException if the file exists but cannot be found during scanning.
     */
    public static List<Task> load() throws SomException {
        List<Task> tasks = new ArrayList<>();
        File file = FILE_PATH.toFile();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String line = "";
                try {
                    line = sc.nextLine().trim();
                    tasks.add(parseTask(line));
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping corrupt task: " + line + "\nError: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            throw new SomException("File not found!");
        }
        return tasks;
    }

    /**
     * Parses a single line into a Task.
     * <p>Format: TYPE | DONE | ARGS...
     * Example: T | 1 | read book</p>
     *
     * @param line the raw line from the file to parse.
     * @return the parsed Task object.
     * @throws SomException if the line is malformed or contains invalid data.
     */
    private static Task parseTask(String line) throws SomException {
        String[] parts = line.split(" \\| ", -1); // Keep trailing empty strings
        if (parts.length < 3) {
            throw new IllegalArgumentException("Too few parts");
        }

        String type = parts[0].trim();
        boolean isDone = "1".equals(parts[1].trim());

        return switch (type) {
            case "T":
                if (parts.length < 3 || parts[2].isEmpty()) {
                    throw new IllegalArgumentException("Missing Description");
                }
                Task t = new Todo(parts[2]);
                if (isDone) {
                    t.markAsDone();
                }
                yield t;
            case "D":
                if (parts.length < 4 || parts[2].isEmpty()) {
                    throw new IllegalArgumentException("Missing Description");
                }
                if (parts.length < 4 || parts[3].isEmpty()) {
                    throw new IllegalArgumentException("Missing /by");
                }
                Task d = new Deadline(parts[2], parts[3]);
                if (isDone) {
                    d.markAsDone();
                }
                yield d;
            case "E":
                if (parts.length < 5 || parts[2].isEmpty())
                    throw new IllegalArgumentException("Missing description");
                if (parts.length < 5 || parts[3].isEmpty())
                    throw new IllegalArgumentException("Missing /from");
                if (parts.length < 5 || parts[4].isEmpty())
                    throw new IllegalArgumentException("Missing /to");
                Event e = new Event(parts[2], parts[3], parts[4]);
                if (isDone) {
                    e.markAsDone();
                }
                yield e;
            default:
                throw new IllegalArgumentException("Unknown task type");
        };
    }

    /**
     * Saves the current list of tasks to the data file.
     * <p>The file is overwritten completely to reflect the current state of the task list.
     * This ensures that deleted or updated tasks are not left in stale form.</p>
     *
     * @param tasks the list of tasks to save.
     * @throws SomException if an I/O error occurs during writing.
     */
    public static void save(List<Task> tasks) throws SomException {
        try {
            FileWriter fw = new FileWriter(FILE_PATH.toFile());
            for (Task task : tasks) {
                fw.write(task.encode());
                fw.write(System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new SomException("Error saving task: " + e.getMessage());
        }
    }


}
