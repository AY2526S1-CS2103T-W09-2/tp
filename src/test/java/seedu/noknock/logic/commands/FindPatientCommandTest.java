package seedu.noknock.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.noknock.logic.Messages;
import seedu.noknock.model.AddressBook;
import seedu.noknock.model.Model;
import seedu.noknock.model.ModelManager;
import seedu.noknock.model.UserPrefs;
import seedu.noknock.model.person.PatientNameContainsKeywordsPredicate;
import seedu.noknock.testutil.PatientBuilder;

public class FindPatientCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        AddressBook addressBook = new AddressBook();

        // two patients
        addressBook.addPatient(new PatientBuilder().withName("Alice Pauline").withIC("S1234567A").build());
        addressBook.addPatient(new PatientBuilder().withName("John Brown").withIC("S1234567B").build());

        model = new ModelManager(addressBook, new UserPrefs());
    }

    @Test
    public void equals() {
        PatientNameContainsKeywordsPredicate firstPredicate =
                new PatientNameContainsKeywordsPredicate(Collections.singletonList("first"));
        PatientNameContainsKeywordsPredicate secondPredicate =
                new PatientNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPatientCommand findFirstCommand = new FindPatientCommand(firstPredicate);
        FindPatientCommand findSecondCommand = new FindPatientCommand(secondPredicate);

        // same object -> true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> true
        FindPatientCommand findFirstCommandCopy = new FindPatientCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> false
        assertNotEquals(1, findFirstCommand);

        // null -> false
        assertNotEquals(null, findFirstCommand);

        // different predicate -> false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_emptyKeywords_listsAllPatients() {
        FindPatientCommand command =
                new FindPatientCommand(new PatientNameContainsKeywordsPredicate(Collections.emptyList()));
        CommandResult result = command.execute(model);

        // There are 2 patients added in setUp
        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2),
                result.getFeedbackToUser());
        assertEquals(2, model.getFilteredPatientList().size());
    }

    @Test
    public void execute_multipleKeywords_multiplePatientsFound() {
        FindPatientCommand command =
                new FindPatientCommand(new PatientNameContainsKeywordsPredicate(Arrays.asList("alice", "brown")));
        CommandResult result = command.execute(model);

        // Matches "Alice Pauline" via "alice" and "Bob Brown" via "brown"
        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2),
                result.getFeedbackToUser());
        assertEquals(2, model.getFilteredPatientList().size());
    }
}
