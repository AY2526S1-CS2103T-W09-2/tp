package seedu.noknock.logic.parser;

import static seedu.noknock.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.commands.DeleteCaringSessionCommand;
import seedu.noknock.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCaringSessionCommand object.
 */
public class DeleteCaringSessionCommandParser implements Parser<DeleteCaringSessionCommand> {

    @Override
    public DeleteCaringSessionCommand parse(String args) throws ParseException {
        try {
            String[] parts = args.trim().split("\\s+");
            if (parts.length != 2) {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, DeleteCaringSessionCommand.MESSAGE_USAGE));
            }

            Index patientIndex = ParserUtil.parseIndex(parts[0]);
            Index sessionIndex = ParserUtil.parseIndex(parts[1]);

            return new DeleteCaringSessionCommand(patientIndex, sessionIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCaringSessionCommand.MESSAGE_USAGE), pe);
        }
    }
}
