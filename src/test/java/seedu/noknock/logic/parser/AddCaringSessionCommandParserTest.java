package seedu.noknock.logic.parser;

import static seedu.noknock.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_CARE_TYPE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.noknock.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.noknock.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.AddCaringSessionCommand;
import seedu.noknock.model.date.Date;
import seedu.noknock.model.date.Time;
import seedu.noknock.model.session.CareType;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.model.session.Note;

public class AddCaringSessionCommandParserTest {

    private static final String VALID_DATE = "2025-12-25";
    private static final String VALID_TIME = "14:30";
    private static final String VALID_TYPE = "medication";
    private static final String VALID_NOTE = "Check sugar";

    private static final String USER_INPUT_ALL_FIELDS =
        "1 " + PREFIX_DATE + VALID_DATE + " " + PREFIX_TIME + VALID_TIME + " " + PREFIX_CARE_TYPE
            + VALID_TYPE + " " + PREFIX_NOTES + VALID_NOTE;

    private static final String USER_INPUT_EMPTY_NOTE =
        "1 " + PREFIX_DATE + VALID_DATE + " " + PREFIX_TIME + VALID_TIME + " " + PREFIX_CARE_TYPE
            + VALID_TYPE + " " + PREFIX_NOTES;

    private final AddCaringSessionCommandParser parser = new AddCaringSessionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CaringSession expectedSession = new CaringSession(
            new CareType(VALID_TYPE), new Note(VALID_NOTE), new Date(VALID_DATE), new Time(VALID_TIME));
        AddCaringSessionCommand expectedCommand = new AddCaringSessionCommand(
            Index.fromOneBased(1), expectedSession);

        assertParseSuccess(parser, USER_INPUT_ALL_FIELDS, expectedCommand);
    }

    @Test
    public void parse_emptyNotes_failure() {
        CaringSession expectedSession = new CaringSession(
            new CareType(VALID_TYPE), new Note(""), new Date(VALID_DATE), new Time(VALID_TIME));
        AddCaringSessionCommand expectedCommand = new AddCaringSessionCommand(
            Index.fromOneBased(1), expectedSession);

        assertParseSuccess(parser, USER_INPUT_EMPTY_NOTE, expectedCommand);
    }

    @Test
    public void parse_missingIndex_failure() {
        String input = "d/" + VALID_DATE + " time/" + VALID_TIME + " type/" + VALID_TYPE + " notes/" + VALID_NOTE;
        assertParseFailure(parser, input,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCaringSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        String input = "one d/" + VALID_DATE + " time/" + VALID_TIME + " type/" + VALID_TYPE + " notes/" + VALID_NOTE;
        assertParseFailure(parser, input,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCaringSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingDate_failure() {
        String input = "1 time/" + VALID_TIME + " type/" + VALID_TYPE + " notes/" + VALID_NOTE;
        assertParseFailure(parser, input,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCaringSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTime_failure() {
        String input = "1 d/" + VALID_DATE + " type/" + VALID_TYPE + " notes/" + VALID_NOTE;
        assertParseFailure(parser, input,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCaringSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingType_failure() {
        String input = "1 d/" + VALID_DATE + " time/" + VALID_TIME + " notes/" + VALID_NOTE;
        assertParseFailure(parser, input,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCaringSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingNotesPrefix_success() {
        String input = "1 d/" + VALID_DATE + " time/" + VALID_TIME + " type/" + VALID_TYPE;
        CaringSession expectedSession = new CaringSession(
            new CareType(VALID_TYPE), new Note(""), new Date(VALID_DATE), new Time(VALID_TIME));
        AddCaringSessionCommand expectedCommand = new AddCaringSessionCommand(Index.fromOneBased(1), expectedSession);
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String input = "1 d/" + VALID_DATE + " d/2023-01-01 time/" + VALID_TIME + " type/"
            + VALID_TYPE + " notes/" + VALID_NOTE;
        assertParseFailure(parser, input,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));
    }
}
