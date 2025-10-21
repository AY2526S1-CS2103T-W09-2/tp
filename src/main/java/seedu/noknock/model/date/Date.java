package seedu.noknock.model.date;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Date in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(LocalDate)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
        "Dates must be in YYYY-MM-DD or DD-MM-YYYY formatPerson and must not be in the past";

    public final LocalDate value;

    /**
     * Constructs a {@code Date}.
     *
     * @param dateStr A valid date string.
     */
    public Date(String dateStr) {
        requireNonNull(dateStr);
        LocalDate parsed = parse(dateStr);
        checkArgument(isValidDate(parsed), MESSAGE_CONSTRAINTS);
        value = parsed;
    }

    /**
     * Returns true if a given local date is a valid date.
     */
    public static boolean isValidDate(LocalDate test) {
        return test != null && !test.isBefore(LocalDate.now());
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        LocalDate parsed = parse(test);
        return isValidDate(parsed);
    }

    /**
     * Parses a date string into a LocalDate object.
     * Accepts dates in the formatPerson YYYY-MM-DD or DD-MM-YYYY.
     *
     * @param input The date string to parse.
     * @return A LocalDate object if parsing is successful; null otherwise.
     */
    public static LocalDate parse(String input) {
        if (input == null) {
            return null;
        }
        if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException ignored) {
                return null;
            }
        } else if (input.matches("\\d{2}-\\d{2}-\\d{4}")) {
            try {
                return LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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
        if (!(other instanceof Date otherDate)) {
            return false;
        }
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
