package seedu.noknock.model.session;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents the status of a session.
 * Guarantees: immutable; is valid as declared in {@link #isValidSessionStatus(String)}.
 */
public enum SessionStatus {
    COMPLETED("Completed"),
    INCOMPLETE("Incomplete");

    public static final String MESSAGE_CONSTRAINTS =
        "Session status must be one of: "
            + Arrays.stream(values())
            .map(SessionStatus::toString)
            .collect(Collectors.joining(", "))
            + " (case-insensitive)";

    private final String displayValue;

    SessionStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    /**
     * Returns true if a given string is a valid session status.
     */
    public static boolean isValidSessionStatus(String test) {
        if (test == null) {
            return false;
        }
        try {
            fromString(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Converts a string to a SessionStatus enum value (case-insensitive).
     */
    public static SessionStatus fromString(String status) {
        requireNonNull(status);
        String normalized = status.toUpperCase().trim();
        try {
            return SessionStatus.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Creates a SessionStatus with validation.
     */
    public static SessionStatus of(String status) {
        requireNonNull(status);
        checkArgument(isValidSessionStatus(status), MESSAGE_CONSTRAINTS);
        return fromString(status);
    }

    @Override
    public String toString() {
        return displayValue;
    }
}
