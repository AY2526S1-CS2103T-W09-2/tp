package seedu.noknock.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.commons.util.AppUtil.checkArgument;
import static seedu.noknock.commons.util.StringUtil.removeSpaces;

/**
 * Represents a patient in the system, extending the {@code Person} class.
 * A patient has an identification code (IC), is assigned to a ward,
 * and may have associated tags and a list of next of kin.
 * This class provides methods to access and modify patient-specific information, such as next of kin and tags.
 * Use this class to model and manage patient data within the application.
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        String cleanedTag = removeSpaces(tagName);
        checkArgument(isValidTagName(cleanedTag), MESSAGE_CONSTRAINTS);
        this.tagName = cleanedTag;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
