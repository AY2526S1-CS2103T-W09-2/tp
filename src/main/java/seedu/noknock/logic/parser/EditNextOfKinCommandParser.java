package seedu.noknock.logic.parser;

import static seedu.noknock.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;

import java.util.Objects;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.commands.EditNextOfKinCommand;
import seedu.noknock.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditPatientCommand object
 */
public class EditNextOfKinCommandParser implements Parser<EditNextOfKinCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditPatientCommand
     * and returns an EditPatientCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditNextOfKinCommand parse(String args) throws ParseException {
        Objects.requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_RELATIONSHIP);

        Index patientIndex;
        Index nokIndex;

        try {
            String trimmedPreamble = argMultimap.getPreamble().trim();
            String[] indices = trimmedPreamble.split("\\s+");
            if (indices.length != 2) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNextOfKinCommand.MESSAGE_USAGE));
            }
            patientIndex = ParserUtil.parseIndex(indices[0]);
            nokIndex = ParserUtil.parseIndex(indices[1]);

        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNextOfKinCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_RELATIONSHIP);

        EditNextOfKinCommand.EditNextOfKinDescriptor
            editNextOfKinDescriptor = new EditNextOfKinCommand.EditNextOfKinDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editNextOfKinDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editNextOfKinDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_RELATIONSHIP).isPresent()) {
            editNextOfKinDescriptor.setRelationship(ParserUtil.parseRelationship(argMultimap
                .getValue(PREFIX_RELATIONSHIP).get()));
        }

        if (!editNextOfKinDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditNextOfKinCommand.MESSAGE_NOT_EDITED);
        }

        return new EditNextOfKinCommand(patientIndex, nokIndex, editNextOfKinDescriptor);
    }
}
