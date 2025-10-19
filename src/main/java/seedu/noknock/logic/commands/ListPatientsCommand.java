package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.noknock.model.Model;
import seedu.noknock.model.person.IsPatientPredicate;

/**
 * Lists all patients in the address book to the user.
 */
public class ListPatientsCommand extends Command {

    public static final String COMMAND_WORD = "list-patients";

    public static final String MESSAGE_SUCCESS = "Listed all patients";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPatientList(new IsPatientPredicate());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
