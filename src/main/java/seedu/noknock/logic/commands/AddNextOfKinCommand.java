package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

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
 * Adds a next of kin to an existing patient.
 */
public class AddNextOfKinCommand extends Command {

    public static final String COMMAND_WORD = "add-nok";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds a next of kin to the patient identified by the patient index.\n"
        + "Parameters: PATIENT_INDEX (must be a positive integer) "
        + PREFIX_NAME + "NAME "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_RELATIONSHIP + "RELATIONSHIP\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_NAME + "Jane Doe "
        + PREFIX_PHONE + "98765432 "
        + PREFIX_RELATIONSHIP + "Mother";

    public static final String MESSAGE_ADD_NOK_SUCCESS = "Added NextOfKin: %1$s to Patient: %2$s";
    public static final String MESSAGE_DUPLICATE_NOK = "This next of kin already exists for this patient";

    private final Index patientIndex;
    private final NextOfKin nokToAdd;

    /**
     * Creates an AddNextOfKinCommand to add the specified next of kin to a patient.
     *
     * @param patientIndex Index of the patient in the filtered patient list.
     * @param nokToAdd     The next of kin to add.
     */
    public AddNextOfKinCommand(Index patientIndex, NextOfKin nokToAdd) {
        requireNonNull(patientIndex);
        requireNonNull(nokToAdd);
        this.patientIndex = patientIndex;
        this.nokToAdd = nokToAdd;
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

        if (nokList.contains(nokToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOK);
        }

        List<NextOfKin> updatedNokList = new ArrayList<>(nokList);
        updatedNokList.add(nokToAdd);

        Patient editedPatient = patient.withNextOfKinList(updatedNokList);
        model.setPatient(patient, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PERSONS);

        model.setSessionDisplayFilter(PREDICATE_SHOW_ALL_SESSIONS);

        return new CommandResult(String.format(MESSAGE_ADD_NOK_SUCCESS,
            Messages.formatNextOfKin(nokToAdd), Messages.formatPatient(patient)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddNextOfKinCommand otherAddCommand)) {
            return false;
        }
        return patientIndex.equals(otherAddCommand.patientIndex)
            && nokToAdd.equals(otherAddCommand.nokToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("patientIndex", patientIndex)
            .add("nokToAdd", nokToAdd)
            .toString();
    }
}
