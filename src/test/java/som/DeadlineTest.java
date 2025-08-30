package som;

import org.junit.jupiter.api.Test;
import som.task.Deadline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeadlineTest {

    @Test
    void createdDeadlineWithCorrectDateTime() throws SomException {
        //Arrange & Act
        Deadline deadline = new Deadline("Submit report", "2025-03-11 2359");

        // Assert
        assertEquals(2025, deadline.getby().getYear());
        assertEquals(3, deadline.getby().getMonthValue());
        assertEquals(11, deadline.getby().getDayOfMonth());
        assertEquals(23, deadline.getby().getHour());
        assertEquals(59, deadline.getby().getMinute());
    }


    @Test
    void invalidDateFormatThrowsSomException() {
        // Assert
        assertThrows(SomException.class, () ->
                new Deadline("Invalid date", "2025/03/11 23:59")
        );
    }
}
