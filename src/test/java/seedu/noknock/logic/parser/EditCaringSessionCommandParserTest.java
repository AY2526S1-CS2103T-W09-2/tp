package seedu.noknock.logic.parser;

import static seedu.noknock.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_CARE_TYPE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.noknock.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.noknock.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.EditCaringSessionCommand;
import seedu.noknock.logic.commands.EditCaringSessionCommand.EditSessionDescriptor;
import seedu.noknock.model.date.Date;
import seedu.noknock.model.date.Time;
import seedu.noknock.model.session.CareType;
import seedu.noknock.model.session.Note;
import seedu.noknock.model.session.SessionStatus;

public class EditCaringSessionCommandParserTest {

    private static final String VALID_DATE = "2025-12-25";
    private static final String VALID_TIME = "14:30";
    private static final String VALID_TYPE = "medication";
    private static final String VALID_NOTE = "Check sugar";
    private static final String VALID_STATUS = "completed";

    private final EditCaringSessionCommandParser parser = new EditCaringSessionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = "1 1 " + PREFIX_DATE + VALID_DATE + " " + PREFIX_TIME + VALID_TIME + " "
            + PREFIX_CARE_TYPE + VALID_TYPE + " " + PREFIX_NOTES + VALID_NOTE + " "
            + PREFIX_STATUS + VALID_STATUS;

        EditSessionDescriptor descriptor = new EditSessionDescriptor();
        descriptor.setDate(new Date(VALID_DATE));
        descriptor.setTime(new Time(VALID_TIME));
        descriptor.setCareType(new CareType(VALID_TYPE));
        descriptor.setNote(new Note(VALID_NOTE));
        descriptor.setStatus(SessionStatus.of(VALID_STATUS));

        EditCaringSessionCommand expectedCommand = new EditCaringSessionCommand(
            Index.fromOneBased(1), Index.fromOneBased(1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = "1 2 " + PREFIX_CARE_TYPE + VALID_TYPE + " " + PREFIX_NOTES + VALID_NOTE;

        EditSessionDescriptor descriptor = new EditSessionDescriptor();
        descriptor.setCareType(new CareType(VALID_TYPE));
        descriptor.setNote(new Note(VALID_NOTE));

        EditCaringSessionCommand expectedCommand = new EditCaringSessionCommand(
            Index.fromOneBased(1), Index.fromOneBased(2), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // date
        String userInput = "1 1 " + PREFIX_DATE + VALID_DATE;
        EditSessionDescriptor descriptor = new EditSessionDescriptor();
        descriptor.setDate(new Date(VALID_DATE));
        EditCaringSessionCommand expectedCommand = new EditCaringSessionCommand(
            Index.fromOneBased(1), Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time
        userInput = "1 1 " + PREFIX_TIME + VALID_TIME;
        descriptor = new EditSessionDescriptor();
        descriptor.setTime(new Time(VALID_TIME));
        expectedCommand = new EditCaringSessionCommand(
            Index.fromOneBased(1), Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // care type
        userInput = "1 1 " + PREFIX_CARE_TYPE + VALID_TYPE;
        descriptor = new EditSessionDescriptor();
        descriptor.setCareType(new CareType(VALID_TYPE));
        expectedCommand = new EditCaringSessionCommand(
            Index.fromOneBased(1), Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // notes
        userInput = "1 1 " + PREFIX_NOTES + VALID_NOTE;
        descriptor = new EditSessionDescriptor();
        descriptor.setNote(new Note(VALID_NOTE));
        expectedCommand = new EditCaringSessionCommand(
            Index.fromOneBased(1), Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // status
        userInput = "1 1 " + PREFIX_STATUS + VALID_STATUS;
        descriptor = new EditSessionDescriptor();
        descriptor.setStatus(SessionStatus.of(VALID_STATUS));
        expectedCommand = new EditCaringSessionCommand(
            Index.fromOneBased(1), Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() {
        String userInput = "1 1";
        assertParseFailure(parser, userInput, EditCaringSessionCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_missingPatientIndex_failure() {
        String userInput = PREFIX_CARE_TYPE + VALID_TYPE;
        assertParseFailure(parser, userInput,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCaringSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingSessionIndex_failure() {
        String userInput = "1 " + PREFIX_CARE_TYPE + VALID_TYPE;
        assertParseFailure(parser, userInput,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCaringSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPatientIndex_failure() {
        String userInput = "a 1 " + PREFIX_CARE_TYPE + VALID_TYPE;
        assertParseFailure(parser, userInput,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCaringSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSessionIndex_failure() {
        String userInput = "1 a " + PREFIX_CARE_TYPE + VALID_TYPE;
        assertParseFailure(parser, userInput,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCaringSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String userInput = "1 1 " + PREFIX_CARE_TYPE + VALID_TYPE + " "
            + PREFIX_CARE_TYPE + "consultation";
        assertParseFailure(parser, userInput,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CARE_TYPE));
    }

    @Test
    public void parse_tooManyIndices_failure() {
        String userInput = "1 2 3 " + PREFIX_CARE_TYPE + VALID_TYPE;
        assertParseFailure(parser, userInput,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCaringSessionCommand.MESSAGE_USAGE));
    }
}
