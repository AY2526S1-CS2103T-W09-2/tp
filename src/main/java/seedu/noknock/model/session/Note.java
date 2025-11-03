package seedu.noknock.model.session;

import static seedu.noknock.commons.util.AppUtil.checkArgument;
import static seedu.noknock.commons.util.StringUtil.cleanSpaces;

import java.util.Objects;

/**
 * Represents a Note in the session.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class Note {
    public static final String DEFAULT_NONE = "";
    public static final String MESSAGE_CONSTRAINTS =
        "Notes can be empty or up to 200 characters long";

    public static final String VALIDATION_REGEX = "^.{0,200}$";

    public final String value;

    /**
     * Constructs a {@code Note}.
     *
     * @param note A valid note (can be null or up to 200 characters).
     */
    public Note(String note) {
        String cleanedNote = (note == null) ? "" : cleanSpaces(note);
        checkArgument(isValidNote(cleanedNote), MESSAGE_CONSTRAINTS);
        value = cleanedNote;
    }

    /**
     * Returns true if a given string is a valid note.
     */
    public static boolean isValidNote(String test) {
        return test == null || test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (value.isEmpty()) {
            return DEFAULT_NONE;
        }
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Note otherNote)) {
            return false;
        }
        return Objects.equals(value, otherNote.value);
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }
}
