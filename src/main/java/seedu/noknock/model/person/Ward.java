package seedu.noknock.model.person;

import static seedu.noknock.commons.util.StringUtil.removeSpaces;

/**
 * Represents a hospital ward assigned to a person.
 * <p>
 * A ward is identified by a room number that must be a positive integer followed by an alphabet (e.g., "2A").
 * This class provides validation for the ward format and stores the ward information for a person.
 *
 */
public class Ward {
    public static final String REGEX_PATTERN = "[1-9]\\d*[a-zA-Z]";
    public static final String MESSAGE_CONSTRAINTS = "Ward number must be a positive integer "
        + "followed by an alphabet. E.g. 2A";

    private final String room;

    /**
     * Returns a Ward object
     * @param room
     */
    public Ward(String room) {
        this.room = removeSpaces(room).toUpperCase();
    }

    public static boolean isValidWard(String ward) {
        return ward.matches(REGEX_PATTERN);
    }

    public String getRoom() {
        return room;
    }

    public String toString() {
        return room;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Ward)) {
            return false;
        }
        Ward otherWard = (Ward) other;
        return room.equals(otherWard.getRoom());
    }
}
