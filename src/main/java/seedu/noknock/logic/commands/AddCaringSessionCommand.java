package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.person.CaringSession;
import seedu.noknock.model.person.Patient;

/**
 * Adds a caring session for a patient identified by their index.
 */
public class AddCaringSessionCommand extends Command {

    public static final String COMMAND_WORD = "add-session";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a caring session for a patient.\n"
            + "Parameters: PATIENT_INDEX d/DATE t/TIME type/CARE_TYPE [notes/NOTES]\n"
            + "Example: " + COMMAND_WORD + " 1 d/2025-10-16 t/14:30 type/medication notes/Give insulin shot";
    public static final String MESSAGE_SUCCESS = "Caring session added for %1$s: %2$s on %3$s at %4$s (%5$s)";
    public static final String MESSAGE_INVALID_PATIENT_INDEX = "Patient index %d is out of range.";
    public static final String MESSAGE_DUPLICATE_SESSION = "Duplicate caring session: same date, time, and care type.";

    private final Index patientIndex;
    private final CaringSession session;

    /**
     * Creates an AddCaringSessionCommand to add the specified {@code CaringSession}
     */
    public AddCaringSessionCommand(Index patientIndex, CaringSession session) {
        this.patientIndex = patientIndex;
        this.session = session;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (patientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_PATIENT_INDEX, patientIndex.getOneBased()));
        }

        Patient patient = lastShownList.get(patientIndex.getZeroBased());

        if (patient.hasOverlappingSession(session)) {
            throw new CommandException(MESSAGE_DUPLICATE_SESSION);
        }

        patient.addCaringSession(session);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                patient.getName(),
                session.getCareType(),
                session.getDate(),
                session.getTime(),
                session.getNotes()));
    }
}
