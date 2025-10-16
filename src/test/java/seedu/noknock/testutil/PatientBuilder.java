package seedu.noknock.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.noknock.model.person.IC;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.person.Ward;
import seedu.noknock.model.tag.Tag;
import seedu.noknock.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_WARD = "2A";
    public static final String DEFAULT_IC = "T1234567A";

    private Name name;
    private Ward ward;
    private IC ic;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        ward = new Ward(DEFAULT_WARD);
        ic = new IC(DEFAULT_IC);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        ward = patientToCopy.getWard();
        ic = patientToCopy.getIC();
        tags = new HashSet<>(patientToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PatientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PatientBuilder withWard(String ward) {
        this.ward = new Ward(ward);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PatientBuilder withIC(String ic) {
        this.ic = new IC(ic);
        return this;
    }


    public Patient build() {
        return new Patient(name, ward, ic, tags);
    }

}
