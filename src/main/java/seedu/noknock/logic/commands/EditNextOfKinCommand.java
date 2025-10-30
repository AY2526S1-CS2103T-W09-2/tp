package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.commons.util.CollectionUtil;
import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.NextOfKin;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.person.Phone;
import seedu.noknock.model.person.Relationship;

/**
 * Edits the details of an existing next of kin of a patient in the address book.
 */
public class EditNextOfKinCommand extends Command {

    public static final String COMMAND_WORD = "edit-nok";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the NOK of patient identified "
        + "by the index number used in the displayed patient list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: PATIENT_INDEX (must be a positive integer) "
        + "Parameters: NOK_INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME]"
        + "[" + PREFIX_PHONE + "PHONE]"
        + "[" + PREFIX_RELATIONSHIP + "RELATIONSHIP]\n"
        + "Example: " + COMMAND_WORD + " 1 2 "
        + PREFIX_NAME + "Jane Doe "
        + PREFIX_PHONE + "98765432 "
        + PREFIX_RELATIONSHIP + "Mother";

    public static final String MESSAGE_EDIT_NOK_SUCCESS = "Edited NextOfKin: %1$s of Patient: %2$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index patientIndex;
    private final Index nokIndex;
    private final EditNextOfKinDescriptor editNextOfKinDescriptor;

    /**
     * Creates an AddNextOfKinCommand to add the specified next of kin to a patient.
     *
     * @param patientIndex            Index of the patient in the filtered patient list.
     * @param nokIndex                Index of the NOK in the NOK List
     * @param editNextOfKinDescriptor Edited Next of Kin.
     */
    public EditNextOfKinCommand(Index patientIndex, Index nokIndex, EditNextOfKinDescriptor editNextOfKinDescriptor) {
        requireNonNull(patientIndex);
        requireNonNull(editNextOfKinDescriptor);
        this.patientIndex = patientIndex;
        this.nokIndex = nokIndex;
        this.editNextOfKinDescriptor = new EditNextOfKinDescriptor(editNextOfKinDescriptor);
    }

    private static NextOfKin createEditedNok(NextOfKin nokToEdit, EditNextOfKinDescriptor editNextOfKinDescriptor) {
        assert nokToEdit != null;

        Name updatedName = editNextOfKinDescriptor.getName().orElse(nokToEdit.getName());
        Phone updatedPhone = editNextOfKinDescriptor.getPhone().orElse(nokToEdit.getPhone());
        Relationship updatedRelationship =
            editNextOfKinDescriptor.getRelationship().orElse(nokToEdit.getRelationship());

        return new NextOfKin(updatedName, updatedPhone, updatedRelationship);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> patientList = model.getFilteredPatientList();

        if (patientIndex.getZeroBased() >= patientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patient = patientList.get(patientIndex.getZeroBased());
        List<NextOfKin> nokList = patient.getNextOfKinList();

        if (nokIndex.getZeroBased() >= nokList.size() || nokIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOK_DISPLAYED_INDEX);
        }

        NextOfKin nokToEdit = nokList.get(nokIndex.getZeroBased());
        NextOfKin editedNok = createEditedNok(nokToEdit, editNextOfKinDescriptor);

        List<NextOfKin> updatedNokList = new ArrayList<>(nokList);
        updatedNokList.set(nokIndex.getZeroBased(), editedNok);

        Patient editedPatient = patient.withNextOfKinList(updatedNokList);
        model.setPatient(patient, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PERSONS);
        model.setSessionDisplayFilter(PREDICATE_SHOW_ALL_SESSIONS);

        return new CommandResult(String.format(MESSAGE_EDIT_NOK_SUCCESS,
            Messages.formatNextOfKin(editedNok), Messages.formatPatient(patient)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditNextOfKinCommand otherEditCommand)) {
            return false;
        }
        return patientIndex.equals(otherEditCommand.patientIndex)
            && nokIndex.equals(otherEditCommand.nokIndex)
            && editNextOfKinDescriptor.equals(otherEditCommand.editNextOfKinDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("patientIndex", patientIndex)
            .add("editNextOfKinDescriptor", editNextOfKinDescriptor)
            .toString();
    }

    /**
     * Stores the details to edit the Nok with. Each non-empty field value will replace the
     * corresponding field value of the Nok.
     */
    public static class EditNextOfKinDescriptor {
        private Name name;
        private Phone phone;
        private Relationship relationship;

        public EditNextOfKinDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditNextOfKinDescriptor(EditNextOfKinDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setRelationship(toCopy.relationship);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, relationship);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Relationship> getRelationship() {
            return Optional.ofNullable(relationship);
        }

        public void setRelationship(Relationship relationship) {
            this.relationship = relationship;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditNextOfKinDescriptor otherEditNextOfKinDescriptor)) {
                return false;
            }

            return Objects.equals(name, otherEditNextOfKinDescriptor.name)
                && Objects.equals(phone, otherEditNextOfKinDescriptor.phone)
                && Objects.equals(relationship, otherEditNextOfKinDescriptor.relationship);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("relationship", relationship)
                .toString();
        }
    }
}
