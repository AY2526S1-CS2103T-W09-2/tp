package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.logic.Messages;
import seedu.noknock.model.Model;
import seedu.noknock.model.person.PatientNokContainsKeywordsPredicate;

/**
 * Finds and lists all patients whose next-of-kin name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPatientByNextOfKinCommand extends Command {

    public static final String COMMAND_WORD = "find-by-nok";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all patients whose next-of-kin names "
        + "contain any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " jane john";

    private final PatientNokContainsKeywordsPredicate predicate;

    public FindPatientByNextOfKinCommand(PatientNokContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPatientList(predicate);
        model.setSessionDisplayFilter(PREDICATE_SHOW_ALL_SESSIONS);
        return new CommandResult(
            String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPatientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindPatientByNextOfKinCommand)) {
            return false;
        }

        FindPatientByNextOfKinCommand otherCommand = (FindPatientByNextOfKinCommand) other;
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("predicate", predicate)
            .toString();
    }
}
