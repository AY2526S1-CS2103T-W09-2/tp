package seedu.noknock.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.model.tag.Tag;

/**
 *
 */
public final class Patient extends Person {
    public static final String DEFAULT_IC = "S0000000A";
    public static final String DEFAULT_WARD = "1N";
    private final IC ic;
    private final Ward ward;
    private final List<Person> nextOfKinList = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * @param name
     */
    public Patient(Name name) {
        super(name);
        this.ic = new IC(DEFAULT_IC);
        this.ward = new Ward(DEFAULT_WARD);
    }
    /**
     * @param name
     * @param ward
     * @param ic
     * @param tags
     */
    public Patient(Name name, Ward ward, IC ic, Set<Tag> tags) {
        super(name);
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
        if (!(otherPerson instanceof Patient)) {
            return false;
        }
        Patient otherPatient = (Patient) otherPerson;
        return otherPatient.getName().equals(getName());
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
        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPerson = (Patient) other;
        return getName().equals(otherPerson.getName())
                && ward.equals(otherPerson.getWard())
                && ic.equals(otherPerson.getIC())
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
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
