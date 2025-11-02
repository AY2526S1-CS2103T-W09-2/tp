package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_WARD;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.commons.util.CollectionUtil;
import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.person.IC;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.person.Ward;
import seedu.noknock.model.tag.Tag;

/**
 * Edits the details of an existing patient in the address book.
 */
public class EditPatientCommand extends Command {
    public static final String COMMAND_WORD = "edit-patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the patient identified "
        + "by the index number used in the displayed patient list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: PATIENT INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_IC + "IC]"
        + "[" + PREFIX_WARD + "WARD]"
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Patient: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PATIENT = "This patient already exists in the address book.";

    private final Index index;
    private final EditPatientDescriptor editPatientDescriptor;

    /**
     * @param index                 of the patient in the filtered person list to edit
     * @param editPatientDescriptor details to edit the patient with
     */
    public EditPatientCommand(Index index, EditPatientDescriptor editPatientDescriptor) {
        requireNonNull(index);
        requireNonNull(editPatientDescriptor);

        this.index = index;
        this.editPatientDescriptor = new EditPatientDescriptor(editPatientDescriptor);
    }

    /**
     * Creates and returns a {@code Patient} with the details of {@code patientToEdit}
     * edited with {@code editPatientDescriptor}.
     */
    private static Patient createEditedPatient(Patient patientToEdit, EditPatientDescriptor editPatientDescriptor) {
        requireNonNull(patientToEdit);

        Name updatedName = editPatientDescriptor.getName().orElse(patientToEdit.getName());
        Ward updatedWard = editPatientDescriptor.getWard().orElse(patientToEdit.getWard());
        IC updatedIc = editPatientDescriptor.getIC().orElse(patientToEdit.getIC());
        Set<Tag> updatedTags = editPatientDescriptor.getTags().orElse(patientToEdit.getTags());

        return new Patient(updatedName, updatedWard, updatedIc, updatedTags).withNextOfKinList(
            patientToEdit.getNextOfKinList()).withCaringSessionList(patientToEdit.getCaringSessionList());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToEdit = lastShownList.get(index.getZeroBased());
        Patient editedPatient = createEditedPatient(patientToEdit, editPatientDescriptor);

        if (!patientToEdit.isSamePerson(editedPatient) && model.hasPatient(editedPatient)) {
            throw new CommandException(MESSAGE_DUPLICATE_PATIENT);
        }

        model.setPatient(patientToEdit, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PERSONS);
        model.setSessionDisplayFilter(PREDICATE_SHOW_ALL_SESSIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.formatPatient(editedPatient)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPatientCommand otherEditPatientCommand)) {
            return false;
        }

        return index.equals(otherEditPatientCommand.index)
            && editPatientDescriptor.equals(otherEditPatientCommand.editPatientDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .add("editPatientDescriptor", editPatientDescriptor)
            .toString();
    }

    /**
     * Stores the details to edit the patient with. Each non-empty field value will replace the
     * corresponding field value of the patient.
     */
    public static class EditPatientDescriptor {
        private Name name;
        private Ward ward;
        private IC ic;
        private Set<Tag> tags;

        public EditPatientDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPatientDescriptor(EditPatientCommand.EditPatientDescriptor toCopy) {
            setName(toCopy.name);
            setIC(toCopy.ic);
            setWard(toCopy.ward);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, ic, ward, tags);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Ward> getWard() {
            return Optional.ofNullable(ward);
        }

        public void setWard(Ward ward) {
            this.ward = ward;
        }

        public Optional<IC> getIC() {
            return Optional.ofNullable(ic);
        }

        public void setIC(IC ic) {
            this.ic = ic;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPatientDescriptor otherEditPatientDescriptor)) {
                return false;
            }

            return Objects.equals(name, otherEditPatientDescriptor.name)
                && Objects.equals(ic, otherEditPatientDescriptor.ic)
                && Objects.equals(ward, otherEditPatientDescriptor.ward)
                && Objects.equals(tags, otherEditPatientDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .add("name", name)
                .add("ward", ward)
                .add("ic", ic)
                .add("tags", tags)
                .toString();
        }
    }
}
