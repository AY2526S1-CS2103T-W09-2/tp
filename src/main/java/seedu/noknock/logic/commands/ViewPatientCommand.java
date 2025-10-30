package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.logic.Messages.MESSAGE_VIEW_PATIENT_SUCCESS;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

import java.util.List;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.person.PatientEqualsPredicate;

/**
 * Finds and displays the patient who is equal to the patient at the given index
 * in the current filtered patient list.
 */
public class ViewPatientCommand extends Command {

    public static final String COMMAND_WORD = "view-patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Shows the patient at the specified index in the current patient list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";


    private final Index index;

    /**
     * Creates a ViewPatientCommand to view the patient at the specified index.
     *
     * @param index The index of the patient to view.
     */
    public ViewPatientCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient target = lastShownList.get(index.getZeroBased());
        PatientEqualsPredicate predicate = new PatientEqualsPredicate(target);
        model.updateFilteredPatientList(predicate);
        model.setSessionDisplayFilter(PREDICATE_SHOW_ALL_SESSIONS);
        return new CommandResult(String.format(MESSAGE_VIEW_PATIENT_SUCCESS, target.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ViewPatientCommand otherCommand)) {
            return false;
        }
        return index.equals(otherCommand.index);
    }

    @Override
    public String toString() {
        return "ViewPatientCommand(index=" + index + ")";
    }
}
