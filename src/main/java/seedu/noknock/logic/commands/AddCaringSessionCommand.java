package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_CARE_TYPE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.session.CaringSession;

/**
 * Adds a caring session to an existing patient
 */
public class AddCaringSessionCommand extends Command {
    public static final String COMMAND_WORD = "add-session";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds a care task for a patient .\n"
        + "Parameters: PATIENT_INDEX (must be a positive integer) "
        + PREFIX_DATE + "DATE "
        + PREFIX_TIME + "TIME "
        + PREFIX_CARE_TYPE + "CARE_TYPE ["
        + PREFIX_NOTES + "notes ]"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_DATE + "2024-12-25 "
        + PREFIX_TIME + "14:30 "
        + PREFIX_CARE_TYPE + "medication "
        + PREFIX_NOTES + "Give insulin shot ";

    public static final String MESSAGE_ADD_CARING_SESSION_SUCCESS = "Added Caring Session: %1$s to Patient: %2$s";
    public static final String MESSAGE_HAS_OVERLAPPING_SESSION =
        "This session (%1$s) overlaps with an existing session.";

    private final Index patientIndex;
    private final CaringSession sessionToAdd;

    /**
     * Creates a command which adds a {@Link CaringSession} to an existing patient
     *
     * @param patientIndex Index of the patient in the filtered patient list.
     * @param sessionToAdd specifies the caring session to be added.
     */
    public AddCaringSessionCommand(Index patientIndex, CaringSession sessionToAdd) {
        requireNonNull(patientIndex);
        requireNonNull(sessionToAdd);
        this.patientIndex = patientIndex;
        this.sessionToAdd = sessionToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> patientList = model.getFilteredPatientList();

        if (patientIndex.getZeroBased() >= patientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patient = patientList.get(patientIndex.getZeroBased());
        List<CaringSession> caringSessionList = patient.getCaringSessionList();

        if (patient.hasOverlappingSession(sessionToAdd)) {
            throw new CommandException(String.format(MESSAGE_HAS_OVERLAPPING_SESSION, sessionToAdd.getCareType()));
        }

        List<CaringSession> updatedCaringSessionList = new ArrayList<>(caringSessionList);
        updatedCaringSessionList.add(sessionToAdd);

        Patient editedPatient = patient.withCaringSessionList(updatedCaringSessionList);
        model.setPatient(patient, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ADD_CARING_SESSION_SUCCESS,
            Messages.formatSession(sessionToAdd),
            Messages.formatPatient(editedPatient)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddCaringSessionCommand otherAddCommand)) {
            return false;
        }
        return patientIndex.equals(otherAddCommand.patientIndex)
            && sessionToAdd.equals(otherAddCommand.sessionToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("patientIndex", patientIndex)
            .add("sessionToAdd", sessionToAdd)
            .toString();
    }
}
