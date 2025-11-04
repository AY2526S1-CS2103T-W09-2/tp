package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_WARD;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.person.Patient;

/**
 * Adds a patient to the address book.
 */
public class AddPatientCommand extends Command {

    public static final String COMMAND_WORD = "add-patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient to the database"
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_WARD + "WARD "
        + PREFIX_IC + "IC"
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_WARD + "2A "
        + PREFIX_IC + "S1234567A "
        + PREFIX_TAG + "friends "
        + PREFIX_TAG + "owesMoney\n"
        + "Note: IC must be entered in capital letters.";

    public static final String MESSAGE_SUCCESS = "New patient added: %1$s";
    public static final String MESSAGE_DUPLICATE_PATIENT = "A patient with this IC already exists in the database";

    private final Patient toAdd;

    /**
     * Creates an AddPatientCommand to add the specified {@code Patient}
     */
    //Change constructor
    public AddPatientCommand(Patient patient) {
        requireNonNull(patient);
        toAdd = patient;
    }

    @Override
    //model should be able to identify runtime type of Patient or NOK
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPatient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PATIENT);
        }

        model.addPatient(toAdd);
        model.setSessionDisplayFilter(PREDICATE_SHOW_ALL_SESSIONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatPatient(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPatientCommand)) {
            return false;
        }

        AddPatientCommand otherAddCommand = (AddPatientCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("toAdd", toAdd)
            .toString();
    }
}
