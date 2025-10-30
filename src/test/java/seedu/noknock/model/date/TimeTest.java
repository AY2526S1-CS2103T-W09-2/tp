package seedu.noknock.model.date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.testutil.Assert.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TimeTest {

    @Test
    public void constructorValidTest() {
        // 24-hour format
        Time time24 = new Time("14:30");
        assertEquals(LocalTime.of(14, 30), time24.value);

        // 12-hour format with am/pm
        Time time12 = new Time("2:30pm");
        assertEquals(LocalTime.of(14, 30), time12.value);

        Time time12am = new Time("2:30am");
        assertEquals(LocalTime.of(2, 30), time12am.value);
    }

    @Test
    public void constructorInvalidTest() {
        // null should throw NPE
        assertThrows(NullPointerException.class, () -> new Time(null));

        // invalid strings should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Time("25:00"));
        assertThrows(IllegalArgumentException.class, () -> new Time("12:60"));
        assertThrows(IllegalArgumentException.class, () -> new Time("14-30"));
        assertThrows(IllegalArgumentException.class, () -> new Time("2:30 xm"));
    }

    @Test
    public void parseTest() {
        // valid 24-hour
        assertEquals(LocalTime.of(14, 30), Time.parse("14:30"));

        // valid 12-hour
        assertEquals(LocalTime.of(14, 30), Time.parse("2:30pm"));
        assertEquals(LocalTime.of(2, 30), Time.parse("2:30am"));

        // invalid formats
        assertNull(Time.parse("24:00"));
        assertNull(Time.parse("12:60"));
        assertNull(Time.parse("abc"));
        assertNull(Time.parse("12:30 xm"));
    }

    @Test
    public void isValidTimeStringTest() {
        assertTrue(Time.isValidTime("14:30"));
        assertTrue(Time.isValidTime("2:30pm"));
        assertTrue(Time.isValidTime("2:30am"));

        assertFalse(Time.isValidTime("25:00"));
        assertFalse(Time.isValidTime("12:60"));
        assertFalse(Time.isValidTime("2:30 xm"));
        assertFalse(Time.isValidTime((String) null));
    }

    @Test
    public void equalsTest() {
        Time t1 = new Time("14:30");
        Time t2 = new Time("2:30pm");
        Time t3 = new Time("15:00");

        // same object -> true
        assertTrue(t1.equals(t1));

        // same value -> true
        assertTrue(t1.equals(t2));

        // null -> false
        assertFalse(t1.equals(null));

        // different type -> false
        assertFalse(t1.equals("14:30"));

        // different value -> false
        assertFalse(t1.equals(t3));
    }

    @Test
    public void toStringTest() {
        Time time = new Time("14:30");
        assertEquals("14:30", time.toString());
    }

    @Test
    public void hashCodeTest() {
        Time t1 = new Time("14:30");
        Time t2 = new Time("2:30pm");
        assertEquals(t1.hashCode(), t2.hashCode());
    }
}
