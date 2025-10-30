
package seedu.noknock.model.person;

import static seedu.noknock.commons.util.StringUtil.removeSpaces;

/**
 * IC model
 */
public class IC {
    public static final String REGEX_PATTERN = "[ST]\\d{7}[A-Z]";
    public static final String MESSAGE_CONSTRAINTS = "IC must start with S or T, "
            + "followed by 7 digits and finally an alphabet. E.g. S1234567A";
    public final String ic;

    public IC(String ic) {
        this.ic = removeSpaces(ic).toUpperCase();
    }

    public static boolean isValidIC(String ic) {
        return ic.matches(REGEX_PATTERN);
    }

    @Override
    public String toString() {
        return ic;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IC)) {
            return false;
        }

        IC otherIc = (IC) other;
        return ic.equals(otherIc.ic);
    }
}
