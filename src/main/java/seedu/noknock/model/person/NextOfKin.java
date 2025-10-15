package seedu.noknock.model.person;

import java.util.Objects;

import seedu.noknock.commons.util.ToStringBuilder;

/**
 * Returns a next of kin object
 */
public final class NextOfKin extends Person {
    private final Relationship relationship;
    private final Phone phone;

    /**
     * @param name
     * @param phone
     * @param relationship
     */
    public NextOfKin(Name name, Phone phone, Relationship relationship) {
        super(name);
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
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    @Override
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        if (otherPerson == null) {
            return false;
        }
        if (!(otherPerson instanceof NextOfKin)) {
            return false;
        }
        NextOfKin otherNok = (NextOfKin) otherPerson;
        return otherNok.getName().equals(getName())
                && otherNok.relationship.equals(getRelationship())
                && otherNok.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NextOfKin)) {
            return false;
        }

        NextOfKin otherNok = (NextOfKin) other;
        return getName().equals(otherNok.getName())
                && relationship.equals(otherNok.getRelationship())
                && phone.equals(otherNok.getPhone());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
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
