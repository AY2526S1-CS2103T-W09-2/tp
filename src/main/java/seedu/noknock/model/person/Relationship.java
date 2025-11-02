package seedu.noknock.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.commons.util.AppUtil.checkArgument;

/**
 * Represents the relationship between a patient and their next-of-kin.
 * Guarantees: immutable; is valid as declared in {@link #isValidRelationship(String)}
 */
public enum Relationship {
    // Immediate family
    FATHER("Father"),
    MOTHER("Mother"),
    SON("Son"),
    DAUGHTER("Daughter"),
    SPOUSE("Spouse"),
    HUSBAND("Husband"),
    WIFE("Wife"),

    // Siblings
    BROTHER("Brother"),
    SISTER("Sister"),

    // Grandparents and grandchildren
    GRANDFATHER("Grandfather"),
    GRANDMOTHER("Grandmother"),
    GRANDSON("Grandson"),
    GRANDDAUGHTER("Granddaughter"),

    // In-laws
    FATHER_IN_LAW("Father-in-law"),
    MOTHER_IN_LAW("Mother-in-law"),
    SON_IN_LAW("Son-in-law"),
    DAUGHTER_IN_LAW("Daughter-in-law"),
    BROTHER_IN_LAW("Brother-in-law"),
    SISTER_IN_LAW("Sister-in-law"),

    // Extended family
    UNCLE("Uncle"),
    AUNT("Aunt"),
    COUSIN("Cousin"),
    NEPHEW("Nephew"),
    NIECE("Niece"),

    // Other common NOK relationships
    GRANDUNCLE("Granduncle"),
    GRANDAUNT("Grandaunt"),
    GODPARENT("Godparent"),
    GUARDIAN("Guardian"),
    CAREGIVER("Caregiver"),
    FRIEND("Friend"),
    NEIGHBOUR("Neighbour"),
    DOMESTIC_HELPER("Domestic Helper"),
    OTHER("Other");

    public static final String MESSAGE_CONSTRAINTS = String.format(
        "Relationship must be one of:%n"
            + "Immediate Family: Father, Mother, Son, Daughter, Spouse, Husband, Wife%n"
            + "Siblings: Brother, Sister%n"
            + "Grand-family: Grandfather, Grandmother, Grandson, Granddaughter%n"
            + "In-laws: Father-in-law, Mother-in-law, Son-in-law, Daughter-in-law, Brother-in-law, Sister-in-law%n"
            + "Extended Family: Uncle, Aunt, Cousin, Nephew, Niece%n"
            + "Other Family: Granduncle, Grandaunt, Godparent%n"
            + "Non-Family / Care: Guardian, Caregiver, Friend, Neighbour, Domestic Helper%n"
            + "Miscellaneous: Other%n"
            + "(case-insensitive)"
    );

    private final String displayValue;

    Relationship(String displayValue) {
        this.displayValue = displayValue;
    }

    /**
     * Returns true if a given string is a valid relationship.
     */
    public static boolean isValidRelationship(String test) {
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
     * Converts a string to a Relationship enum value.
     * Case-insensitive matching.
     *
     * @param relationship The relationship string to convert.
     * @return The corresponding Relationship enum value.
     * @throws IllegalArgumentException if the string is not a valid relationship.
     */
    public static Relationship fromString(String relationship) {
        requireNonNull(relationship);
        String normalized = relationship.toUpperCase().trim().replace("-", "_");
        try {
            return Relationship.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Creates a Relationship with validation (for consistency with other classes).
     *
     * @param relationship A valid relationship string.
     * @return The corresponding Relationship enum value.
     */
    public static Relationship of(String relationship) {
        requireNonNull(relationship);
        checkArgument(isValidRelationship(relationship), MESSAGE_CONSTRAINTS);
        return fromString(relationship);
    }

    @Override
    public String toString() {
        return displayValue;
    }
}
