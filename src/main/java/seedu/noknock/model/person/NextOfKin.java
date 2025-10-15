package seedu.noknock.model.person;

import java.util.Objects;

import seedu.noknock.commons.util.ToStringBuilder;

/**
 * Represents a next of kin, which is a special type of {@link Person}.
 * Stores the name, patient, phone number, and relationship to the patient.
 * Instances are immutable.
 */
public final class NextOfKin extends Person {
    private final Patient patient;
    private final Phone phone;
    private final Relationship relationship;

    /**
     * Constructs a {@code NextOfKin} with the specified name, phone, and relationship.
     *
     * @param name         The name of the next of kin.
     * @param patient      The patient associated with this next of kin.
     * @param phone        The phone number of the next of kin.
     * @param relationship The relationship to the main person.
     */
    public NextOfKin(Name name, Patient patient, Phone phone, Relationship relationship) {
        super(name);
        this.patient = patient;
        this.phone = phone;
        this.relationship = relationship;
    }

    /**
     * Returns the patient associated with this next of kin.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the phone number of the next of kin.
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * Returns the relationship to the main person.
     */
    public Relationship getRelationship() {
        return relationship;
    }

    /**
     * Returns true if both persons have the same name, patient, relationship, and phone.
     * This defines a weaker notion of equality between two persons.
     *
     * @param otherPerson The other person to compare.
     * @return true if both have the same name, relationship, and phone; false otherwise.
     */
    @Override
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        if (otherPerson == null) {
            return false;
        }
        if (!(otherPerson instanceof NextOfKin otherNok)) {
            return false;
        }
        return otherNok.getName().equals(getName())
            && otherNok.getPatient().equals(getPatient())
            && otherNok.getPhone().equals(getPhone())
            && otherNok.relationship.equals(getRelationship());
    }

    /**
     * Returns true if both objects are NextOfKin and have the same name, patient, relationship, and phone.
     *
     * @param other The object to compare.
     * @return true if both are equal; false otherwise.
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
            && patient.equals(otherNok.getPatient())
            && phone.equals(otherNok.getPhone())
            && relationship.equals(otherNok.getRelationship());
    }

    /**
     * Returns the hash code for this next of kin.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPatient(), getPhone(), getRelationship());
    }

    /**
     * Returns a string representation of this next of kin.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("name", getName())
            .add("patient", getPatient())
            .add("phone", getPhone())
            .add("relationship", getRelationship())
            .toString();
    }
}
