package seedu.noknock.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.noknock.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.noknock.model.person.Address;
import seedu.noknock.model.person.IC;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.person.Person;
import seedu.noknock.model.person.Ward;
import seedu.noknock.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPatientDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPatientDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPatientDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPatientDescriptorBuilder(Person person) {
        assert person instanceof Patient;
        Patient patient = (Patient) person;
        descriptor = new EditPersonDescriptor();
        descriptor.setName(patient.getName());
        descriptor.setWard(patient.getWard());
        descriptor.setIC(patient.getIC());
        descriptor.setTags(patient.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withWard(String ward) {
        descriptor.setWard(new Ward(ward));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withIC(String ic) {
        descriptor.setIC(new IC(ic));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPatientDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
