package seedu.noknock.model.date;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a Time in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(LocalTime)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS =
        "Times must be in HH:MM (24-hour) or HH:MMam/pm (12-hour) format";

    public final LocalTime value;

    /**
     * Constructs a {@code Time}.
     *
     * @param timeStr A valid time string.
     */
    public Time(String timeStr) {
        requireNonNull(timeStr);
        LocalTime parsed = parse(timeStr);
        checkArgument(isValidTime(parsed), MESSAGE_CONSTRAINTS);
        value = parsed;
    }

    /**
     * Returns true if a given local time is valid (not null).
     */
    public static boolean isValidTime(LocalTime test) {
        return test != null;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return parse(test) != null;
    }

    /**
     * Parses a time string into a LocalTime object.
     * Accepts times in the format HH:MM (24-hour) or HH:MMam/pm (12-hour).
     *
     * @param input The time string to parse.
     * @return A LocalTime object if parsing is successful; null otherwise.
     */
    public static LocalTime parse(String input) {
        if (input == null) {
            return null;
        }

        if (input.matches("\\d{1,2}:\\d{2}")) {
            try {
                return LocalTime.parse(input);
            } catch (DateTimeParseException ignored) {
                return null;
            }
        }

        String formattedInput = input.replaceAll("\\s", "").toLowerCase();
        if (formattedInput.matches("\\d{1,2}:\\d{2}(am|pm)")) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);
            try {
                return LocalTime.parse(formattedInput.toUpperCase(Locale.ROOT), fmt);
            } catch (DateTimeParseException ignored) {
                return null;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Time otherTime)) {
            return false;
        }
        return value.equals(otherTime.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
