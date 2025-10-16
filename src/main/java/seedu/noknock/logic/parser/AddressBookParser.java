package seedu.noknock.logic.parser;

import static seedu.noknock.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.noknock.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.noknock.commons.core.LogsCenter;
import seedu.noknock.logic.commands.AddNextOfKinCommand;
import seedu.noknock.logic.commands.AddPatientCommand;
import seedu.noknock.logic.commands.ClearCommand;
import seedu.noknock.logic.commands.Command;
import seedu.noknock.logic.commands.DeleteCommand;
import seedu.noknock.logic.commands.DeleteNextOfKinCommand;
import seedu.noknock.logic.commands.DeletePatientCommand;
import seedu.noknock.logic.commands.EditCommand;
import seedu.noknock.logic.commands.EditPatientCommand;
import seedu.noknock.logic.commands.ExitCommand;
import seedu.noknock.logic.commands.FindCommand;
import seedu.noknock.logic.commands.FindPatientByNextOfKinCommand;
import seedu.noknock.logic.commands.FindPatientCommand;
import seedu.noknock.logic.commands.HelpCommand;
import seedu.noknock.logic.commands.ListCommand;
import seedu.noknock.logic.commands.ListPatientsCommand;
import seedu.noknock.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddPatientCommand.COMMAND_WORD:
            return new AddPatientCommandParser().parse(arguments);

        case AddNextOfKinCommand.COMMAND_WORD:
            return new AddNextOfKinCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditPatientCommand.COMMAND_WORD:
            return new EditPatientCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeletePatientCommand.COMMAND_WORD:
            return new DeletePatientCommandParser().parse(arguments);

        case DeleteNextOfKinCommand.COMMAND_WORD:
            return new DeleteNextOfKinCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindPatientByNextOfKinCommand.COMMAND_WORD:
            return new FindPatientByNextOfKinCommandParser().parse(arguments);

        case FindPatientCommand.COMMAND_WORD:
            return new FindPatientCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListPatientsCommand.COMMAND_WORD:
            return new ListPatientsCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
