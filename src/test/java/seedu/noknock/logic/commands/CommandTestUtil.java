package seedu.noknock.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_WARD;
import static seedu.noknock.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.AddressBook;
import seedu.noknock.model.Model;
import seedu.noknock.model.person.NameContainsKeywordsPredicate;
import seedu.noknock.model.person.Person;
import seedu.noknock.testutil.EditNextOfKinDescriptorBuilder;
import seedu.noknock.testutil.EditPatientDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_WARD_AMY = "2A";
    public static final String VALID_WARD_BOB = "2B";
    public static final String VALID_IC_AMY = "S1234567A";
    public static final String VALID_IC_BOB = "S1234567B";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_NAME_DAUGHTER = "Sammy";
    public static final String VALID_NAME_GRANDPA = "James";
    public static final String VALID_PHONE_DAUGHTER = "11111111";
    public static final String VALID_PHONE_GRANDPA = "22222222";
    public static final String VALID_RELATION_DAUGHTER = "Daughter";
    public static final String VALID_RELATION_GRANDPA = "Grandfather";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String WARD_DESC_AMY = " " + PREFIX_WARD + VALID_WARD_AMY;
    public static final String WARD_DESC_BOB = " " + PREFIX_WARD + VALID_WARD_BOB;
    public static final String IC_DESC_AMY = " " + PREFIX_IC + VALID_IC_AMY;
    public static final String IC_DESC_BOB = " " + PREFIX_IC + VALID_IC_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_WARD_DESC = " " + PREFIX_WARD + "33"; // '33' is not allowed in ward
    public static final String INVALID_IC_DESC = " " + PREFIX_IC + "1234567S"; // 'H1234567S' is not allowed in IC
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_PHONE = "12"; // phone should be at least 3 digits long
    public static final String INVALID_PHONE_NON_DIGIT = "12A"; // non-number characters are not allowed in phone
    public static final String INVALID_RELATIONSHIP = "Brother123!"; // invalid chars (numbers, special chars)

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPatientDescriptor DESC_AMY;
    public static final EditCommand.EditPatientDescriptor DESC_BOB;
    public static final EditNextOfKinCommand.EditNextOfKinDescriptor DESC_DAUGHTER;
    public static final EditNextOfKinCommand.EditNextOfKinDescriptor DESC_GRANDPA;

    static {
        DESC_AMY = new EditPatientDescriptorBuilder().withName(VALID_NAME_AMY)
                .withWard(VALID_WARD_AMY).withIC(VALID_IC_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB)
                .withWard(VALID_WARD_AMY).withIC(VALID_IC_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_DAUGHTER = new EditNextOfKinDescriptorBuilder().withName(VALID_NAME_DAUGHTER)
                .withPhone(VALID_PHONE_DAUGHTER).withRelationship(VALID_RELATION_DAUGHTER).build();
        DESC_GRANDPA = new EditNextOfKinDescriptorBuilder().withName(VALID_NAME_GRANDPA)
                .withPhone(VALID_PHONE_GRANDPA).withRelationship(VALID_RELATION_GRANDPA).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPatientList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPatientList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPatientList().size());

        Person person = model.getFilteredPatientList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPatientList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPatientList().size());
    }

}
