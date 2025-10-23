package seedu.noknock.testutil;

import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.NextOfKin;
import seedu.noknock.model.person.Phone;
import seedu.noknock.model.person.Relationship;

/**
 * A utility class to help with building NextOfKin objects.
 */
public class NextOfKinBuilder {

    public static final String DEFAULT_NAME = "John Doe";
    public static final String DEFAULT_PHONE = "98765432";
    public static final String DEFAULT_RELATIONSHIP = "daughter";

    private Name name;
    private Phone phone;
    private Relationship relationship;

    /**
     * Creates a {@code NextOfKinBuilder} with the default details.
     */
    public NextOfKinBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        relationship = Relationship.of(DEFAULT_RELATIONSHIP);
    }

    /**
     * Initializes the NextOfKinBuilder with the data of {@code nokToCopy}.
     */
    public NextOfKinBuilder(NextOfKin nokToCopy) {
        name = nokToCopy.getName();
        phone = nokToCopy.getPhone();
        relationship = nokToCopy.getRelationship();
    }

    /**
     * Sets the {@code Name} of the {@code NextOfKin} that we are building.
     */
    public NextOfKinBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code NextOfKin} that we are building.
     */
    public NextOfKinBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Relationship} of the {@code NextOfKin} that we are building.
     */
    public NextOfKinBuilder withRelationship(String relationship) {
        this.relationship = Relationship.of(relationship);
        return this;
    }

    /**
     * Builds the NextOfKin object.
     */
    public NextOfKin build() {
        return new NextOfKin(name, phone, relationship);
    }
}
