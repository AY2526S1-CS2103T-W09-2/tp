package seedu.noknock.testutil;

import seedu.noknock.logic.commands.EditCaringSessionCommand.EditSessionDescriptor;
import seedu.noknock.model.date.Date;
import seedu.noknock.model.date.Time;
import seedu.noknock.model.session.CareType;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.model.session.Note;
import seedu.noknock.model.session.SessionStatus;

/**
 * A utility class to help with building EditSessionDescriptor objects.
 */
public class EditSessionDescriptorBuilder {

    private final EditSessionDescriptor descriptor;

    /**
     * Creates a {@code EditSessionDescriptorBuilder} with default details.
     */
    public EditSessionDescriptorBuilder() {
        descriptor = new EditSessionDescriptor();
    }

    /**
     * Initializes the builder with the data of {@code descriptor}.
     */
    public EditSessionDescriptorBuilder(EditSessionDescriptor descriptor) {
        this.descriptor = new EditSessionDescriptor(descriptor);
    }

    /**
     * Initializes the builder with the fields of {@code session}.
     */
    public EditSessionDescriptorBuilder(CaringSession session) {
        descriptor = new EditSessionDescriptor();
        descriptor.setCareType(session.getCareType());
        descriptor.setNote(session.getNote());
        descriptor.setDate(session.getDate());
        descriptor.setTime(session.getTime());
        descriptor.setStatus(session.getStatus());
    }

    /**
     * Sets the {@code CareType} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withCareType(String careType) {
        descriptor.setCareType(new CareType(careType));
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withTime(String time) {
        descriptor.setTime(new Time(time));
        return this;
    }

    /**
     * Sets the {@code SessionStatus} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withStatus(SessionStatus status) {
        descriptor.setStatus(status);
        return this;
    }

    /**
     * Builds the {@code EditSessionDescriptor}.
     */
    public EditSessionDescriptor build() {
        return descriptor;
    }
}
