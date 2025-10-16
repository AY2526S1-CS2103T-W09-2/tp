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
    public static final String DEFAULT_IC = "S0000000A";
    public static final String DEFAULT_WARD = "1N";

    private final IC ic;
    private final Ward ward;
    private final List<NextOfKin> nextOfKinList = new ArrayList<>();
    private final List<CaringSession> caringSessionList = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Constructs a {@code Patient} with the given name and default ward/IC.
     *
     * @param name Patient's name.
     */
    public Patient(Name name) {
        super(name);
        this.ic = new IC(DEFAULT_IC);
        this.ward = new Ward(DEFAULT_WARD);
    }

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

    public List<NextOfKin> getNextOfKinList() {
        return nextOfKinList;
    }

    public List<CaringSession> getCaringSessionList() {
        return caringSessionList;
    }

    /**
     * Creates a copy of the current patient with an updated next-of-kin list.
     *
     * @param newNextOfKinList The new list of next-of-kin.
     * @return A new Patient instance with the updated next-of-kin list.
     */
    public Patient withNextOfKinList(List<NextOfKin> newNextOfKinList) {
        Patient copy = new Patient(getName(), getWard(), getIC(), getTags());
        copy.nextOfKinList.addAll(newNextOfKinList);
        copy.caringSessionList.addAll(this.caringSessionList);
        return copy;
    }

    /**
     * Creates a copy of the current patient with an updated caring session list.
     *
     * @param newCaringSessionList The new list of caring sessions.
     * @return A new Patient instance with the updated caring session list.
     */
    public Patient withCaringSessionList(List<CaringSession> newCaringSessionList) {
        Patient copy = new Patient(getName(), getWard(), getIC(), getTags());
        copy.nextOfKinList.addAll(this.nextOfKinList);
        copy.caringSessionList.addAll(newCaringSessionList);
        return copy;
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
        return otherPatient.getName().equals(getName());
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
        return Objects.hash(getName(), getWard(), getIC(), getTags());
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
