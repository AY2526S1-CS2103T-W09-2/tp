package seedu.noknock.logic.parser;

import static seedu.noknock.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_CARE_TYPE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Objects;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.commands.EditCaringSessionCommand;
import seedu.noknock.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCaringSessionCommand object
 */
public class EditCaringSessionCommandParser implements Parser<EditCaringSessionCommand> {
    @Override
    public EditCaringSessionCommand parse(String args) throws ParseException {
        Objects.requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
            args, PREFIX_CARE_TYPE, PREFIX_NOTES, PREFIX_DATE, PREFIX_TIME, PREFIX_STATUS);

        Index patientIndex;
        Index sessionIndex;

        try {
            String trimmedPreamble = argMultimap.getPreamble().trim();
            String[] indices = trimmedPreamble.split("\\s+");
            if (indices.length != 2) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCaringSessionCommand.MESSAGE_USAGE));
            }
            patientIndex = ParserUtil.parseIndex(indices[0]);
            sessionIndex = ParserUtil.parseIndex(indices[1]);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCaringSessionCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
            PREFIX_CARE_TYPE, PREFIX_NOTES, PREFIX_DATE, PREFIX_TIME, PREFIX_STATUS);

        EditCaringSessionCommand.EditSessionDescriptor descriptor =
            new EditCaringSessionCommand.EditSessionDescriptor();

        if (argMultimap.getValue(PREFIX_CARE_TYPE).isPresent()) {
            descriptor.setCareType(ParserUtil.parseCareType(argMultimap.getValue(PREFIX_CARE_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTES).isPresent()) {
            descriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTES).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            descriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            descriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            descriptor.setStatus(ParserUtil.parseSessionStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }

        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCaringSessionCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCaringSessionCommand(patientIndex, sessionIndex, descriptor);
    }
}
