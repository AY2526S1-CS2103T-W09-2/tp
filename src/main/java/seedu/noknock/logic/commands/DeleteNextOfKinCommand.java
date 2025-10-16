package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.person.NextOfKin;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteNextOfKinCommand extends Command {

    public static final String COMMAND_WORD = "delete-nok";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the next of kin identified by the index number used in the displayed next of kin list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PATIENT_SUCCESS = "Deleted NextOfKin: %1$s";
    public static final String MESSAGE_INVALID_PATIENT_INDEX = "Invalid next of kin index.\n"
            + "Please use a number from the next of kin list.";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "NextOfKin not found at index %d";

    private final Index targetIndex;

    public DeleteNextOfKinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<NextOfKin> lastShownList = model.getFilteredPersonList().stream()
                .filter(p -> p instanceof NextOfKin)
                .map(p -> (NextOfKin) p)
                .toList();

        int zeroBasedIndex = targetIndex.getZeroBased();
        if (zeroBasedIndex >= lastShownList.size() || zeroBasedIndex < 0) {
            throw new CommandException(MESSAGE_INVALID_PATIENT_INDEX);
        }

        NextOfKin nextOfKinToDelete = lastShownList.get(zeroBasedIndex);
        if (nextOfKinToDelete == null) {
            throw new CommandException(String.format(MESSAGE_PATIENT_NOT_FOUND, targetIndex.getOneBased()));
        }

        model.deletePerson(nextOfKinToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PATIENT_SUCCESS, Messages.format(nextOfKinToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteNextOfKinCommand otherDeleteCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
