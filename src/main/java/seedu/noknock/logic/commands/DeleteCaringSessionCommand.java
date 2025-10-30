package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

import java.util.ArrayList;
import java.util.List;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.session.CaringSession;

/**
 * Deletes a caring session from a specific patient.
 */
public class DeleteCaringSessionCommand extends Command {

    public static final String COMMAND_WORD = "delete-session";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a caring session for a patient.\n"
        + "Parameters: PATIENT_INDEX SESSION_INDEX\n"
        + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_SUCCESS = "Deleted caring session for %1$s: %2$s";

    private final Index patientIndex;
    private final Index sessionIndex;

    /**
     * Creates a DeleteCaringSessionCommand to delete the specified {@code CaringSession}.
     */
    public DeleteCaringSessionCommand(Index patientIndex, Index sessionIndex) {
        this.patientIndex = patientIndex;
        this.sessionIndex = sessionIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> patientList = model.getFilteredPatientList();

        if (patientIndex.getZeroBased() >= patientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patient = patientList.get(patientIndex.getZeroBased());
        List<CaringSession> sessions = patient.getCaringSessionList();

        if (sessionIndex.getZeroBased() >= sessions.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_INDEX);
        }

        CaringSession sessionToDelete = sessions.get(sessionIndex.getZeroBased());
        List<CaringSession> updatedSessions = new ArrayList<>(sessions);
        updatedSessions.remove(sessionToDelete);

        Patient updatedPatient = patient.withCaringSessionList(updatedSessions);
        model.setPatient(patient, updatedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PERSONS);
        model.setSessionDisplayFilter(PREDICATE_SHOW_ALL_SESSIONS);

        return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS,
            Messages.formatSession(sessionToDelete), Messages.formatPatient(patient)));
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
