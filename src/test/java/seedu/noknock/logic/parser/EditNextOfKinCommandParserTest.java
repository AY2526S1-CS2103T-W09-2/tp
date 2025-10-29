package seedu.noknock.logic.parser;

import static seedu.noknock.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.noknock.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.noknock.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.EditNextOfKinCommand;
import seedu.noknock.logic.commands.EditNextOfKinCommand.EditNextOfKinDescriptor;
import seedu.noknock.testutil.EditNextOfKinDescriptorBuilder;

public class EditNextOfKinCommandParserTest {

    private static final String VALID_NAME = "Jane Doe";
    private static final String VALID_PHONE = "98765432";
    private static final String VALID_RELATIONSHIP = "Mother";

    private final EditNextOfKinCommandParser parser = new EditNextOfKinCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // user provides patient index, NOK index, and all fields
        String userInput = "1 1 " + PREFIX_NAME + VALID_NAME + " "
                + PREFIX_PHONE + VALID_PHONE + " "
                + PREFIX_RELATIONSHIP + VALID_RELATIONSHIP;

        EditNextOfKinDescriptor descriptor = new EditNextOfKinDescriptorBuilder()
                .withName(VALID_NAME)
                .withPhone(VALID_PHONE)
                .withRelationship(VALID_RELATIONSHIP)
                .build();

        EditNextOfKinCommand expectedCommand =
                new EditNextOfKinCommand(Index.fromOneBased(1), Index.fromOneBased(1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        // user provides only phone number
        String userInput = "1 2 " + PREFIX_PHONE + VALID_PHONE;

        EditNextOfKinDescriptor descriptor = new EditNextOfKinDescriptorBuilder()
                .withPhone(VALID_PHONE)
                .build();

        EditNextOfKinCommand expectedCommand =
                new EditNextOfKinCommand(Index.fromOneBased(1), Index.fromOneBased(2), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index patientIndex = Index.fromOneBased(1);
        Index nokIndex = Index.fromOneBased(1);

        // name only
        String userInput = patientIndex.getOneBased() + " " + nokIndex.getOneBased() + " " + PREFIX_NAME + VALID_NAME;
        EditNextOfKinDescriptor descriptor = new EditNextOfKinDescriptorBuilder()
                .withName(VALID_NAME)
                .build();
        assertParseSuccess(parser, userInput,
                new EditNextOfKinCommand(patientIndex, nokIndex, descriptor));

        // phone only
        userInput = patientIndex.getOneBased() + " " + nokIndex.getOneBased() + " " + PREFIX_PHONE + VALID_PHONE;
        descriptor = new EditNextOfKinDescriptorBuilder()
                .withPhone(VALID_PHONE)
                .build();
        assertParseSuccess(parser, userInput,
                new EditNextOfKinCommand(patientIndex, nokIndex, descriptor));

        // relationship only
        userInput = patientIndex.getOneBased() + " " + nokIndex.getOneBased() + " "
                + PREFIX_RELATIONSHIP + VALID_RELATIONSHIP;
        descriptor = new EditNextOfKinDescriptorBuilder()
                .withRelationship(VALID_RELATIONSHIP)
                .build();
        assertParseSuccess(parser, userInput,
                new EditNextOfKinCommand(patientIndex, nokIndex, descriptor));
    }

    @Test
    public void parse_noFieldSpecified_failure() {
        // user provides only indices without any fields -> should fail
        String userInput = "1 1";
        assertParseFailure(parser, userInput, EditNextOfKinCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_missingPatientIndex_failure() {
        // missing patient index -> should fail
        String userInput = PREFIX_NAME + VALID_NAME;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNextOfKinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingNokIndex_failure() {
        // missing NOK index -> should fail
        String userInput = "1 " + PREFIX_NAME + VALID_NAME;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNextOfKinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPatientIndex_failure() {
        // non-integer patient index -> should fail
        String userInput = "a 1 " + PREFIX_NAME + VALID_NAME;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNextOfKinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNokIndex_failure() {
        // non-integer NOK index -> should fail
        String userInput = "1 a " + PREFIX_NAME + VALID_NAME;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNextOfKinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // user enters the same prefix twice -> should fail
        String userInput = "1 1 " + PREFIX_PHONE + VALID_PHONE + " "
                + PREFIX_PHONE + "1234";
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
    }

    @Test
    public void parse_tooManyIndices_failure() {
        // more than two indices provided -> should fail
        String userInput = "1 2 3 " + PREFIX_NAME + VALID_NAME;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNextOfKinCommand.MESSAGE_USAGE));
    }
}
