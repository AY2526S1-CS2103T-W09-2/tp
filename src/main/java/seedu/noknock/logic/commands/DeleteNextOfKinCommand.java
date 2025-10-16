package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.person.NextOfKin;
import seedu.noknock.model.person.Patient;

/**
 * Deletes a next of kin identified by patient and NOK indices.
 */
public class DeleteNextOfKinCommand extends Command {

    public static final String COMMAND_WORD = "delete-nok";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the next of kin identified by the patient index and NOK index.\n"
        + "Parameters: PATIENT_INDEX NOK_INDEX (both must be positive integers)\n"
        + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_NOK_SUCCESS = "Deleted NextOfKin: %1$s";
    public static final String MESSAGE_INVALID_NOK_INDEX = "Invalid next of kin index for patient.";

    private final Index patientIndex;
    private final Index nokIndex;

    /**
     * Creates a DeleteNextOfKinCommand to delete the specified next of kin of a patient.
     *
     * @param patientIndex Index of the patient in the filtered patient list.
     * @param nokIndex     Index of the next of kin in the patient's next of kin list.
     */
    public DeleteNextOfKinCommand(Index patientIndex, Index nokIndex) {
        this.patientIndex = patientIndex;
        this.nokIndex = nokIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> patientList = model.getFilteredPersonList();

        if (patientIndex.getZeroBased() >= patientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Patient patient = patientList.get(patientIndex.getZeroBased());
        List<NextOfKin> nokList = patient.getNextOfKinList();

        if (nokIndex.getZeroBased() >= nokList.size() || nokIndex.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_NOK_INDEX);
        }

        NextOfKin nokToDelete = nokList.get(nokIndex.getZeroBased());
        List<NextOfKin> updatedNokList = new ArrayList<>(nokList);
        updatedNokList.remove(nokToDelete);

        Patient editedPatient = patient.withNextOfKinList(updatedNokList);
        model.setPerson(patient, editedPatient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DELETE_NOK_SUCCESS, Messages.format(nokToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteNextOfKinCommand otherDeleteCommand)) {
            return false;
        }
        return patientIndex.equals(otherDeleteCommand.patientIndex)
            && nokIndex.equals(otherDeleteCommand.nokIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("patientIndex", patientIndex)
            .add("nokIndex", nokIndex)
            .toString();
    }
}
