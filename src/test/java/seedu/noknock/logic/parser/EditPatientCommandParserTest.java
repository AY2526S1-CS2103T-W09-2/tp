package seedu.noknock.logic.parser;

import static seedu.noknock.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.noknock.logic.commands.CommandTestUtil.IC_DESC_AMY;
import static seedu.noknock.logic.commands.CommandTestUtil.IC_DESC_BOB;
import static seedu.noknock.logic.commands.CommandTestUtil.INVALID_IC_DESC;
import static seedu.noknock.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.noknock.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.noknock.logic.commands.CommandTestUtil.INVALID_WARD_DESC;
import static seedu.noknock.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.noknock.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.noknock.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.noknock.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_IC_BOB;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_WARD_BOB;
import static seedu.noknock.logic.commands.CommandTestUtil.WARD_DESC_AMY;
import static seedu.noknock.logic.commands.CommandTestUtil.WARD_DESC_BOB;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_WARD;
import static seedu.noknock.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.noknock.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.EditPatientCommand;
import seedu.noknock.logic.commands.EditPatientCommand.EditPatientDescriptor;
import seedu.noknock.model.person.IC;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.Ward;
import seedu.noknock.model.tag.Tag;
import seedu.noknock.testutil.EditPatientDescriptorBuilder;

public class EditPatientCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;
    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPatientCommand.MESSAGE_USAGE);

    private EditPatientCommandParser parser = new EditPatientCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_BOB, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditPatientCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_BOB, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_BOB, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid ward
        assertParseFailure(parser, "1" + INVALID_WARD_DESC, Ward.MESSAGE_CONSTRAINTS);

        // invalid ic
        assertParseFailure(parser, "1" + INVALID_IC_DESC, IC.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_WARD_DESC,
            Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateFields_failure() {
        // multiple names
        assertParseFailure(parser, "1" + NAME_DESC_BOB + NAME_DESC_AMY,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple wards
        assertParseFailure(parser, "1" + WARD_DESC_BOB + WARD_DESC_AMY,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WARD));

        // multiple ics
        assertParseFailure(parser, "1" + IC_DESC_BOB + IC_DESC_AMY,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_IC));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = "1" + NAME_DESC_BOB + WARD_DESC_BOB + IC_DESC_BOB + TAG_DESC_HUSBAND;

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
            .withName(VALID_NAME_BOB)
            .withWard(VALID_WARD_BOB)
            .withIC(VALID_IC_BOB)
            .withTags(VALID_TAG_HUSBAND).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(Index.fromOneBased(1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = "1" + NAME_DESC_BOB + WARD_DESC_BOB;

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
            .withName(VALID_NAME_BOB)
            .withWard(VALID_WARD_BOB)
            .build();
        EditPatientCommand expectedCommand = new EditPatientCommand(Index.fromOneBased(1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String userInput = "1" + NAME_DESC_BOB;
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
            .withName(VALID_NAME_BOB)
            .build();
        EditPatientCommand expectedCommand = new EditPatientCommand(Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // ward
        userInput = "1" + WARD_DESC_BOB;
        descriptor = new EditPatientDescriptorBuilder()
            .withWard(VALID_WARD_BOB)
            .build();
        expectedCommand = new EditPatientCommand(Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // ic
        userInput = "1" + IC_DESC_BOB;
        descriptor = new EditPatientDescriptorBuilder()
            .withIC(VALID_IC_BOB)
            .build();
        expectedCommand = new EditPatientCommand(Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = "1" + TAG_DESC_FRIEND;
        descriptor = new EditPatientDescriptorBuilder()
            .withTags(VALID_TAG_FRIEND)
            .build();
        expectedCommand = new EditPatientCommand(Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleTagsSpecified_success() {
        String userInput = "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
            .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
            .build();
        EditPatientCommand expectedCommand = new EditPatientCommand(Index.fromOneBased(1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        String userInput = "1" + TAG_EMPTY;

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
            .withTags()
            .build();
        EditPatientCommand expectedCommand = new EditPatientCommand(Index.fromOneBased(1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
