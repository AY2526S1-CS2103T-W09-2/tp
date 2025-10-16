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
import static seedu.noknock.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.noknock.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
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
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_WARD;
import static seedu.noknock.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.noknock.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.noknock.testutil.TypicalPatients.AMY;
import static seedu.noknock.testutil.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.AddPatientCommand;
import seedu.noknock.model.person.IC;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.person.Ward;
import seedu.noknock.model.tag.Tag;
import seedu.noknock.testutil.PatientBuilder;

public class AddCommandParserTest {
    private AddPatientCommandParser parser = new AddPatientCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Patient expectedPerson = new PatientBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + WARD_DESC_BOB + IC_DESC_BOB + TAG_DESC_FRIEND,
                new AddPatientCommand(expectedPerson));


        // multiple tags - all accepted
        Patient expectedPersonMultipleTags = new PatientBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + WARD_DESC_BOB + IC_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddPatientCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + WARD_DESC_BOB + IC_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        System.out.println(NAME_DESC_AMY + validExpectedPersonString);
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple wards
        System.out.println(WARD_DESC_AMY + validExpectedPersonString);
        assertParseFailure(parser, WARD_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WARD));

        // multiple ic
        System.out.println(IC_DESC_AMY + validExpectedPersonString);
        assertParseFailure(parser, IC_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_IC));

        // multiple fields repeated
        System.out.println(validExpectedPersonString + WARD_DESC_AMY + IC_DESC_AMY
                + NAME_DESC_AMY + validExpectedPersonString);
        assertParseFailure(parser,
                validExpectedPersonString + WARD_DESC_AMY + IC_DESC_AMY + NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_WARD, PREFIX_IC));

        // invalid name
        System.out.println(INVALID_NAME_DESC + validExpectedPersonString);
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        System.out.println(INVALID_WARD_DESC + validExpectedPersonString);
        assertParseFailure(parser, INVALID_WARD_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WARD));

        // invalid phone
        System.out.println(INVALID_IC_DESC + validExpectedPersonString);
        assertParseFailure(parser, INVALID_IC_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_IC));

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_WARD_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WARD));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_IC_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_IC));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Patient expectedPerson = new PatientBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + WARD_DESC_AMY + IC_DESC_AMY,
                new AddPatientCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + WARD_DESC_BOB + IC_DESC_BOB,
                expectedMessage);

        System.out.println(NAME_DESC_BOB + VALID_WARD_BOB + IC_DESC_BOB);
        // missing ward prefix
        assertParseFailure(parser, NAME_DESC_BOB + " " + VALID_WARD_BOB + IC_DESC_BOB,
                expectedMessage);

        // missing ic prefix
        System.out.println(NAME_DESC_BOB + WARD_DESC_BOB + VALID_IC_BOB);
        assertParseFailure(parser, NAME_DESC_BOB + WARD_DESC_BOB + VALID_IC_BOB,
                expectedMessage);

        // all prefixes missing
        System.out.println(VALID_NAME_BOB + VALID_WARD_BOB + VALID_IC_BOB);
        assertParseFailure(parser, VALID_NAME_BOB + VALID_WARD_BOB + VALID_IC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + WARD_DESC_BOB + IC_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_WARD_DESC + IC_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Ward.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + WARD_DESC_BOB + INVALID_IC_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, IC.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + WARD_DESC_BOB + IC_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + WARD_DESC_BOB + IC_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + WARD_DESC_BOB + IC_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE));
    }
}
