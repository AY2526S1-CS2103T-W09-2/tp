package seedu.noknock.model.session;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.commons.util.AppUtil.checkArgument;

/**
 * Represents a Care Type in the session.
 * Guarantees: immutable; is valid as declared in {@link #isValidCareType(String)}
 */
public class CareType {

    public static final String MESSAGE_CONSTRAINTS =
        "Care type must be between 1 and 50 characters";

    public static final String VALIDATION_REGEX = "^.{1,50}$";

    public final String value;

    /**
     * Constructs a {@code CareType}.
     *
     * @param careType A valid care type.
     */
    public CareType(String careType) {
        requireNonNull(careType);
        checkArgument(isValidCareType(careType), MESSAGE_CONSTRAINTS);
        value = careType;
    }

    /**
     * Returns true if a given string is a valid care type.
     */
    public static boolean isValidCareType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CareType otherCareType)) {
            return false;
        }
        return value.equals(otherCareType.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
