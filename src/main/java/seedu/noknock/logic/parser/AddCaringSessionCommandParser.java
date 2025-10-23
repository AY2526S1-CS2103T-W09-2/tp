package seedu.noknock.logic.parser;

import static seedu.noknock.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_CARE_TYPE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.commands.AddCaringSessionCommand;
import seedu.noknock.logic.parser.exceptions.ParseException;
import seedu.noknock.model.date.Date;
import seedu.noknock.model.date.Time;
import seedu.noknock.model.session.CareType;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.model.session.Note;

/**
 * Parses input arguments and creates a new AddCaringSessionCommand object.
 */
public class AddCaringSessionCommandParser implements Parser<AddCaringSessionCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCaringSessionCommand
     * and returns an AddCaringSessionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddCaringSessionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_TIME, PREFIX_CARE_TYPE, PREFIX_NOTES);

        Index patientIndex;

        try {
            patientIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCaringSessionCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_TIME, PREFIX_CARE_TYPE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCaringSessionCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATE, PREFIX_TIME, PREFIX_CARE_TYPE, PREFIX_NOTES);

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        CareType type = ParserUtil.parseCareType(argMultimap.getValue(PREFIX_CARE_TYPE).get());
        Optional<String> noteValue = argMultimap.getValue(PREFIX_NOTES);
        Note note;
        if (noteValue.isPresent()) {
            note = ParserUtil.parseNote(noteValue.get()); // may throw ParseException
        } else {
            note = new Note(""); // or Optional<Note>
        }
        CaringSession session = new CaringSession(type, note, date, time);

        return new AddCaringSessionCommand(patientIndex, session);
    }
}
