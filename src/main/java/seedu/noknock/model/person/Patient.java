package seedu.noknock.model.person;

import static seedu.noknock.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.model.tag.Tag;

/**
 * Represents a patient in the system with personal details, ward assignment,
 * IC number, next-of-kin list, caring sessions, and associated tags.
 * This class is immutable except for the modifiable lists.
 */
public final class Patient extends Person {
    private final IC ic;
    private final Ward ward;
    private final List<Person> nextOfKinList = new ArrayList<>();
    private final List<CaringSession> caringSessionList = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Constructs a {@code Patient} with the given details.
     *
     * @param name Patient's name.
     * @param ward Patient's assigned ward.
     * @param ic   Patient's identification number.
     * @param tags Tags associated with the patient.
     */
    public Patient(Name name, Ward ward, IC ic, Set<Tag> tags) {
        super(name);
        requireAllNonNull(ward, ic, tags);
        this.ic = ic;
        this.ward = ward;
        this.tags.addAll(tags);
    }

    public IC getIC() {
        return ic;
    }

    public Ward getWard() {
        return ward;
    }

    public List<Person> getNextOfKinList() {
        return nextOfKinList;
    }

    public void addNextOfKin(Person person) {
        nextOfKinList.add(person);
    }

    public List<CaringSession> getCaringSessionList() {
        return caringSessionList;
    }

    public void addCaringSession(CaringSession caringSession) {
        caringSessionList.add(caringSession);
    }

    /**
     * Returns an unmodifiable view of the patient's tags.
     *
     * @return Immutable set of tags.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Checks if another person has the same name, ward, and IC.
     *
     * @param otherPerson The person to compare.
     * @return True if both refer to the same patient identity.
     */
    @Override
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        if (!(otherPerson instanceof Patient otherPatient)) {
            return false;
        }
        return otherPatient.getName().equals(getName())
            && otherPatient.getWard().equals(getWard())
            && otherPatient.getIC().equals(getIC());
    }

    /**
     * Checks equality based on all identifying fields.
     *
     * @param other The object to compare.
     * @return True if all fields match.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Patient otherPerson)) {
            return false;
        }
        return getName().equals(otherPerson.getName())
            && ward.equals(otherPerson.getWard())
            && ic.equals(otherPerson.getIC())
            && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getIC(), getTags());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("name", getName())
            .add("ward", getWard())
            .add("ic", getIC())
            .add("tags", tags)
            .toString();
    }
}
