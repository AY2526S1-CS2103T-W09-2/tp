package seedu.noknock.logic.parser;

import static seedu.noknock.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.commands.DeleteNextOfKinCommand;
import seedu.noknock.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteNextOfKinCommand object
 */
public class DeleteNextOfKinCommandParser implements Parser<DeleteNextOfKinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteNextOfKinCommand
     * and returns a DeleteNextOfKinCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public DeleteNextOfKinCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split("\\s+");
        if (splitArgs.length != 2) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNextOfKinCommand.MESSAGE_USAGE));
        }
        try {
            Index patientIndex = ParserUtil.parseIndex(splitArgs[0]);
            Index nokIndex = ParserUtil.parseIndex(splitArgs[1]);
            return new DeleteNextOfKinCommand(patientIndex, nokIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNextOfKinCommand.MESSAGE_USAGE), pe);
        }
    }
}
