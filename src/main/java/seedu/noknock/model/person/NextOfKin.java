package seedu.noknock.model.person;

import static seedu.noknock.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.noknock.commons.util.ToStringBuilder;

/**
 * Represents a next-of-kin (NOK) associated with a patient.
 * A NextOfKin has a name, phone number, and relationship to the patient.
 * This class is immutable.
 */
public final class NextOfKin extends Person {
    private final Relationship relationship;
    private final Phone phone;

    /**
     * Constructs a {@code NextOfKin} with the specified details.
     *
     * @param name         The name of the next-of-kin.
     * @param phone        The phone number of the next-of-kin.
     * @param relationship The relationship of the next-of-kin to the patient.
     */
    public NextOfKin(Name name, Phone phone, Relationship relationship) {
        super(name);
        requireAllNonNull(phone, relationship);
        this.relationship = relationship;
        this.phone = phone;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public Phone getPhone() {
        return phone;
    }

    /**
     * Returns true if both persons have the same name, phone, and relationship.
     * Defines a weaker notion of equality between two persons.
     *
     * @param otherPerson The person to compare.
     * @return True if both represent the same next-of-kin identity.
     */
    @Override
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        if (!(otherPerson instanceof NextOfKin otherNok)) {
            return false;
        }
        return otherNok.getName().equals(getName())
            && otherNok.getRelationship().equals(getRelationship())
            && otherNok.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both next-of-kin objects have the same identity and data fields.
     *
     * @param other The object to compare.
     * @return True if both contain identical information.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NextOfKin otherNok)) {
            return false;
        }
        return getName().equals(otherNok.getName())
            && relationship.equals(otherNok.getRelationship())
            && phone.equals(otherNok.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getRelationship(), getPhone());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("name", getName())
            .add("phone", getPhone())
            .add("relationship", getRelationship())
            .toString();
    }
}
