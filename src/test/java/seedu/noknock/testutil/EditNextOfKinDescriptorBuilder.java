package seedu.noknock.testutil;

import seedu.noknock.logic.commands.EditNextOfKinCommand.EditNextOfKinDescriptor;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.NextOfKin;
import seedu.noknock.model.person.Phone;
import seedu.noknock.model.person.Relationship;

/**
 * A utility class to help with building EditNextOfKinDescriptor objects.
 */
public class EditNextOfKinDescriptorBuilder {

    private EditNextOfKinDescriptor descriptor;

    /**
     * Creates a {@code EditNextOfKinDescriptor} with default values.
     */
    public EditNextOfKinDescriptorBuilder() {
        descriptor = new EditNextOfKinDescriptor();
    }

    /**
     * Creates a {@code EditNextOfKinDescriptor} with the given descriptor's details.
     */
    public EditNextOfKinDescriptorBuilder(EditNextOfKinDescriptor descriptor) {
        this.descriptor = new EditNextOfKinDescriptor(descriptor);
    }

    /**
     * Creates a {@code EditNextOfKinDescriptor} with the details of the given {@code NextOfKin}.
     */
    public EditNextOfKinDescriptorBuilder(NextOfKin nok) {
        descriptor = new EditNextOfKinDescriptor();
        descriptor.setName(nok.getName());
        descriptor.setPhone(nok.getPhone());
        descriptor.setRelationship(nok.getRelationship());
    }

    /**
     * Sets the {@code Name} of the {@code EditNextOfKinDescriptor} that we are building.
     */
    public EditNextOfKinDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditNextOfKinDescriptor} that we are building.
     */
    public EditNextOfKinDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Relationship} of the {@code EditNextOfKinDescriptor} that we are building.
     */
    public EditNextOfKinDescriptorBuilder withRelationship(String relationship) {
        descriptor.setRelationship(Relationship.of(relationship));
        return this;
    }

    /**
     * Builds and returns the {@code EditNextOfKinDescriptor}.
     */
    public EditNextOfKinDescriptor build() {
        return descriptor;
    }
}
