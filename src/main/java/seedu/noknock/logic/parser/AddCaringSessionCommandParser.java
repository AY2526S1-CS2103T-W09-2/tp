package seedu.noknock.logic.parser;

import static seedu.noknock.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TYPE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.commands.AddCaringSessionCommand;
import seedu.noknock.logic.parser.exceptions.ParseException;
import seedu.noknock.model.person.CaringSession;

/**
 * Parses input arguments and creates a new AddCaringSessionCommand object.
 */
public class AddCaringSessionCommandParser implements Parser<AddCaringSessionCommand> {

    @Override
    public AddCaringSessionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_TIME, PREFIX_TYPE, PREFIX_NOTE);

        String preamble = argMultimap.getPreamble();
        String[] preambleParts = preamble.trim().split("\\s+");

        if (preambleParts.length != 1
                || !arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_TIME, PREFIX_TYPE)) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddCaringSessionCommand.MESSAGE_USAGE));
        }

        Index patientIndex = ParserUtil.parseIndex(preambleParts[0]);
        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        LocalTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        String type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        String notes = argMultimap.getValue(PREFIX_NOTE).orElse("");

        CaringSession session = new CaringSession(date, time, type, notes);

        return new AddCaringSessionCommand(patientIndex, session);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
