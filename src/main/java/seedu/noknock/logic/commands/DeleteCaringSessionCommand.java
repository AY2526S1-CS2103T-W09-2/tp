package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.person.CaringSession;
import seedu.noknock.model.person.Patient;

/**
 * Deletes a caring session from a specific patient.
 */
public class DeleteCaringSessionCommand extends Command {

    public static final String COMMAND_WORD = "delete-session";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a caring session for a patient.\n"
            + "Parameters: PATIENT_INDEX SESSION_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_SUCCESS = "Deleted caring session for %1$s: %2$s";
    public static final String MESSAGE_INVALID_PATIENT_INDEX = "Patient index %d is out of range.";
    public static final String MESSAGE_INVALID_SESSION_INDEX = "Session index %d is out of range for patient %s.";

    private final Index patientIndex;
    private final Index sessionIndex;

    /**
     * Creates a DeleteCaringSessionCommand to delete the specified {@code CaringSession}
     */
    public DeleteCaringSessionCommand(Index patientIndex, Index sessionIndex) {
        this.patientIndex = patientIndex;
        this.sessionIndex = sessionIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> patientList = model.getFilteredPersonList();

        if (patientIndex.getZeroBased() >= patientList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_PATIENT_INDEX, patientIndex.getOneBased()));
        }

        Patient patient = patientList.get(patientIndex.getZeroBased());
        List<CaringSession> sessions = patient.getCaringSessions();

        if (sessionIndex.getZeroBased() >= sessions.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_SESSION_INDEX,
                    sessionIndex.getOneBased(), patient.getName()));
        }

        CaringSession sessionToDelete = sessions.get(sessionIndex.getZeroBased());
        patient.removeCaringSession(sessionToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS,
                patient.getName(), sessionToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteCaringSessionCommand)) {
            return false;
        }
        DeleteCaringSessionCommand o = (DeleteCaringSessionCommand) other;
        return patientIndex.equals(o.patientIndex)
                && sessionIndex.equals(o.sessionIndex);
    }
}
